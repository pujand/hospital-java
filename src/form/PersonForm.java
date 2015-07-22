/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import dbconnection.DBConnection;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import model.Person;
import net.sourceforge.jdatepicker.JDatePicker;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

/**
 *
 * @author user
 */
public class PersonForm extends JFrame implements ActionListener{
    JLabel lblFirstName, lblLastName, lblAddress, lblPhoneNumber, lblDOB;
    JTextField tfFirstName, tfLastName, tfAddress, tfPhoneNumber, tfDOB;
    JButton btnSave, btnReset, btnDelete;
    Person person;
    
    UtilDateModel dateModel;
    
    public PersonForm(){
        setLayout(new FlowLayout());
        setTitle("Person Form");
        lblFirstName = new JLabel("FIrst Name: ");
        lblLastName = new JLabel("Last Name:");
        lblAddress = new JLabel("Address: ");
        lblPhoneNumber = new JLabel("Phone Number: ");
        lblDOB = new JLabel("Date of Birth: ");
        tfFirstName = new JTextField(20);
        tfLastName = new JTextField(20);
        tfAddress = new JTextField(20);
        tfPhoneNumber = new JTextField(15);
        tfDOB = new JTextField(10);
        btnSave = new JButton("Save");
        btnReset = new JButton("Reset");
        btnDelete = new JButton("Delete");
        
        dateModel = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel);        
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        
        add(lblFirstName);
        add(tfFirstName);
        add(lblLastName);
        add(tfLastName);
        add(lblAddress);
        add(tfAddress);
        add(lblPhoneNumber);
        add(tfPhoneNumber);
        add(lblDOB);
//        add(tfDOB);        
        add(datePicker);
        add(btnSave);
        add(btnReset);
        add(btnDelete);
        btnDelete.setVisible(false);
        setVisible(true);
        setSize(300, 300);
        btnSave.addActionListener(this);
        btnReset.addActionListener(this);
        btnDelete.addActionListener(this);
    }
    public PersonForm(Person person){
        this();
        btnDelete.setVisible(true);
        updateFields(person);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btnSave){
            if(tfFirstName.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Enter first name!");
                return;
            }else if(tfLastName.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Enter last name!");
                return;
            }else if(tfAddress.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Enter Address!");
                return;
            }else if(tfPhoneNumber.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Enter phone number!");
                return;
            }else if(dateModel.getValue().toString().isEmpty()){
                JOptionPane.showMessageDialog(null, "Enter date of birth!");
                return;
            }
            DBConnection dbconnection = new DBConnection();
            
            if(person==null){                
                person = new Person();           
            }
            
            person.setFirstName(tfFirstName.getText());
            person.setLastName(tfLastName.getText());
            person.setAddress(tfAddress.getText());
            person.setPhoneNumber(tfPhoneNumber.getText());
            person.setDOB(dateModel.getYear()+"-"+dateModel.getMonth()+"-"+dateModel.getDay());
            int result= -1;
            if(person.getId()==0){
                result = dbconnection.savePerson(person);
            }else{
                result = dbconnection.updatePerson(person);
            }
            
            if(result!=-1){
                JOptionPane.showMessageDialog(null, "Saved Successfully!");
            }else{
                JOptionPane.showMessageDialog(null, "Save Unsuccessful!");
            }
        }else if(e.getSource()==btnReset){
            tfFirstName.setText("");
            tfLastName.setText("");
            tfAddress.setText("");
            tfPhoneNumber.setText("");
            tfDOB.setText("");
            dateModel.setValue(new Date());
        }else if(e.getSource()==btnDelete){
            DBConnection dBConnection = new DBConnection();
            int result= dBConnection.deletePerson(person.getId());
            if(result!=-1){
                JOptionPane.showMessageDialog(null, "Deleted Successfully!");
            }else{
                JOptionPane.showMessageDialog(null, "Deletion Unsuccessful!");
            }
            
        }
    }

    private void updateFields(Person person) {
        this.person = person;
        tfFirstName.setText(person.getFirstName());
        tfLastName.setText(person.getLastName());
        tfAddress.setText(person.getAddress());
        tfPhoneNumber.setText(person.getPhoneNumber());
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try { 
            Date result =  df.parse(person.getDOB());            
            dateModel.setValue(result);
            dateModel.setSelected(true);
        } catch (ParseException ex) {
            Logger.getLogger(PersonForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
}
