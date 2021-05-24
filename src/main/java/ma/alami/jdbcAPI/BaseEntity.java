package ma.alami.jdbcAPI;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseEntity {

    private String tableName;
    private Long id;

    public BaseEntity(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public abstract Map<String,String> getColumnsMap();

    public Map<String,String> getBaseColumnMap(){
        Map<String,String> map = new HashMap<String, String>();
        map.put("id","id");
        return map;
    }
}
