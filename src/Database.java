import  java.sql.*;
import java.util.*;

public class Database {

    Connection cursor;

    public void connect_toDB(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            cursor = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_app", "root", "brightai");
        }catch (Exception e){
            System.out.println(e);
        }

    }

    public void create_TB (){
        try {
            String formula = "CREATE TABLE storedd ("
                    + "Full_Name varchar(100),"
                    + "Email varchar(100),"
                    + "Phone varchar(100),"
                    + "Creation_Info varchar(100)"
                    + ")";
            Statement stmt = cursor.createStatement();
            stmt.execute(formula);
            System.out.println("success");

        }catch (Exception e){
            System.out.println(e);
        }

    }


    public void createEmployee(String name, String email, String tel, String creation){
        try {
            PreparedStatement stmt = cursor.prepareStatement("INSERT INTO storedd VALUES (?, ?, ?, ?)");
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, tel);
            stmt.setString(4, creation);
            stmt.execute();
            System.out.println("success");
            stmt.close();
        }catch (Exception e){
            System.out.println(e);
        }

    }

    public ArrayList<String> EmployeesPhones() {

        ArrayList<String> employeePhones = new ArrayList<>();

        try {
            Statement stmt = cursor.createStatement();
            ResultSet employee = stmt.executeQuery("SELECT * FROM storedd");

            while (employee.next()) {
                String phone = employee.getString("Phone");
                employeePhones.add(phone);

            }
            stmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return employeePhones;

    }

    public ArrayList<String> EmployeeNames(){

        ArrayList<String> employeeNames = new ArrayList<>();

        try {
            Statement stmt = cursor.createStatement();
            ResultSet employee = stmt.executeQuery("SELECT * FROM storedd");

            while (employee.next()) {
                String name = employee.getString("Full_Name");
                employeeNames.add(name);

            }
            stmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return employeeNames;

    }
    public ArrayList<String> EmployeesEmails(){

        ArrayList<String> employeeEmails = new ArrayList<>();

        try {
            Statement stmt = cursor.createStatement();
            ResultSet employee = stmt.executeQuery("SELECT * FROM storedd");

            while (employee.next()) {
                String email = employee.getString("Email");
                employeeEmails.add(email);

            }
            stmt.close();
        }catch (Exception e){
            System.out.println(e);
        }

        return employeeEmails;

    }

    public ArrayList<String> EmployeesCreationInfo(){

        ArrayList<String> employeeCreationInfo = new ArrayList<>();

        try {
            Statement stmt = cursor.createStatement();
            ResultSet employee = stmt.executeQuery("SELECT * FROM storedd");

            while (employee.next()) {
                String ci = employee.getString("Creation_Info");
                employeeCreationInfo.add(ci);

            }
            stmt.close();
        }catch (Exception e){
            System.out.println(e);
        }

        return employeeCreationInfo;

    }


    public void updateEmployee(String name, String email, String tel, String id){

        try {
            PreparedStatement stmt = cursor.prepareStatement(
                    "UPDATE storedd " +
                        "SET Full_Name = ?, Email = ?, Phone = ? " +
                        "WHERE Email = ?");

            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, tel);
            stmt.setString(4, id);
            stmt.executeUpdate();
            stmt.close();


            System.out.println("success");

        }catch (Exception e){
            System.out.println(e);
        }


    }


    public void deleteEmployee(String email){
        try {
            PreparedStatement stmt = cursor.prepareStatement("DELETE FROM storedd WHERE Email = ?");
            stmt.setString(1, email);
            stmt.executeUpdate();
            System.out.println("success");
            stmt.close();
        }catch (Exception e){
            System.out.println(e);
        }

    }





}
