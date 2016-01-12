public abstract class ${dbms}AbstractDao<E> implements GenericDao<E> {

    protected Connection connection;

    protected abstract String getSelectQuery();
    protected abstract String getCreateQuery();
    protected abstract String getUpdateQuery();
    protected abstract String getDeleteQuery();

    protected abstract void prepareStatementForCreate(PreparedStatement statement, E entity) throws SQLException;
    protected abstract void prepareStatementForUpdate(PreparedStatement statement, E entity) throws SQLException;
    protected abstract void prepareStatementForDelete(PreparedStatement statement, E entity) throws SQLException;

    protected abstract List<E> parseResultSet(ResultSet resultSet) throws SQLException;

    public E insert(E entity) throws SQLException {
        E insertInstance;
        String query = getCreateQuery();
        PreparedStatement statement = connection.prepareStatement(query);
        prepareStatementForCreate(statement, entity);
        int count = statement.executeUpdate();
        if (count != 1) {
            throw new SQLException("On insert modify more then 1 record: " + count);
        }

        query = getSelectQuery() + " WHERE id = last_insert_id();";
        statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        List<E> list = parseResultSet(resultSet);
        if ((list == null) || (list.size() != 1)) {
            throw new SQLException("Exception on getByPK(). Amount of returned entity records != 1.");
        }
        insertInstance = list.iterator().next();
        statement.close();
        return insertInstance;
    }

    @Override
    public void update(E entity) throws SQLException{
        String query = getUpdateQuery();
        PreparedStatement statement = connection.prepareStatement(query);
        prepareStatementForUpdate(statement, entity);
        int count = statement.executeUpdate();
        if (count != 1) {
            throw new SQLException("On update modify more then 1 record: " + count);
        }
        statement.close();
    }

    @Override
    public List<E> getAll() throws SQLException {
        List<E> list;
        String query = getSelectQuery();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        statement.close();
        list = parseResultSet(resultSet);
        return list;
    }

    @Override
    public E getByPrimaryKey(int primaryKey) throws SQLException{
        List<E> list = null;
        String query = getSelectQuery();
        query += " WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, primaryKey);
            ResultSet resultSet = statement.executeQuery();
            list = parseResultSet(resultSet);
        } catch (SQLException e) {
            System.out.println("SQLException when trying to get element by primary key");
        }
        if (list == null || list.size() == 0) {
            throw new SQLException("Record with " + primaryKey + "primary key was not found.");
        }
        if (list.size() > 1) {
            throw new SQLException("Received more than one record by primary key");
        }
        return list.get(0);
    }
}