package ua.knure.generators;

import ua.knure.Converter;
import ua.knure.domain.Table;

import java.io.FileWriter;
import java.io.IOException;

public class EntityDaoInterfaceGenerator {

    public static void generate(Table table, String fileName){
        String tableName = Converter.toPascalCase(table.getName());
        String s = String.format(
                "public class %1$sDao extends AbstractDao<%1$s> implements GenericDao<%1$s>{\n\n}",
                tableName);
        try (FileWriter writer = new FileWriter(fileName, false)){
            writer.write(s);
        } catch (IOException e) {
            System.out.println("Cannot write " + table.getName() + "Dao.java. " + e.getMessage());
        }
    }
}
