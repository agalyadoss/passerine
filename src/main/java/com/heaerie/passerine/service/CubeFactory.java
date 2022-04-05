package com.heaerie.passerine.service;

import com.heaerie.passerine.cube.PAS001MTMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CubeFactory {

    private static Connection conn;

    static CubeFactory singleton;
    static final Logger logger = LogManager.getLogger();

    public static void initialize(String url) throws SQLException {

        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/cubefactory");
            conn = ds.getConnection();
        } catch (NamingException e) {
            logger.error("NamingException1 ={}" , e);
            try {
                conn = DriverManager.getConnection("jdbc:sqlite:" + url);
            } catch (SQLException e1 ) {
                e1.printStackTrace();
                e.printStackTrace();
                throw new SQLException("NamingException2" + e1);
            }
        }

        // conn =DriverManager.getConnection("jdbc:sqlite:" + url);
        logger.info("url=jdbc:sqlite:{}", url);
        if (conn != null) {
            DatabaseMetaData meta = conn.getMetaData();
            logger.info("The driver name is " + meta.getDriverName());
            logger.info("A new database has been created.");
        }

        PAS001MTMapper.initilize(conn);

    }

    CubeFactory(String url) throws SQLException {
        initialize(url);
    }

    public static CubeFactory getInstance() throws SQLException {
        String url = "/opt/data/passerine.db";
        if (singleton == null ) {
            singleton = new CubeFactory(url);
        }
        return  singleton;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public static PAS001MTMapper getPAS001MTMapper() {
        return new PAS001MTMapper();
    }

}
