package ma.alami.jdbcAPI;

import java.util.List;

public interface GenericDao<T> {

    public T save(T t); //create
    public T findByParam(String param, String value); //read
    public T update(T t);
    public boolean delete(Long id);
    public List<T> findAll();

}
