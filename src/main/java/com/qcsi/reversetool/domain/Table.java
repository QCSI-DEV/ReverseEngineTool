package com.qcsi.reversetool.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Table {

    private String name;
    private List<Column> columns;

    public Table(String tableName, List<Column> columns) {
        this.name = tableName;
        this.columns = columns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public static List<String> getTables(ResultSet resultSet){
        List<String> tableNames = new ArrayList<String>();
        try {
            while (resultSet.next()) {
                tableNames.add(resultSet.getString("table_name"));
            }
        } catch (SQLException e) {
            System.out.println("Cannot parse the given ResultSet");
            e.printStackTrace();
        }
        return tableNames;
    }
}
