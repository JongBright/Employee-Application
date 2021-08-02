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
            String formula = "CREATE TABLE employees ("
                    + "Full_Name varchar(100),"
                    + "Email varchar(100),"
                    + "Phone int"
                    + ")";
            Statement stmt = cursor.createStatement();
            stmt.execute(formula);
            System.out.println("success");

        }catch (Exception e){
            System.out.println(e);
        }

    }


    public void createEmployee(String name, String email, int tel){
        try {
            PreparedStatement stmt = cursor.prepareStatement("INSERT INTO employees VALUES (?, ?, ?)");
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setInt(3, tel);
            stmt.execute();
            System.out.println("success");
            stmt.close();
        }catch (Exception e){
            System.out.println(e);
        }

    }

    public ArrayList<Integer> EmployeesPhones() {

        ArrayList<Integer> employeePhones = new ArrayList<>();

        try {
            Statement stmt = cursor.createStatement();
            ResultSet employee = stmt.executeQuery("SELECT * FROM employees");

            while (employee.next()) {
                int phone = employee.getInt("Phone");
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
            ResultSet employee = stmt.executeQuery("SELECT * FROM employees");

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
            ResultSet employee = stmt.executeQuery("SELECT * FROM employees");

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


    //public void updateEmployee(String name, String email, int tel){

    //}


    public void deleteEmployee(String email){
        try {
            PreparedStatement stmt = cursor.prepareStatement("DELETE FROM employees WHERE Email = ?");
            stmt.setString(1, email);
            stmt.executeUpdate();
            System.out.println("success");
            stmt.close();
        }catch (Exception e){
            System.out.println(e);
        }

    }





}
