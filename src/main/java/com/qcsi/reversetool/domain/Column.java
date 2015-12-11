package ua.knure.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Column {

    private String name;
    private String type;

    public Column(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static List<String> getColumns(ResultSet resultSet, String tableName) {
        List<String> columnNames = new ArrayList<String>();
        try {
            while (resultSet.next()) {
                columnNames.add(resultSet.getString(tableName));
            }
        } catch (SQLException e) {
            System.out.println("Cannot parse the given ResultSet");
            e.printStackTrace();
        }
        return columnNames;
    }
}
