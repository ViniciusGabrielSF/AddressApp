/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.makery.address.jdbc;

import ch.makery.address.MainApp;
import ch.makery.address.model.Person;
import ch.makery.address.model.PersonListWrapper;
import ch.makery.address.util.DateUtil;
import ch.makery.address.util.PersonDataFile;
import java.io.File;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

// import java.util.Date;
import java.sql.Date;
import java.util.List;
import javafx.scene.control.Alert;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;




/**
 *
 * @author Vinícius
 */
public class AcessDB {
    
    public String drive, user, password, url;
    public Person person;
    private List<Person> persons;


    public AcessDB(){
        drive = "org.postgresql.Driver" ;
        user = "postgres";
        password = "123456";
        url = "jdbc:postgresql:Pessoas";

    }

    public AcessDB(String drive, String user, String password, String url) {
        this.drive = drive;
        this.user = user;
        this.password = password;
        this.url = url;
    }


    private Connection acess(){
        try {
            Class.forName(drive);
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexão realizada com Sucesso!");
            
            return connection;
        } catch (ClassNotFoundException | SQLException ex) {
            return null;
        }
    }


    public void loadPersonDataFromDB() {
        try {
            Connection connection = acess();
            PreparedStatement select = connection.prepareStatement("SELECT * FROM PESSOA");
            ResultSet result = select.executeQuery();
            
            String firstName,lastName,street,city;
            int postalCode;
            Date birthday;
            persons = new ArrayList<Person>();
            while(result.next()){
                
                firstName = result.getString("firstname");
                lastName = result.getString("lastname");
                street = result.getString("street");
                city = result.getString("city");
                postalCode = result.getInt("postalcode");
                birthday =  result.getDate("birthday");
                
                LocalDate localBirthday = DateUtil.toLocalDateFromSqlDate(birthday);
                person = new Person(firstName, lastName, street, postalCode, city, localBirthday );
                persons.add(person);
            }
            MainApp.getInstance().getPersonData().clear();
            MainApp.getInstance().getPersonData().addAll(persons);
            connection.close();
        } catch (SQLException | NullPointerException ex ) {
            
            PersonDataFile personDataFile = MainApp.getInstance().getPersonDataFile();
            File file = personDataFile.getPersonFilePath().getPersonFilePath();
            if (file != null) {
                personDataFile.loadPersonDataFromFile(file);
                return;
            }
        }

    }
    
    public void savePersonDataToDB(List<Person> persons) throws Exception {
        try {
            Connection connection = acess();
            PreparedStatement delete = connection.prepareStatement("DELETE FROM PESSOA");
            ResultSet result = delete.executeQuery();
            PreparedStatement insert = connection
                    .prepareStatement("INSERT INTO pessoa (firstname,lastname,street,city,postalCode,birthday) VALUES (?,?,?,?,?,?);");
            int i = 0;
            
            for (Person person : persons) {
                insert.setString(1, person.getFirstName());
                insert.setString(2, person.getLastName());
                insert.setString(3, person.getStreet());
                insert.setString(4, person.getCity());
                insert.setInt(5, person.getPostalCode());
                Date sqlDate = (Date) DateUtil.toDateViaSqlDate(person.getBirthday());
                insert.setDate(6, sqlDate);
                insert.executeQuery();
                
            } 
            connection.close();
        }catch ( SQLException | NullPointerException ex) {
            System.out.println("ME MAMA LEKEEEEEEE");
            throw new Exception();
        }
    }
}
