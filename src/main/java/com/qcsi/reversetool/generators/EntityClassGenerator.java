package ua.knure.generators;

import ua.knure.Converter;
import ua.knure.domain.Column;
import ua.knure.domain.Table;

import java.io.FileWriter;
import java.io.IOException;

public class EntityClassGenerator {

    public static void generate(Table table, String fileName){
        String tableName = Converter.toPascalCase(table.getName());
        StringBuilder entityClass = new StringBuilder(String.format("public class %s{\n\n", tableName));
        for (Column column : table.getColumns()){
            String columnName = Converter.toCamelCase(column.getName());
            entityClass.append(String.format("\tprivate %s %s;\n",
                    Converter.toJavaType(column.getType()),
                    columnName));
        }
        entityClass.append("\n");
        for (Column column : table.getColumns()){
            String camelColumnName = Converter.toCamelCase(column.getName());
            String pascalColumnName = Converter.toPascalCase(column.getName());
            entityClass.append(String.format("\tpublic %s get%s(){\n\t\treturn %s;\n\t}\n\n",
                    Converter.toJavaType(column.getType()),
                    pascalColumnName,
                    camelColumnName));
            entityClass.append(String.format("\tpublic void set%1$s(%2$s %3$s){\n\t\tthis.%3$s = %3$s;\n\t  }\n\n",
                    pascalColumnName,
                    Converter.toJavaType(column.getType()),
                    camelColumnName));
        }
        entityClass.append("}");

        try (FileWriter writer = new FileWriter(fileName, false)){
            writer.write(entityClass.toString());
        } catch (IOException e) {
            System.out.println("Cannot write GenericDao.java. " + e.getMessage());
        }
    }
}
