public interface GenericDao<E>{

    public E create() throws SQLException;

    public void insert(E entity) throws SQLException;

    public E getByPrimaryKey(int id) throws SQLException;

    public void update(E entity) throws SQLException;

    public void delete(E entity) throws SQLException;

    public List<E> getAll() throws SQLException;
}