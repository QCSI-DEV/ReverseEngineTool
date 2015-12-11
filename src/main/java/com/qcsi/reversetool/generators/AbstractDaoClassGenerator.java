package ua.knure.generators;

import java.io.FileWriter;
import java.io.IOException;

public class AbstractDaoClassGenerator {

    // TODO supplement the class with CRUD, getByPK and getAll methods
    public static void generate(String fileName){
        String s =
                "public abstract class AbstractDao<T, PK> implements GenericDao<E, PK> {\n\n" +
                        "    protected Connection connection;\n\n" +
                        "    protected abstract String getSelectQuery();\n\n" +
                        "    protected abstract String getCreateQuery();\n\n" +
                        "    protected abstract String getUpdateQuery();\n\n" +
                        "    protected abstract String getDeleteQuery();\n\n" +
                        "    protected abstract void prepareStatementForCreate(PreparedStatement statement, E entity) throws SQLException;\n\n" +
                        "    protected abstract void prepareStatementForUpdate(PreparedStatement statement, E entity) throws SQLException;\n\n" +
                        "    protected abstract void prepareStatementForDelete(PreparedStatement statement, E entity) throws SQLException;\n\n" +
                        "    protected abstract List<E> parseResultSet(ResultSet resultSet) throws SQLException;\n}";
        try (FileWriter writer = new FileWriter(fileName, false)){
            writer.write(s);
        } catch (IOException e) {
            System.out.println("Cannot write AbstractDao.java. " + e.getMessage());
        }
    }
}
