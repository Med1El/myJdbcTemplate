package ma.alami.testApp;

import ma.alami.jdbcAPI.GenericDao;
import ma.alami.jdbcAPI.GenericDaoImpl;
import ma.alami.testApp.entities.Person;

public class Main {

    public static void main(String[] args) {
        GenericDao dao = new GenericDaoImpl("test","ma.alami.testApp.entities.Person");
        Person med = new Person();
        med.setFirstName("Mohamed");
        med.setLastName("ELALAMI");
        //dao.save(med);
        //Person person = (Person) dao.findByParam("firstName","Mo");
        //med.setId(4L);
        //med.setFirstName("Med");
        //dao.update(med);
        //dao.delete(3L);
        //System.out.println(person);
        System.out.println(dao.findAll());

    }
}
