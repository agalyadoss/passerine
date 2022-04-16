package com.heaerie.sqlite;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.persistence.Id;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;

import com.google.common.base.CaseFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mapper<T> {
    static final Logger logger = LogManager.getLogger();
    // private static Bindings simpleDateFormat;  .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
    static String isoDatePattern = "yyyy-MM-dd'T'HH:mm:ssZ";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);

    public static final String TEXT = "TEXT";
    public static final String INTEGER = "INTEGER";
    public static final String DOUBLE = "DOUBLE";
    public static final String DATE = "DATE";
    public static final String LONG = "LONG";
    public static final String LIST_TEXT = "TEXT";
    public static final String MAP_TEXT = "TEXT";
    public static final String SET_TEXT = "TEXT";
    public static final String OBJECT_TEXT = "TEXT";
    static Connection conn;
    Map<String, Set<String>> sqliteToJavaDataTypeMap = createSqliteToJavaDataTypeMap();
    Map<String, Field> primaryKeys = new HashMap<String, Field>();

    static Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            //.excludeFieldsWithoutExposeAnnotation()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .setPrettyPrinting()
            .create();

    public static void initilize(Connection conn) {
        Mapper.conn = conn;

    }

    public static Date fromIsoDate(String sendTime) throws ParseException {
        if (isNullOrEmpty(sendTime )) {
            return  null;
        }
        return  simpleDateFormat.parse(sendTime);
    }


    private Map<String, Set<String>> createSqliteToJavaDataTypeMap() {

        Map<String, Set<String>> map = new HashMap<String, Set<String>>();
        Set<String> listText = new HashSet<String>();
        listText.add(String.class.getName());
        map.put(TEXT, listText);

        Set<String> listNumber = new HashSet<String>();
        listNumber.add(Number.class.getName());
        listNumber.add(int.class.getName());
        map.put(INTEGER, listNumber);

        Set<String> listDouble = new HashSet<String>();
        listDouble.add(double.class.getName());
        listDouble.add(Double.class.getName());
        map.put(DOUBLE, listDouble);


        Set<String> listDate = new HashSet<String>();
        listDate.add(Date.class.getName());
        map.put(DATE, listDate);

        Set<String> listLong = new HashSet<String>();
        listLong.add(long.class.getName());
        listLong.add(Long.class.getName());
        map.put(DOUBLE, listLong);


        Set<String> listList = new HashSet<String>();
        listList.add(List.class.getName());
        listList.add(ArrayList.class.getName());
        map.put(LIST_TEXT, listList);

        return map;
    }

    private String getDataType(Class<?> type) {

        for (Map.Entry<String, Set<String>> item : sqliteToJavaDataTypeMap.entrySet()) {
            System.out.println("type=<" + type.getName() + ">, key=<" + item.getKey() + "> list=" + item.getValue());
            if (item.getValue().contains(type.getName())) {
                return item.getKey();
            }
        }
        return "TEXT";
    }

    public void drop(Class<T> tClass) {
        String dropStmt = prepareDrop(tClass.getSimpleName());
        try {
            conn.createStatement().execute(dropStmt);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void delete(T s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        List<T> a= get(s);
        if (!a.isEmpty()) {
            List<T> list = new ArrayList<T>();
            Map<String, TableAttributes> columns = new HashMap<String, TableAttributes>();
            System.out.println(s.getClass());

            for (Field field : s.getClass().getDeclaredFields()) {
                TableAttributes tb = new TableAttributes();
                tb.setDataType(getDataType(field.getType()));

                for (Annotation annotation : field.getAnnotations()) {
                    if (Id.class.getName().equals(annotation.annotationType().getName())) {
                        primaryKeys.put(field.getName(), field);
                        tb.setPrimaryKey(true);
                    }
                }

                if (s != null) {
                    Object value = s.getClass().getMethod(getMethodName(field.getName(), field.getClass().getName())).invoke(s);
                    tb.setValue(value);
                }

                columns.put(field.getName(), tb);

            }

            String deleteStmt = prepareDelete(s.getClass().getSimpleName(), columns);
            logger.debug("deleteStmt={}", deleteStmt);
            System.out.println(deleteStmt);
            try {
                conn.createStatement().execute(deleteStmt);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }

        }


    }


    private String prepareDrop(String tableName) {
        return  "DROP TABLE " + tableName + " ;";

    }

    public List<T> get(T s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<T> list = new ArrayList<T>();
        Map<String, TableAttributes> columns = new HashMap<String, TableAttributes>();
        System.out.println(s.getClass());

        for (Field field : s.getClass().getDeclaredFields()) {
            TableAttributes tb = new TableAttributes();
            tb.setDataType(getDataType(field.getType()));

            for (Annotation annotation : field.getAnnotations()) {
                if (Id.class.getName().equals(annotation.annotationType().getName())) {
                    primaryKeys.put(field.getName(), field);
                    tb.setPrimaryKey(true);
                }
            }

            if (s != null) {
                Object value = s.getClass().getMethod(getMethodName(field.getName(), field.getClass().getName())).invoke(s);
                tb.setValue(value);
            }

            columns.put(field.getName(), tb);

        }

        String selectStmt = prepareSelect(s.getClass().getSimpleName(), columns);
        logger.debug("selectStmt={}",selectStmt);
        System.out.println(selectStmt);

        try {
            ResultSet rs = conn.createStatement().executeQuery(selectStmt);
            while (rs.next()) {
                StringBuffer sbJson = new StringBuffer();
                sbJson.append("{");
                String sep = "";
                for (Map.Entry<String, TableAttributes> col : columns.entrySet()) {

                    if (col.getValue().getDataType().equals(TEXT) ) {
                        if (rs.getString(rs.findColumn(col.getKey())) != null ) {
                            if (col.getValue().getValue() instanceof  String) {
                                sbJson.append(sep + "\"" + getCamelCaseToLowerUnderscore(col.getKey()) + "\" : " + "\"" + rs.getString(rs.findColumn(col.getKey())) + "\"");
                            } else {
                                sbJson.append(sep + "\"" + getCamelCaseToLowerUnderscore(col.getKey()) + "\" : " + "" + rs.getString(rs.findColumn(col.getKey())) + "");

                            }
                            sep = ",\n";
                        }

                    }
                    if (col.getValue().getDataType().equals(DATE) ) {
                        if (rs.getString(rs.findColumn(col.getKey())) != null ) {
                            sbJson.append(sep + "\"" + getCamelCaseToLowerUnderscore(col.getKey()) + "\" : " + "\"" + rs.getString(rs.findColumn(col.getKey())) + "\"");
                            sep = ",\n";
                        }
                    }

                    if (col.getValue().getDataType().equals(INTEGER) ) {

                        sbJson.append(sep + "\"" + col.getKey() + "\" : " + rs.getInt(rs.findColumn(col.getKey())) );
                        sep = ",\n";

                    }

                    if (col.getValue().getDataType().equals(DOUBLE) ) {
                        sbJson.append(sep + "\"" + getCamelCaseToLowerUnderscore(col.getKey()) + "\" : " + rs.getDouble(rs.findColumn(col.getKey())) );
                        sep = ",\n";
                    }

                    if (col.getValue().getDataType().equals(LONG) ) {

                        sbJson.append(sep + "\"" + getCamelCaseToLowerUnderscore(col.getKey()) + "\" : " + rs.getLong(rs.findColumn(col.getKey())) );
                        sep = ",\n";

                    }


                }
                sbJson.append("}");
                logger.debug("sbJson={}",sbJson);

                System.out.println(sbJson);
                T s1 = (T) gson.fromJson(sbJson.toString(), s.getClass());
                list.add(s1);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }


        return list;
    }

    private String prepareSelect(String simpleName, Map<String, TableAttributes> columns) {
        StringBuffer selBuf = new StringBuffer();
        selBuf.append("SELECT  *  \n FROM ");
        selBuf.append(simpleName);

        StringBuffer wb = new StringBuffer();
        wb.append("\n WHERE ");

        Boolean needWhereClause = false;
        if (columns != null) {
            String wbSep = "";
            String sbSep = "";
            for (Map.Entry<String, TableAttributes> col : columns.entrySet()) {
                if (!isNullOrEmpty(col.getValue().getValue())) {
                    needWhereClause = true;
                    wb.append(wbSep + col.getKey() + " = ");

                    if (INTEGER.equals(col.getValue().getDataType()) || DOUBLE.equals(col.getValue().getDataType()) || LONG.equals(col.getValue().getDataType())) {
                        wb.append(col.getValue().getValue());
                    } else if (DATE.equals(col.getValue().getDataType()) ) {
                        wb.append( "\"" + toIsoDate( (Date) col.getValue().getValue()) + "\"");
                    } else {
                        wb.append("\'" + col.getValue().getValue() + "\'");
                    }
                    wbSep = " AND \n";
                }
            }
        }

        if (needWhereClause) {
            selBuf.append(wb.toString());
        }
        selBuf.append(" ;");
        return selBuf.toString();
    }


    private String prepareDelete(String simpleName, Map<String, TableAttributes> columns) {
        StringBuffer selBuf = new StringBuffer();
        selBuf.append("DELETE  FROM ");
        selBuf.append(simpleName);

        StringBuffer wb = new StringBuffer();
        wb.append("\n WHERE ");

        Boolean needWhereClause = false;
        if (columns != null) {
            String wbSep = "";
            String sbSep = "";
            for (Map.Entry<String, TableAttributes> col : columns.entrySet()) {
                if (!isNullOrEmpty(col.getValue().getValue())) {
                    needWhereClause = true;
                    wb.append(wbSep + col.getKey() + " = ");

                    if (INTEGER.equals(col.getValue().getDataType()) || DOUBLE.equals(col.getValue().getDataType()) || LONG.equals(col.getValue().getDataType())) {
                        wb.append(col.getValue().getValue());
                    } else if (DATE.equals(col.getValue().getDataType()) ) {
                        wb.append( "\"" + toIsoDate( (Date) col.getValue().getValue()) + "\"");
                    } else {
                        wb.append("\'" + col.getValue().getValue() + "\'");
                    }
                    wbSep = " AND \n";
                }
            }
        }

        if (needWhereClause) {
            selBuf.append(wb.toString());
        }
        selBuf.append(" ;");
        return selBuf.toString();
    }


    public void save(T s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, PrimaryKeyException {

        logger.debug("save={}",s);
        Map<String, TableAttributes> columns = new HashMap<String, TableAttributes>();
        for (Field field : s.getClass().getDeclaredFields()) {
            TableAttributes tb = new TableAttributes();
            tb.setDataType(getDataType(field.getType()));


            for (Annotation annotation : field.getAnnotations()) {
                if (Id.class.getName().equals(annotation.annotationType().getName())) {
                    primaryKeys.put(field.getName(), field);
                    tb.setPrimaryKey(true);
                }
            }

            columns.put(field.getName(), tb);
            Object value = s.getClass().getMethod(getMethodName(field.getName(), field.getClass().getName())).invoke(s);
            tb.setValue(value);
        }


        for (Map.Entry<String, Field> fieldEntry : primaryKeys.entrySet()) {
            Object value = s.getClass().getMethod(getMethodName(fieldEntry.getKey(), fieldEntry.getValue().getClass().getName())).invoke(s);
            if (isNullOrEmpty(value)) {
                throw new PrimaryKeyException("Name " + fieldEntry.getKey() + " is primary key in [" + s.getClass().getCanonicalName() + "]");
            }
        }

        try {
            String creatSql = prepareCreate(s.getClass().getSimpleName(), columns);
            logger.debug("creatSql={}",creatSql);
            conn.createStatement().execute(creatSql);
        } catch (SQLException exception) {
            System.out.println("Warning " + exception.getMessage());
        }

        try {
            String insertSql = prepareInsert(s.getClass().getSimpleName(), columns);
            logger.debug("insertSql={}",insertSql);
            System.out.println(insertSql);
            conn.createStatement().execute(insertSql);
        } catch (SQLException exception) {
            logger.error("Warning " + exception.getMessage());
            String updateSql = prepareUpdate(s.getClass().getSimpleName(), columns);
            logger.debug("updateSql={}",updateSql);
            System.out.println(updateSql);
            try {
                conn.createStatement().execute(updateSql);
            } catch (SQLException throwables) {
                logger.error("throwables=",throwables);
                System.out.println(updateSql);
                throwables.printStackTrace();
            }
        }


    }

    public static String prepareUpdate(String tableName, Map<String, TableAttributes> columns) {

        StringBuffer up = new StringBuffer();
        up.append("UPDATE ");
        up.append(tableName);
        StringBuffer sb = new StringBuffer();
        StringBuffer wb = new StringBuffer();
        wb.append("WHERE ");
        sb.append(" SET ");
        if (columns != null) {
            String wbSep = "";
            String sbSep = "";
            for (Map.Entry<String, TableAttributes> col : columns.entrySet()) {
                if (!isNullOrEmpty(col.getValue().getValue())) {
                    sb.append(sbSep + col.getKey() + " = ");

                    if (INTEGER.equals(col.getValue().getDataType()) || DOUBLE.equals(col.getValue().getDataType()) || LONG.equals(col.getValue().getDataType())) {
                        sb.append(col.getValue().getValue());
                    } else if (DATE.equals(col.getValue().getDataType()) ) {
                        sb.append( "\"" + toIsoDate( (Date) col.getValue().getValue()) + "\"");
                    } else {
                        sb.append("\'" + qutoReplace(col.getValue().getValue())+ "\'");
                    }

                    sbSep = " ,\n";
                }


                if (!isNullOrEmpty(col.getValue().getValue()) && col.getValue().isPrimaryKey()) {
                    wb.append(wbSep + col.getKey() + " = ");

                    if (INTEGER.equals(col.getValue().getDataType()) || DOUBLE.equals(col.getValue().getDataType()) || LONG.equals(col.getValue().getDataType())) {
                        wb.append(col.getValue().getValue());
                    } else if (DATE.equals(col.getValue().getDataType()) ) {
                        wb.append( "\"" + toIsoDate( (Date) col.getValue().getValue()) + "\"");
                    } else {
                        wb.append("\'" + qutoReplace(col.getValue().getValue()) + "\'");
                    }

                    wbSep = " AND \n";
                }
            }
        }

        up.append(sb);
        up.append(" ");
        up.append(wb);
        return up.toString();
    }

    private String prepareInsert(String tableName, Map<String, TableAttributes> columns) {
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO ");
        sb.append(tableName);
        sb.append(" ");
        StringBuffer kb = new StringBuffer();
        StringBuffer vb = new StringBuffer();
        kb.append("(");
        vb.append("(");
        System.out.println("columns =" + columns);
        if (columns != null) {
            String sep = "";
            for (Map.Entry<String, TableAttributes> col : columns.entrySet()) {
                if (!isNullOrEmpty(col.getValue().getValue())) {
                    kb.append(sep + col.getKey());
                    if (INTEGER.equals(col.getValue().getDataType()) || DOUBLE.equals(col.getValue().getDataType()) || LONG.equals(col.getValue().getDataType())) {
                        vb.append(sep + col.getValue().getValue());
                    } else if (DATE.equals(col.getValue().getDataType()) ) {
                        vb.append(sep + "\"" + toIsoDate( (Date) col.getValue().getValue()) + "\"");
                    } else {
                        String  str = sep + "\'" + qutoReplace(col.getValue().getValue() )+ "\'";
                        System.out.println(str);
                        vb.append(str);
                    }

                    sep = ",\n";
                }
            }
        }
        kb.append(")");
        vb.append(")");
        sb.append(kb);
        sb.append(" VALUES ");
        sb.append(vb.toString());
        return sb.toString();
    }

    public static Object qutoReplace(Object value) {
        if (value instanceof  String) {
            String v = (String) value;
            //  String v1= v.replaceAll("\"", "\\\\\"").replaceAll("\'", "\\\\\'");
            String v1= v.replaceAll("\'", "\\\\\'");

            return  v1;
        }

        if (value instanceof  List || value instanceof  Map) {
            return gson.toJson(value);
        }


        if (value instanceof  Object || value instanceof  Object) {
            return gson.toJson(value);
        }
        return value;

    }

    public static String toIsoDate(Date value) {
        return  simpleDateFormat.format(value);
    }

    private String prepareCreate(String tableName, Map<String, TableAttributes> columns) {
        StringBuffer sb = new StringBuffer();
        sb.append("CREATE TABLE ");
        sb.append(tableName);
        sb.append(" (\n");
        if (columns != null) {
            String sep = "";
            for (Map.Entry<String, TableAttributes> col : columns.entrySet()) {
                sb.append(sep + col.getKey() + " " + col.getValue().getDataType());
                if (col.getValue().isPrimaryKey()) {
                    sb.append(" PRIMARY KEY");
                } else if (col.getValue().isNotNull()) {
                    sb.append(" NOT NULL");
                }
                sep = ",\n";
            }
        }
        sb.append(") ;");
        return sb.toString();

    }


    public static String getMethodName(String name, String jclass) {
        String getMethod = "get";
        if (isNullOrEmpty(name)) {
            return name;
        }
        if (!isNullOrEmpty(jclass) && (boolean.class.getName().equals(jclass) || Boolean.class.getName().equals(jclass))) {
            getMethod = "is";
        }
        return getMethod + getCamelCase(name);
    }

    public static String setMethodName(String name, String jclass) {
        String getMethod = "set";
        if (isNullOrEmpty(name)) {
            return name;
        }
        if (!isNullOrEmpty(jclass) && (boolean.class.getName().equals(jclass) || Boolean.class.getName().equals(jclass))) {
            getMethod = "set";
        }
        return getMethod + getCamelCase(name);
    }

    public static boolean isNullOrEmpty(Object anyObject) {
        if (null == anyObject) {
            return true;
        }

        if (anyObject instanceof String) {
            return ((String) anyObject).isEmpty();
        }

        return false;

    }

    public static String getCamelCase(String name) {
        if (isNullOrEmpty(name)) {
            return name;
        }
        return  name.substring(0,1).toUpperCase() + name.substring(1);
    }

    public static String getCamelCaseToLowerUnderscore(String name) {
        if (isNullOrEmpty(name)) {
            return name;
        }
        return name.replaceAll("([A-Z])", "_$1").toLowerCase();

    }

}