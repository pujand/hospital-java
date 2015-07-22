/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import dbconnection.DBConnection;
import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import model.Person;

/**
 *
 * @author user
 */
public class Search extends JFrame implements ActionListener{
    JLabel lblName = new JLabel("Name");
    JTextField jfName = new JTextField(20);
    JButton btnSearch= new JButton("Search");
    JButton btnNew = new JButton("New");
    JTable table;
    ArrayList<Person> persons;
    public Search(){
        DBConnection dbconnection = new DBConnection();
        persons = dbconnection.getPersons();  
        
        table = new JTable(buildTableModel(persons));
        new JFrame();
        setLayout(new FlowLayout());
        add(lblName);
        add(jfName);
        add(btnSearch); 
        add(btnNew);
        getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
    pack();
        setVisible(true);
        setSize(500, 500);
        setTitle("Search Form");
        btnSearch.addActionListener(this);
        btnNew.addActionListener(this);
        
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
        public void valueChanged(ListSelectionEvent event) {
            // do some actions here, for example
            // print first column value from selected row
            if(!event.getValueIsAdjusting()){
                System.out.println(""+table.getSelectedRow());
                new PersonForm(persons.get(table.getSelectedRow()));
            }
        }
    });
    }
    public static void main(String args[]){
        new Search();
    }
    
    /**
     *
     * @param persons
     * @return
     */
    public static DefaultTableModel buildTableModel(ArrayList<Person> persons){
        // names of columns
        Vector<String> columnNames = new Vector<String>();
        columnNames.add("First Name");
        columnNames.add("Last Name");
        columnNames.add("Address");
        columnNames.add("Phone Number");
        columnNames.add("Date of Birth");
        
        
        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        for (Person person: persons) {
                Vector<Object> vector = new Vector<Object>();
                    vector.add(person.getFirstName());
                    vector.add(person.getLastName());
                    vector.add(person.getAddress());
                    vector.add(person.getPhoneNumber());
                    vector.add(person.getDOB());
                    data.add(vector);
        }
        return new DefaultTableModel(data, columnNames);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btnSearch){
            DBConnection dbconnection = new DBConnection();
            persons = dbconnection.searchPerson(jfName.getText());          
            table.setModel(buildTableModel(persons));  
        }else if(e.getSource()==btnNew){
            new PersonForm();
        }
    }
}
