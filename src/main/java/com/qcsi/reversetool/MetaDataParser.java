package com.qcsi.reversetool;

import com.qcsi.reversetool.domain.Column;
import com.qcsi.reversetool.domain.Table;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MetaDataParser {

    public static final String URL = "jdbc:postgresql://localhost/testdb";
    public static final String USER = "app";
    public static final String PASSWORD = "qwerty";
    public static final String DRIVER = "org.postgresql.Driver";
    public static Connection connection = null;

    public static final String DATABASE_META_QUERY =
            "SELECT table_name FROM information_schema.tables WHERE table_schema = \'public\';";
    public static final String TABLE_META_QUERY =
            "SELECT column_name, data_type  FROM information_schema.columns WHERE table_name = ?;";

    private static final Logger log = LogManager.getLogger(MetaDataParser.class.getName());

    public MetaDataParser(){
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e1) {
            log.fatal("Driver class cannot be found.", e1);
        } catch (SQLException e2){
            log.fatal("Cannot connect to the database.", e2);
        }
        log.trace("MetaDataParser instance is created");
    }

    public List<Column> getColumns(String tableName) {
        List<Column> columns = new ArrayList<>();
        PreparedStatement tableStatement;
        try {
            tableStatement = connection.prepareStatement(TABLE_META_QUERY);
            tableStatement.setString(1, tableName);
        } catch (SQLException e) {
            log.error("Cannot create statement for getting table metadata.", e);
            return columns;
        }

        ResultSet tableMetaData;
        try {
            tableMetaData = tableStatement.executeQuery();

            while (tableMetaData.next()){
                columns.add(new Column(
                        Converter.toCamelCase(tableMetaData.getString("column_name")),
                        Converter.toJavaType(tableMetaData.getString("data_type"))));
            }
        } catch (SQLException e) {
            log.error("Cannot parse the given ResultSet", e);
        }
        log.trace("Columns are formed for table " + tableName);
        return columns;
    }

    public List<String> getTableNames(){
        List<String> tableNames = new ArrayList<>();
        Statement databaseStatement;
        try {
            databaseStatement = connection.createStatement();
        } catch (SQLException e){
            log.error("Cannot create statement for getting database metadata.", e);
            return tableNames;
        }
        ResultSet databaseMetaData;
        try {
            databaseMetaData = databaseStatement.executeQuery(DATABASE_META_QUERY);
            while (databaseMetaData.next()) {
                tableNames.add(Converter.toCamelCase(databaseMetaData.getString("table_name")));
            }
        } catch (SQLException e) {
            log.error("Cannot parse the given ResultSet", e);
        }
        log.trace("Table names are extracted");
        return tableNames;
    }

    public List<Table> getTables(){
        List<String> tableNames = getTableNames();
        List<Table> tables = new ArrayList<>();
        for (String tableName : tableNames) {
            Table table = new Table(tableName, getColumns(tableName));
            tables.add(table);
        }
        log.trace("Tables are formed");
        return tables;
    }
}
