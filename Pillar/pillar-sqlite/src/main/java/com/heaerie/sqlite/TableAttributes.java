package com.heaerie.sqlite;

class TableAttributes {
    String dataType;
    boolean primaryKey;
    boolean notNull;
    Object value;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "TableAttributes {" +
                "dataType='" + dataType + '\'' +
                ", primaryKey=" + primaryKey +
                ", notNull=" + notNull +
                ", value=" + value +
                '}';
    }
}
