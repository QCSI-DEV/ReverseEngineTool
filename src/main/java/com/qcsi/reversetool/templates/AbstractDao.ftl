public abstract class ${dbms}AbstractDao<E> implements GenericDao<E> {

    protected Connection connection;

    protected abstract String getSelectQuery();
    protected abstract String getCreateQuery();
    protected abstract String getUpdateQuery();
    protected abstract String getDeleteQuery();

    protected abstract void prepareStatementForCreate(PreparedStatement statement, E entity) throws SQLException;
    protected abstract void prepareStatementForUpdate(PreparedStatement statement, E entity) throws SQLException;
    protected abstract void prepareStatementForDelete(PreparedStatement statement, E entity) throws SQLException;

    public E insert(E entity) throws SQLException {
        E insertInstance;
        String query = getCreateQuery();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            prepareStatementForInsert(statement, entity);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new SQLException("On insert modify more then 1 record: " + count);
        }

        query = getSelectQuery() + " WHERE id = last_insert_id();";
            PreparedStatement statement = connection.prepareStatement(query)
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
    public void update(E entity) throws PersistException throws SQLException{
        String query = getUpdateQuery();
        PreparedStatement statement = connection.prepareStatement(query);) {
        prepareStatementForUpdate(statement, entity);
        int count = statement.executeUpdate();
        if (count != 1) {
            throw new SQLException("On update modify more then 1 record: " + count);
        }
        statement.close();
    }

    @Override
    public void delete(E entity) throws SQLException {
        String query = getDeleteQuery();
        PreparedStatement statement = connection.prepareStatement(query))
        statement.setInt(1, entity.getId());
        int count = statement.executeUpdate();
        statement.close();
        if (count != 1) {
            throw new SQLException("On delete modify more then 1 record: " + count);
        }
    }

    @Override
    public List<E> getAll() throws SQLException {
        List<E> list;
        String query = getSelectQuery();
        PreparedStatement statement = connection.prepareStatement(query));
        ResultSet resultSet = statement.executeQuery();
        statement.close();
        list = parseResultSet(resultSet);
        return list;
    }

    protected abstract List<E> parseResultSet(ResultSet resultSet) throws SQLException;
}