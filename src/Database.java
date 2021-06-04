import  java.sql.*;

public class Database {

    Connection cursor;

    public void connect_toDB(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cursor = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_app", "root", "brightai");
        }catch (Exception e){
            System.out.println(e);
        }

    }

    public void create_DB (){
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

    public void viewEmployees(){
        try {
            Statement stmt = cursor.createStatement();
            ResultSet employee = stmt.executeQuery("SELECT * FROM employees");
            while (employee.next()) {
                String name = employee.getString("Full_Name");
                String email = employee.getString("Email");
                int phone = employee.getInt("Phone");
            }
            stmt.close();
        }catch (Exception e){
            System.out.println(e);
        }


    }


    //public void updateEmployee(String name, String email, int tel){

    //}


    public void deleteEmployee(String email){
        try {
            PreparedStatement stmt = cursor.prepareStatement("DELETE FROM employees WHERE Email = ?");
            stmt.setString(2, email);
            stmt.executeUpdate();
            System.out.println("success");
            stmt.close();
        }catch (Exception e){
            System.out.println(e);
        }

    }





}
