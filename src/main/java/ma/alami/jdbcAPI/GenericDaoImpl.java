package ma.alami.jdbcAPI;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenericDaoImpl implements GenericDao<BaseEntity> {

    private Connection connection;
    private String entityClassName;
    private BaseEntity baseEntity;


    public GenericDaoImpl(String database, String entity) {
        connection = SingletonConnection.getConnection(database);
        this.entityClassName = entity;

    }

    public void setEntityClassName(String entityClassName) {
        this.entityClassName = entityClassName;
    }

    private BaseEntity getNewEntityInstance(){
        try {
            return (BaseEntity) Class.forName(entityClassName).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    @Override
    public BaseEntity save(BaseEntity baseEntity) {

        String queryStart = "INSERT INTO " + baseEntity.getTableName() + " (";
        String queryEnd = "VALUES (";
        int i = 0;
        for (String column : baseEntity.getColumnsMap().values()) {
            queryStart += column;
            queryEnd += "?";
            if (i < baseEntity.getColumnsMap().size() - 1) {
                queryStart += ", ";
                queryEnd += ", ";
                i++;
            }
        }
        queryStart += ") ";
        queryEnd += ");";

        PreparedStatement ps = null;


        try {
            ps = connection.prepareStatement(queryStart + queryEnd);
            i = 1;
            Field field;
            for (String attr : baseEntity.getColumnsMap().keySet()) {
                field = baseEntity.getClass().getDeclaredField(attr);
                field.setAccessible(true);
                ps.setObject(i++, field.get(baseEntity));
            }

            int result;
            result = ps.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return baseEntity;
    }


    @Override
    public BaseEntity findByParam(String param, String value) {
        this.baseEntity = getNewEntityInstance();
        String query = "SELECT * FROM " + baseEntity.getTableName() + " WHERE " + baseEntity.getColumnsMap().get(param) + "=?";
        PreparedStatement ps;
        Field field;
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, value);
            ResultSet rs = ps.executeQuery();
            rs.next();


            for (String attr : baseEntity.getColumnsMap().keySet()) {

                field = baseEntity.getClass().getDeclaredField(attr);
                field.setAccessible(true);
                Object o = rs.getObject(baseEntity.getColumnsMap().get(attr));
                field.set(baseEntity, o);

            }

            for (String attr : baseEntity.getBaseColumnMap().keySet()) {

                field = baseEntity.getClass().getSuperclass().getDeclaredField(attr);
                field.setAccessible(true);
                Long o  = rs.getLong(baseEntity.getBaseColumnMap().get(attr));
                field.set(baseEntity, o);

            }


        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

        return baseEntity;


    }

    @Override
    public BaseEntity update(BaseEntity baseEntity) {
        String query = "UPDATE " + baseEntity.getTableName() + " SET ";
        int i = 0;
        for (String column : baseEntity.getColumnsMap().values()) {
            query += column + "=?";
            if (i < baseEntity.getColumnsMap().size() - 1) {
                query += ", ";
                i++;
            }
        }
        query += "WHERE id=?";

        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(query);

            i = 1;
            Field field;
            for (String attr : baseEntity.getColumnsMap().keySet()) {
                field = baseEntity.getClass().getDeclaredField(attr);
                field.setAccessible(true);
                ps.setObject(i++, field.get(baseEntity));

            }
            field = baseEntity.getClass().getSuperclass().getDeclaredField("id");
            field.setAccessible(true);
            ps.setObject(i, field.get(baseEntity));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        int result = -2;

        try {
            result = ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return baseEntity;

    }

    @Override
    public boolean delete(Long id) {
        this.baseEntity = getNewEntityInstance();
        String query = "DELETE FROM " + baseEntity.getTableName() + " WHERE id=?";
        try {

            PreparedStatement ps = connection.prepareStatement(query);
            ;
            ps.setLong(1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }

    }

    @Override
    public List<BaseEntity> findAll() {
        String query = "SELECT * FROM " + baseEntity.getTableName();
        PreparedStatement ps;
        Field field;
        List<BaseEntity> list = new ArrayList<>();
        try {
            ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery();


            while (rs.next()) {
                for (String attr : baseEntity.getColumnsMap().keySet()) {

                    field = baseEntity.getClass().getDeclaredField(attr);
                    field.setAccessible(true);
                    Object o = rs.getObject(baseEntity.getColumnsMap().get(attr));
                    field.set(baseEntity, o);

                }
                for (String attr : baseEntity.getBaseColumnMap().keySet()) {

                    field = baseEntity.getClass().getSuperclass().getDeclaredField(attr);
                    field.setAccessible(true);
                    Long o  = rs.getLong(baseEntity.getBaseColumnMap().get(attr));
                    field.set(baseEntity, o);

                }
                list.add(baseEntity);
                baseEntity=getNewEntityInstance();

            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

        return list;

    }
}
