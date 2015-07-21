/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Person;

/**
 *
 * @author user
 */
public class DBConnection {
    Connection con;
    PreparedStatement pstmt;
    ResultSet rs;
    public DBConnection(){
        try{
       Class.forName("com.mysql.jdbc.Driver");
       con = DriverManager.getConnection("jdbc:mysql://localhost/hospital","root","");
        }catch(Exception e){
            System.out.println("Exception: "+e);
        }
    }
    public ArrayList<Person> getPersons(){
        ArrayList<Person> persons = new ArrayList<>();
        try {
            pstmt = con.prepareStatement("Select * from person");
            rs = pstmt.executeQuery();
            while(rs.next()){
                Person person = new Person();
                person.setId(rs.getLong("id"));
                person.setFirstName(rs.getString("first_name"));
                person.setLastName(rs.getString("last_name"));
                person.setAddress(rs.getString("address"));
                person.setPhoneNumber(rs.getString("phone_number"));
                person.setDOB(rs.getString("dob"));
                persons.add(person);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        return persons;
    }
    
     public Person getPerson(long id){
        return null;
    }
    public void savePerson(Person person){
        
    }
    public void insert(){
        
    }
    public void update(){
        
    }
    public void delete(){
        
    }       
}
