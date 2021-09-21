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
        window.setMaxHeight(470);
        window.setMaxWidth(750);
        window.setMinHeight(470);
        window.setMinWidth(750);
        window.setHeight(470);
        window.setWidth(750);

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
        VBox vbox1 = new VBox();
        VBox vbox2 = new VBox();
        HBox hbox = new HBox();

        mainlayout.setTop(menuBar);
        mainlayout.setRight(vbox1);
        mainlayout.setLeft(vbox2);
        mainlayout.setBottom(hbox);

        TextArea blank = new TextArea();
        blank.setText("");
        blank.setMinWidth(500);
        blank.setMinHeight(300);

        Label label = new Label("BRIGHT AI Employee App");

        Button create = new Button("Create");
        create.setMinWidth(100);
        create.setMinHeight(50);
        create.setOnAction(e -> {
            Boolean result = AlertBox.alert("Create", "Do you want to create a new employee");
            if(result){
                scene1();
            }

        });

        Button list = new Button("Employees");
        list.setMinWidth(100);
        list.setMinHeight(50);
        list.setOnAction(e -> {
            Boolean result = AlertBox.alert("View employees", "Do you want to see all employees?");
            if(result){
                scene2();
            }

        });

        Button search = new Button("Search");
        search.setOnAction(e -> search());
        search.setMinWidth(100);
        search.setMinHeight(50);

        Button delete = new Button("Delete");
        delete.setOnAction(e -> delete());
        delete.setMinWidth(100);
        delete.setMinHeight(50);

        hbox.setPadding(new Insets(10, 10, 10, 270));
        hbox.getChildren().add(label);
        vbox1.setPadding(new Insets(30, 30, 30, 30));
        vbox1.setSpacing(30);
        vbox1.getChildren().addAll(create, list, search, delete);
        vbox2.setPadding(new Insets(30, 30, 30, 30));
        vbox2.getChildren().add(blank);

        mainscene = new Scene(mainlayout, 750, 500);
        window.setScene(mainscene);
        window.show();

    }

    // Create window
    public void scene1 (){
        window.setTitle("Employee App");
        GridPane grid = new GridPane();
        Label label = new Label("                 @EMPLOYEE FORM                                             ");
        GridPane.setConstraints(label, 7, 0);

        grid.setPadding(new Insets(40, 40, 40, 40));
        grid.setVgap(30);
        grid.setHgap(20);


        Label fname = new Label("First_Name:");
        GridPane.setConstraints(fname, 6, 2);
        TextField fname_Input = new TextField();
        GridPane.setConstraints(fname_Input, 7, 2);

        Label lname = new Label("Last_Name:");
        GridPane.setConstraints(lname, 6, 3);
        TextField lname_Input = new TextField();
        GridPane.setConstraints(lname_Input, 7, 3);

        Label email = new Label("Email:");
        GridPane.setConstraints(email, 6, 4);
        TextField email_Input = new TextField();
        GridPane.setConstraints(email_Input, 7, 4);

        Label phone = new Label("Phone:");
        GridPane.setConstraints(phone, 6, 5);
        TextField phone_Input = new TextField();
        GridPane.setConstraints(phone_Input, 7, 5);


        Button submit = new Button("Submit");
        Button cancel = new Button("Cancel");

        ArrayList<String> existing_emails = dbObject.EmployeesEmails();
        ArrayList<Integer> existing_tels = dbObject.EmployeesPhones();

        submit.setOnAction(e -> {
            if(fname_Input.getText()!="" && lname_Input.getText()!="" && email_Input.getText()!="" && phone_Input.getText()!="") {
                if(isInt(phone_Input)){
                    boolean result = AlertBox.alert("New", "Are you sure you want to create this employee");
                    boolean email_exists = existing_emails.contains(email_Input.getText());
                    boolean tel_exists = existing_tels.contains(phone_Input.getText());
                    if(result && !email_exists && !tel_exists) {
                        employee(fname_Input, lname_Input, email_Input, phone_Input);
                        AlertBox.success("New employee created!");
                        //window.setScene(mainscene);
                    }
                    if (result && (email_exists || tel_exists)) {
                        AlertBox.error("Employee not added.Either employee email, phone or both already exists");
                    }
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


        GridPane.setConstraints(submit, 6, 7);
        GridPane.setConstraints(cancel, 8, 7);

        grid.getChildren().addAll(label, fname, fname_Input,lname, lname_Input,email, email_Input, phone, phone_Input, submit, cancel);
        Scene temp1 = new Scene(grid, 750, 470);
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
        delete.setOnAction(e -> deleteEmployee());
        GridPane.setConstraints(delete, 2, 1);

        Button back = new Button("return");
        back.setOnAction(e -> window.setScene(mainscene));
        GridPane.setConstraints(back, 27, 1);


        layout =new VBox();
        grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(3);
        grid.setHgap(20);
        grid.getChildren().addAll(edit, delete, back);

        layout.getChildren().addAll(table, grid);

        scene = new Scene(layout, 750, 470);
        window.setScene(scene);

    }


    public void scene3(){

        ObservableList<Employee> selected, allEmployees;
        allEmployees = table.getItems();
        selected = table.getSelectionModel().getSelectedItems();
        String id = updateEmployeeEmail();

        if(selected.size()>0){
            Stage box = new Stage();
            VBox layout = new VBox();
            GridPane grid = new GridPane();

            box.initModality(Modality.APPLICATION_MODAL);
            box.setTitle("Edit Employee");
            box.setMinWidth(550);
            box.setMinHeight(300);

            grid.setPadding(new Insets(40, 40, 40, 40));
            grid.setVgap(30);
            grid.setHgap(20);

            Label fname = new Label("First_Name:");
            GridPane.setConstraints(fname, 5, 0);
            TextField fname_Input = new TextField();
            fname_Input.setText(updateEmployeeFname());
            GridPane.setConstraints(fname_Input, 6, 0);

            Label lname = new Label("Last_Name:");
            GridPane.setConstraints(lname, 5, 1);
            TextField lname_Input = new TextField();
            lname_Input.setText(updateEmployeeLname());
            GridPane.setConstraints(lname_Input, 6, 1);

            Label email = new Label("Email:");
            GridPane.setConstraints(email, 5, 2);
            TextField email_Input = new TextField();
            email_Input.setText(updateEmployeeEmail());
            GridPane.setConstraints(email_Input, 6, 2);

            Label phone = new Label("Phone:");
            GridPane.setConstraints(phone, 5, 3);
            TextField phone_Input = new TextField();
            phone_Input.setText(updateEmployeeTel().toString());
            GridPane.setConstraints(phone_Input, 6, 3);


            Button update = new Button("update");
            update.setOnAction(e -> {
                if(fname_Input.getText()!="" && lname_Input.getText()!="" && email_Input.getText()!="" && phone_Input.getText()!="") {
                    if(isInt(phone_Input)){
                        boolean result = AlertBox.alert("Update", "Are you sure you want to update this employee?");
                        if (result) {
                            dbObject.updateEmployee((fname_Input.getText() + " " + lname_Input.getText()), email_Input.getText(), Integer.parseInt(phone_Input.getText()), id);
                            AlertBox.success("Employee updated succesfully!");
                            box.close();
                            scene2();
                        }
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
            GridPane.setConstraints(update, 5, 5);

            Button cancel = new Button("cancel");
            cancel.setOnAction(e -> {
                Boolean result = AlertBox.cancel("Are you sure you want to discard your changes?");
                if(result){
                    box.close();
                    scene2();
                }
            });
            GridPane.setConstraints(cancel, 7, 5);

            grid.getChildren().addAll(fname, fname_Input,lname, lname_Input,email, email_Input, phone, phone_Input, update, cancel);
            layout.getChildren().addAll(grid);

            Scene scene = new Scene(layout);
            box.setScene(scene);
            box.showAndWait();




        }

    }

    public void search(){
        Scene scene;
        GridPane grid = new GridPane();
        HBox hBox = new HBox();
        GridPane.setConstraints(hBox, 1, 1);

        Label text = new Label("    Input employee name, email or phone to search                 ");
        GridPane.setConstraints(text, 0, 0);
        Label search = new Label(" Search: ");
        GridPane.setConstraints(search, 0, 1);
        TextField input = new TextField();
        GridPane.setConstraints(input, 1, 1);

        hBox.getChildren().addAll(search, input);
        grid.getChildren().addAll(text, hBox);

        scene = new Scene(grid, 750, 470);

        window.setScene(scene);





    }



    //others

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

        if(selected.size()>0) {
            Boolean result = AlertBox.alert("Delete", "Are you sure you want to remove this employee?");
            if(result) {
                String employeeEmail = selected.get(0).getEmail();
                dbObject.deleteEmployee(employeeEmail);
                selected.forEach(allEmployees::remove);
                AlertBox.success("Employee removed succesfully");
            }
        }
    }

    public void delete(){

        Stage box = new Stage();
        VBox layout = new VBox();
        GridPane grid = new GridPane();

        box.initModality(Modality.APPLICATION_MODAL);
        box.setTitle("Delete Employee");
        box.setMinWidth(550);
        box.setMinHeight(300);

        grid.setPadding(new Insets(40, 40, 40, 40));
        grid.setVgap(30);
        grid.setHgap(20);

        Label fname = new Label("First_Name:");
        GridPane.setConstraints(fname, 5, 0);
        TextField fname_Input = new TextField();
        GridPane.setConstraints(fname_Input, 6, 0);

        Label lname = new Label("Last_Name:");
        GridPane.setConstraints(lname, 5, 1);
        TextField lname_Input = new TextField();
        GridPane.setConstraints(lname_Input, 6, 1);

        Label email = new Label("Email:");
        GridPane.setConstraints(email, 5, 2);
        TextField email_Input = new TextField();
        GridPane.setConstraints(email_Input, 6, 2);

        Label phone = new Label("Phone:");
        GridPane.setConstraints(phone, 5, 3);
        TextField phone_Input = new TextField();
        GridPane.setConstraints(phone_Input, 6, 3);

        ArrayList<String> existing_emails = dbObject.EmployeesEmails();
        ArrayList<Integer> existing_tels = dbObject.EmployeesPhones();

        Button del = new Button("Delete");
        del.setOnAction(e -> {
            if(fname_Input.getText()!="" && lname_Input.getText()!="" && email_Input.getText()!="" && phone_Input.getText()!="") {
                if(isInt(phone_Input)) {
                    boolean result = AlertBox.alert("New", "Are you sure you want to delete this employee");
                    boolean email_exists = existing_emails.contains(email_Input.getText());
                    boolean tel_exists = existing_tels.contains(phone_Input.getText());
                    if(result && email_exists && tel_exists) {
                        dbObject.deleteEmployee(email_Input.getText());
                        AlertBox.success("Employee deleted succesfully!");
                        box.close();
                    }
                    if (result && (!email_exists || !tel_exists)) {
                        AlertBox.error(" Employee not removed. Either employee email, phone or both do not exists ");
                    }
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
        GridPane.setConstraints(del, 5, 5);

        Button cancel = new Button("cancel");
        cancel.setOnAction(e -> {
            Boolean result = AlertBox.cancel("Are you sure you want to discard your changes?");
            if(result){
                box.close();
            }
        });
        GridPane.setConstraints(cancel, 7, 5);

        grid.getChildren().addAll(fname, fname_Input,lname, lname_Input,email, email_Input, phone, phone_Input, del, cancel);
        layout.getChildren().addAll(grid);

        Scene scene = new Scene(layout);
        box.setScene(scene);
        box.showAndWait();



    }



    //update methods for retrieving the employee information
    public String updateEmployeeFname(){

        ObservableList<Employee> selected, allEmployees;
        allEmployees = table.getItems();
        selected = table.getSelectionModel().getSelectedItems();

        String F_name = new String();

        if(selected.size()>0) {
            String full_name = selected.get(0).getFullName();
            String required = new String();
            required = full_name.substring(0, full_name.indexOf(" "));

            F_name = required;
        }

        return F_name;
    }
    public String updateEmployeeLname(){

        ObservableList<Employee> selected, allEmployees;
        allEmployees = table.getItems();
        selected = table.getSelectionModel().getSelectedItems();

        String L_name = new String();

        if(selected.size()>0) {
            String full_name = selected.get(0).getFullName();
            String required = new String();
            required = full_name.substring(full_name.indexOf(" ")+1);

            L_name = required;
        }

        return L_name;
    }
    public String updateEmployeeEmail(){

        ObservableList<Employee> selected, allEmployees;
        allEmployees = table.getItems();
        selected = table.getSelectionModel().getSelectedItems();

        String email = new String();

        if(selected.size()>0) {
            String required = selected.get(0).getEmail();
            email = required;
        }

        return email;
    }
    public Integer updateEmployeeTel(){

        ObservableList<Employee> selected, allEmployees;
        allEmployees = table.getItems();
        selected = table.getSelectionModel().getSelectedItems();

        Integer tel = 0;

        if(selected.size()>0) {
            Integer required = selected.get(0).getPhone();
            tel = required;
        }

        return tel;
    }
    //update methods end here


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








}

