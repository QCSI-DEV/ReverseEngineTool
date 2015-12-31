public class ${dbms}${table.name?cap_first}Dao extends AbstractDao<${table.name?cap_first}> implements ${table.name?cap_first}Dao{

    public ${dbms}${table.name?cap_first}Dao(Connection connection){
        this.connnection = connection;
    }

    @Override
    protected String getSelectQuery() {
        return
            "SELECT <#rt>
            <#list table.columns as column>
                ${column.name}<#sep>, <#t>
            </#list>
            <#lt> FROM ${table.name}";
    }

    @Override
    protected String getCreateQuery() {
        return
            "INSERT INTO ${table.name}(<#rt>
            <#list table.columns as column>
                ${column.name}<#sep>, <#t>
            </#list><#t>
            ) VALUES(0, <#t><#-- First parameter is autoincremented id-->
            <#list 1..table.columns?size - 1 as i>
                ?<#sep>, <#t>
            </#list>
            );";<#lt>
    }

    @Override
    protected String getUpdateQuery() {
        return
            "UPDATE ${table.name} <#rt>
            SET <#t>
            <#list table.columns as column>
                <#if column.name != "id">
                    ${column.name} = ?<#t>
                    <#sep>, <#t>
                </#if>
            </#list> <#t>
            WHERE id = ?;";<#lt>
    }

    @Override
    protected String getDeleteQuery() {
        return
            "DELETE FROM ${table.name} WHERE id = ?;";
    }

    @Override
    protected void prepareStatementForCreate(PreparedStatement statement, ${table.name?cap_first} ${table.name}) throws SQLException{
        <#list table.columns as column>
            <#if column.name != "id">
                statement.set${column.type?cap_first}(${column_index + 1}, ${table.name}.get${column.name?cap_first});
            </#if>
        </#list>
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, ${table.name?cap_first} ${table.name}) throws SQLException {
        prepareStatementForCreate(statement, object);
        statement.setInt(${table.columns?size}, object.getId());
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement statement, ${table.name?cap_first} ${table.name}) throws SQLException {
        statement.setInt(1, object.getId());
    }

    @Override
    protected List<${table.name?cap_first}in> parseResultSet(ResultSet resultSet) throws SQLException {
        List<${table.name?cap_first}> list = new LinkedList<>();
        while (resultSet.next()) {
            ${table.name?cap_first} ${table.name} = new ${table.name?cap_first}();
            <#list table.columns as column>
                ${table.name}.set${column.name?cap_first}(resultSet.get${column.type?cap_first}("${column.name}"));
            </#list>
        }
            list.add(${table.name});
        return list;
    }

    @Override
    public List<${table.name?cap_first}> getAll() throws SQLException {
        String query = "SELECT * FROM ${table.name};";
        PreparedStatement statement = connection.prepareStatement(query);
        return parseResultSet(statement.executeQuery());
    }

    @Override
    public ${table.name?cap_first} create() throws SQLException {
        ${table.name?cap_first} ${table.name} = new ${table.name?cap_first}();
        return insert(${table.name});
    }
}
