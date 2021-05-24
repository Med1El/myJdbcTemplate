package ma.alami.testApp.entities;

import ma.alami.jdbcAPI.BaseEntity;

import java.util.HashMap;
import java.util.Map;

public class Person extends BaseEntity {

    public Person() {
        super("person");
    }

    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public Map<String, String> getColumnsMap(){

        Map<String,String> columns = new HashMap<String, String>();
        columns.put("firstName","first_name");
        columns.put("lastName","last_name");

        return columns;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + super.getId() + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
