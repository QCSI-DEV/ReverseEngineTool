package ua.knure.generators;

import java.io.FileWriter;
import java.io.IOException;

public class GenericDaoGenerator {

    public static final String GENERIC_INTERFACE =
            "public interface GenericDao<E, PK> {\n\n" +
            "    public E create() throws SQLException;\n\n" +
            "    public void insert(E entity) throws SQLException;\n\n" +
            "    public E getByPrimaryKey(PK pk) throws SQLException;\n\n" +
            "    public void update(E entity) throws SQLException;\n\n" +
            "    public void delete(E entity) throws SQLException;\n}";

    public static void generate(String fileName) {
        try (FileWriter writer = new FileWriter(fileName, false)){
            writer.write(GENERIC_INTERFACE);
        } catch (IOException e) {
            System.out.println("Cannot write GenericDao.java. " + e.getMessage());
        }
    }
}
