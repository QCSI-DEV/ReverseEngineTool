package ua.knure;

import ua.knure.domain.Column;
import ua.knure.domain.Table;

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

    static {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException cnfException) {
            System.out.println("Driver class cannot be found.");
            cnfException.printStackTrace();
        } catch (SQLException sqlException){
            System.out.println("Cannot connect to the database.\n" + sqlException.getMessage());
            sqlException.printStackTrace();
        }
    }

    public static List<Column> getColumns(String tableName) {
        List<Column> columns = new ArrayList<>();
        PreparedStatement tableStatement;
        try {
            tableStatement = connection.prepareStatement(TABLE_META_QUERY);
            tableStatement.setString(1, tableName);
        } catch (SQLException e) {
            System.out.println("Cannot create statement for getting table metadata. "
                    + e.getMessage());
            return columns;
        }

        ResultSet tableMetaData;
        try {
            tableMetaData = tableStatement.executeQuery();

            while (tableMetaData.next()){
                columns.add(new Column(
                        tableMetaData.getString("column_name"),
                        tableMetaData.getString("data_type")));
            }
        } catch (SQLException e) {
            System.out.println("Cannot parse the given ResultSet");
            e.printStackTrace();
        }
        return columns;
    }

    public static List<String> getTableNames(){
        List<String> tableNames = new ArrayList<>();
        Statement databaseStatement;
        try {
            databaseStatement = connection.createStatement();
        } catch (SQLException e){
            System.out.println("Cannot create statement for getting database metadata.");
            e.printStackTrace();
            return tableNames;
        }
        ResultSet databaseMetaData;
        try {
            databaseMetaData = databaseStatement.executeQuery(DATABASE_META_QUERY);
            while (databaseMetaData.next()) {
                tableNames.add(databaseMetaData.getString("table_name"));
            }
        } catch (SQLException e) {
            System.out.println("Cannot parse the given ResultSet");
            e.printStackTrace();
        }
        return tableNames;
    }

    public static List<Table> getTables(){
        List<String> tableNames = getTableNames();
        List<Table> tables = new ArrayList<>();
        for (String tableName : tableNames) {
            Table table = new Table(tableName,
                    getColumns(tableName));
            tables.add(table);
        }
        return tables;
    }
}
