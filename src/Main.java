import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.collections.*;
import javafx.stage.Window;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class Main extends Application {

    Scene mainscene;
    Menu filemenu, editmenu, helpmenu;
    Stage window;
    Database dbObject = new Database();
    TableView<Employee> table;

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        dbObject.connect_toDB();

        window = primaryStage;
        window.setTitle("Employee App");
        //window.setMaxHeight(250);
        //window.setMaxWidth(750);

        //MENU
        // File menu
        filemenu = new Menu("_File");


        MenuItem newEmployee = new MenuItem("New employee");
        newEmployee.setOnAction(e -> scene1());
        filemenu.getItems().add(newEmployee);

        MenuItem viewEmployees = new MenuItem("View employees");
        viewEmployees.setOnAction(e -> scene2());
        filemenu.getItems().add(viewEmployees);

        filemenu.getItems().add(new SeparatorMenuItem());

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e -> {
            boolean result = AlertBox.closeProgram("Exit", "Are you sure you want to exit?");
            if(result){
                window.close();
            }
            if (!result){
                window.setScene(mainscene);
            }
        });
        filemenu.getItems().add(exit);

        // Edit menu
        editmenu = new Menu("_Edit");

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(filemenu, editmenu);

        // Main scene and layout
        BorderPane mainlayout = new BorderPane();
        mainlayout.setTop(menuBar);

        Label label = new Label("Welcome");
        mainlayout.setCenter(label);

        mainscene = new Scene(mainlayout, 750, 500);
        window.setScene(mainscene);
        window.show();

    }

    // Create window
    public void scene1 (){
        window.setTitle("Employee App");
        Label label = new Label("Fill the given form to create a new employee");


        GridPane grid = new GridPane();
        grid.setPadding(new Insets(40, 40, 40, 40));
        grid.setVgap(30);
        grid.setHgap(20);

        GridPane.setConstraints(label, 1, 0);

        Label fname = new Label("First_Name ");
        GridPane.setConstraints(fname, 5, 0);
        TextField fname_Input = new TextField();
        GridPane.setConstraints(fname_Input, 6, 0);

        Label lname = new Label("Last_Name ");
        GridPane.setConstraints(lname, 5, 1);
        TextField lname_Input = new TextField();
        GridPane.setConstraints(lname_Input, 6, 1);

        Label email = new Label("Email ");
        GridPane.setConstraints(email, 5, 2);
        TextField email_Input = new TextField();
        GridPane.setConstraints(email_Input, 6, 2);

        Label phone = new Label("Phone ");
        GridPane.setConstraints(phone, 5, 3);
        TextField phone_Input = new TextField();
        GridPane.setConstraints(phone_Input, 6, 3);


        Button submit = new Button("Submit");
        Button cancel = new Button("Cancel");

        submit.setOnAction(e -> {
            if(fname_Input.getText()!="" && lname_Input.getText()!="" && email_Input.getText()!="" && phone_Input.getText()!="") {
                boolean result = AlertBox.create("New Employee", "Are you sure you want to create this employee");
                if (result) {
                    if(isInt(phone_Input)){
                        AlertBox.success("New employee created!");
                        employee(fname_Input, lname_Input, email_Input, phone_Input);
                        window.setScene(mainscene);
                    }
                }
                if (!result) {
                    window.setScene(mainscene);
                }
            }else {
                TextField inputs[] = {fname_Input, lname_Input, email_Input, phone_Input};
                for(TextField x: inputs){
                    if(x.getText()==""){
                        x.setPromptText("Please fill this blank entry");
                    }
                }
            }
            });

        cancel.setOnAction(e -> {
            boolean result = AlertBox.cancel("Are you sure you want to cancel?");
            if (result){
                window.setScene(mainscene);
            }
        });


        GridPane.setConstraints(submit, 5, 7);
        GridPane.setConstraints(cancel, 5, 8);

        grid.getChildren().addAll(fname, fname_Input,lname, lname_Input,email, email_Input, phone, phone_Input, submit, cancel);
        Scene temp1 = new Scene(grid, 750, 460);
        window.setScene(temp1);
        window.show();

    }

    // setting up the employee table to be displayed
    public ObservableList<Employee> Table (){

        ObservableList<Employee> employee = FXCollections.observableArrayList();

        ArrayList<String> names = dbObject.EmployeeNames();
        String[] Names = names.toArray(new String[names.size()]);

        ArrayList<String> emails = dbObject.EmployeesEmails();
        String[] Emails = emails.toArray(new String[emails.size()]);

        ArrayList<Integer> phones = dbObject.EmployeesPhones();
        Integer[] Phones = phones.toArray(new Integer[phones.size()]);

        int i = 0;
        while ((i < Names.length) && (i < Emails.length) && (i < Phones.length)){
            employee.add(new Employee(Names[i], Emails[i], Phones[i]));
            i++;
        }

        return employee;
    }

    // Edit window
    public void scene2 (){

        window.setTitle("Employee App");

        VBox layout;
        GridPane grid;
        Scene scene;

        TableColumn<Employee, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(300);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));

        TableColumn<Employee, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setMinWidth(200);
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Employee, String> telColumn = new TableColumn<>("Phone");
        telColumn.setMinWidth(200);
        telColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));


        table = new TableView<>();
        table.setItems(Table());
        table.getColumns().addAll(nameColumn, emailColumn, telColumn);


        Button edit = new Button("edit");
        edit.setOnAction(e -> scene3());
        GridPane.setConstraints(edit, 1, 1);

        Button delete = new Button("delete");
        delete.setOnAction(e -> {
            Boolean result = AlertBox.delete("Delete Employee", "Are you sure you want to remove this employee?");
            if(result) {
                deleteEmployee();
            }
        });
        GridPane.setConstraints(delete, 2, 1);

        Button back = new Button("return");
        back.setOnAction(e -> {
            boolean result = AlertBox.cancel("Are you sure you want to return?");
            if (result){
                window.setScene(mainscene);
            }
        });
        GridPane.setConstraints(back, 27, 1);


        layout =new VBox();
        grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(3);
        grid.setHgap(20);
        grid.getChildren().addAll(edit, delete, back);

        layout.getChildren().addAll(table, grid);

        scene = new Scene(layout, 750, 460);
        window.setScene(scene);

    }


    public void scene3(){

        Stage box = new Stage();
        VBox layout = new VBox();
        GridPane grid = new GridPane();

        box.initModality(Modality.APPLICATION_MODAL);
        box.setTitle("Update Employee");
        box.setMinWidth(550);
        box.setMinHeight(300);

        grid.setPadding(new Insets(40, 40, 40, 40));
        grid.setVgap(30);
        grid.setHgap(20);

        Label fname = new Label("First_Name ");
        GridPane.setConstraints(fname, 5, 0);
        TextField fname_Input = new TextField();
        GridPane.setConstraints(fname_Input, 6, 0);

        Label lname = new Label("Last_Name ");
        GridPane.setConstraints(lname, 5, 1);
        TextField lname_Input = new TextField();
        GridPane.setConstraints(lname_Input, 6, 1);

        Label email = new Label("Email ");
        GridPane.setConstraints(email, 5, 2);
        TextField email_Input = new TextField();
        GridPane.setConstraints(email_Input, 6, 2);

        Label phone = new Label("Phone ");
        GridPane.setConstraints(phone, 5, 3);
        TextField phone_Input = new TextField();
        GridPane.setConstraints(phone_Input, 6, 3);


        Button update = new Button("update");
        GridPane.setConstraints(update, 5, 5);

        Button cancel = new Button("cancel");
        cancel.setOnAction(e -> scene2());
        GridPane.setConstraints(cancel, 7, 5);

        grid.getChildren().addAll(fname, fname_Input,lname, lname_Input,email, email_Input, phone, phone_Input, update, cancel);
        layout.getChildren().addAll(grid);

        Scene scene = new Scene(layout);
        box.setScene(scene);
        box.showAndWait();


    }



    //others

    //Validating text fields
    private boolean isInt (TextField input) {
        try {
            int num = Integer.parseInt(input.getText());
            return true;
        } catch (Exception e) {
            AlertBox.error("Input an integer in the Phone entry");
            return false;
        }
    }

    //Retrieving data for the database
    public void employee (TextField fn_input, TextField ln_input, TextField em_input, TextField p_input){
        String firstName = fn_input.getText();
        String lastName = ln_input.getText();
        String fullName = firstName + " " + lastName;
        String email = em_input.getText();
        int phone = Integer.parseInt(p_input.getText());
        dbObject.createEmployee(fullName, email, phone);

    }

    public void deleteEmployee(){

        ObservableList<Employee> selected, allEmployees;
        allEmployees = table.getItems();
        selected = table.getSelectionModel().getSelectedItems();

        String employeeEmail = selected.get(0).getEmail();
        dbObject.deleteEmployee(employeeEmail);
        selected.forEach(allEmployees::remove);

    }




}

