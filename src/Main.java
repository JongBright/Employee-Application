import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.Parent;
import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.collections.*;
//import javafx.stage.Window;
//import javax.swing.*;
//import java.sql.ResultSet;
//import java.sql.Statement;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;




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
        //dbObject.create_TB();

        window = primaryStage;
        window.setTitle("Mann-E");
        window.getIcons().add(new Image("app-icons/cc-app-icon.ico"));
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)size.getWidth();
        int height = (int)size.getHeight();
        window.setMaxHeight(height);
        window.setMaxWidth(width);
        window.setMinHeight(height);
        window.setMinWidth(width);
        window.setHeight(height);
        window.setWidth(width);


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

        //help menu
        helpmenu = new Menu("_Help");
        helpmenu.setOnAction( e -> AlertBox.success("For any queries, questions or proposals concerning Mann-E, visit www.softstarz.com"));

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(filemenu, helpmenu);

        // Main scene and layout
        BorderPane mainlayout = new BorderPane();
        VBox vbox1 = new VBox();
        VBox vbox2 = new VBox();
        HBox hbox = new HBox();

        mainlayout.setTop(menuBar);
        mainlayout.setRight(vbox1);
        mainlayout.setLeft(vbox2);
        mainlayout.setBottom(hbox);

//        add the app logo picture here
//        blank.setMinWidth(500);
//        blank.setMinHeight(300);

        Label label = new Label("");

        //submit button
        Image create_img = new Image("button-icons/home-create-icon.png");
        ImageView aview = new ImageView(create_img);
        aview.setFitHeight(100);
        aview.setPreserveRatio(true);
        Button create = new Button("Create");
        create.setTranslateX(25);
        create.setTranslateY(25);
        create.setPrefSize(250, 115);
        create.setGraphic(aview);
        create.setStyle("-fx-background-radius: 100;");

        create.setOnAction(e -> {
            scene1();
        });


        Image list_img = new Image("button-icons/home-list-icon.png");
        ImageView lview = new ImageView(list_img);
        lview.setFitHeight(100);
        lview.setPreserveRatio(true);
        Button list = new Button("Employees");
        list.setTranslateX(25);
        list.setTranslateY(25);
        list.setPrefSize(250, 115);
        list.setGraphic(lview);
        list.setStyle("-fx-background-radius: 100;");

        list.setOnAction(e -> {
            scene2();
        });


        Image delete_img = new Image("button-icons/delete-icon.jpg");
        ImageView dview = new ImageView(delete_img);
        dview.setFitHeight(100);
        dview.setPreserveRatio(true);
        Button delete = new Button("Delete");
        delete.setTranslateX(25);
        delete.setTranslateY(25);
        delete.setPrefSize(250, 115);
        delete.setGraphic(dview);
        delete.setStyle("-fx-background-radius: 100;");

        delete.setOnAction(e -> delete());


        Image info_img = new Image("button-icons/home-info-icon.png");
        ImageView iview = new ImageView(info_img);
        iview.setFitHeight(100);
        iview.setPreserveRatio(true);
        Button info = new Button("Info  ");
        info.setTranslateX(25);
        info.setTranslateY(25);
        info.setPrefSize(250, 115);
        info.setGraphic(iview);
        info.setStyle("-fx-background-radius: 100;");

//        info.setOnAction(e -> info());


        hbox.setPadding(new Insets(10, 10, 10, 270));
        hbox.getChildren().add(label);
        vbox1.setPadding(new Insets(40, 50, 50, 50));
        vbox1.setSpacing(30);
        vbox1.getChildren().addAll(create, list, delete, info);

        vbox2.setPadding(new Insets(30, 30, 30, 30));
//        Image image = new Image("images/home-image.jpg");
//        ImageView imageView = new ImageView();
//        imageView.setImage(image);
//        imageView.setX(500);
//        imageView.setY(200);
//        imageView.setFitWidth(575);
//        imageView.setPreserveRatio(true);
//        vbox2.getChildren().add(imageView);

        mainscene = new Scene(mainlayout, 750, 500);
        mainscene.getStylesheets().add("stylesheets/styler1.css");
        window.setScene(mainscene);
        window.show();

    }

    // Create window
    public void scene1 (){
        window.setTitle("Mann-E");
        GridPane grid = new GridPane();
        Label label = new Label("               Fill the form below to create a new employee!                     ");
        label.setTextFill(Color.web("#FDDA0D"));
        label.setStyle("-fx-font: 25 Georgia; -fx-font-weight: bold;");
        GridPane.setConstraints(label, 7, 0);

        grid.setPadding(new Insets(40, 40, 40, 40));
        grid.setVgap(35);
        grid.setHgap(15);


        Label fname = new Label("First Name:");
        fname.setStyle("-fx-font: 20 arial;");
        GridPane.setConstraints(fname, 6, 2);
        TextField fname_Input = new TextField();
        fname_Input.setPrefWidth(500);
        GridPane.setConstraints(fname_Input, 7, 2);

        Label lname = new Label("Last Name:");
        lname.setStyle("-fx-font: 20 arial;");
        GridPane.setConstraints(lname, 6, 4);
        TextField lname_Input = new TextField();
        lname_Input.setPrefWidth(500);
        GridPane.setConstraints(lname_Input, 7, 4);

        Label email = new Label("Email:");
        email.setStyle("-fx-font: 20 arial;");
        GridPane.setConstraints(email, 6, 6);
        TextField email_Input = new TextField();
        email_Input.setPrefWidth(500);
        GridPane.setConstraints(email_Input, 7, 6);

        Label phone = new Label("Phone:");
        phone.setStyle("-fx-font: 20 arial;");
        GridPane.setConstraints(phone, 6, 8);
        TextField phone_Input = new TextField();
        phone_Input.setPrefWidth(500);
        GridPane.setConstraints(phone_Input, 7, 8);

        //submit button
        Image create_img = new Image("button-icons/create-icon.png");
        ImageView aview = new ImageView(create_img);
        aview.setFitHeight(20);
        aview.setPreserveRatio(true);
        Button submit = new Button("Create");
        submit.setTranslateX(200);
        submit.setTranslateY(25);
        submit.setPrefSize(40, 40);
        submit.setPrefWidth(100);
        submit.setGraphic(aview);
        submit.setStyle("-fx-background-radius: 10;");

        //cancel button
        Image cancel_img = new Image("button-icons/cancel-icon.png");
        ImageView cview = new ImageView(cancel_img);
        cview.setFitHeight(20);
        cview.setPreserveRatio(true);
        Button cancel = new Button("Cancel");
        cancel.setTranslateX(200);
        cancel.setTranslateY(25);
        cancel.setPrefSize(40, 40);
        cancel.setPrefWidth(100);
        cancel.setGraphic(cview);
        cancel.setStyle("-fx-background-radius: 10;");



        submit.setOnAction(e -> {
            ArrayList<String> existing_emails = dbObject.EmployeesEmails();
            ArrayList<String> existing_tels = dbObject.EmployeesPhones();

            if(fname_Input.getText()!="" && lname_Input.getText()!="" && email_Input.getText()!="" && phone_Input.getText()!="") {
                if(isInt(phone_Input)){
                    boolean email_exists = existing_emails.contains(email_Input.getText());
                    boolean tel_exists = existing_tels.contains(phone_Input.getText());
                    if(!email_exists && !tel_exists) {
                        employee(fname_Input, lname_Input, email_Input, phone_Input);
                        AlertBox.success("New employee created!");
                        //window.setScene(mainscene);
                    }
                    if ((email_exists || tel_exists)) {
                        AlertBox.error("  Employee not added!. Either this email or phone number" + "\n" + "                  is owned by an existing employee  ");
                    }
                }
            }else {
                TextField inputs[] = {fname_Input, lname_Input, email_Input, phone_Input};
                for(TextField x: inputs){
                    if(x.getText()==""){
                        x.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
                        //x.setPromptText("Please fill this blank entry");
                    }
                }
                AlertBox.error("Please fill all the blank entries!");
            }
            });

        cancel.setOnAction(e -> {
            if(fname_Input.getText()!="" || lname_Input.getText()!="" || email_Input.getText()!="" || phone_Input.getText()!="") {
                boolean result = AlertBox.cancel("Are you sure you want to cancel?");
                if (result){
                    window.setScene(mainscene);
                }
            } else {
                window.setScene(mainscene);
            }

        });


        GridPane.setConstraints(submit, 4, 12);
        GridPane.setConstraints(cancel, 7, 12);

        grid.getChildren().addAll(label, fname, fname_Input,lname, lname_Input,email, email_Input, phone, phone_Input, submit, cancel);
        Scene temp1 = new Scene(grid, 750, 470);
        temp1.getStylesheets().add("stylesheets/styler2.css");
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

        ArrayList<String> phones = dbObject.EmployeesPhones();
        String[] Phones = phones.toArray(new String[phones.size()]);

        ArrayList<String> cinfos = dbObject.EmployeesCreationInfo();
        String[] Cinfos = cinfos.toArray(new String[emails.size()]);

        int i = 0;
        while ((i < Names.length) && (i < Emails.length) && (i < Phones.length)){
            employee.add(new Employee(Names[i], Emails[i], Phones[i], Cinfos[i]));
            i++;
        }

        return employee;
    }

    // Edit window
    public void scene2 (){

        window.setTitle("Mann-E");

        VBox layout;
        GridPane grid;
        Scene scene;

        TableColumn<Employee, String> nameColumn = new TableColumn<>("Full_Name");
        nameColumn.setMinWidth(350);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));

        TableColumn<Employee, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setMinWidth(350);
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Employee, String> telColumn = new TableColumn<>("Phone");
        telColumn.setMinWidth(350);
        telColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        TableColumn<Employee, String> creationColumn = new TableColumn<>("Creation_Info");
        creationColumn.setMinWidth(350);
        creationColumn.setCellValueFactory(new PropertyValueFactory<>("creationInfo"));


        table = new TableView<>();
        table.setItems(Table());
        table.getColumns().addAll(nameColumn, emailColumn, telColumn, creationColumn);

        ObservableList<Employee> allEmployees;
        allEmployees = table.getItems();
        System.out.println(allEmployees.size());


        Image edit_img = new Image("button-icons/edit-icon.png");
        ImageView eview = new ImageView(edit_img);
        eview.setFitHeight(25);
        eview.setPreserveRatio(true);
        Button edit = new Button("Edit");
        edit.setTranslateX(200);
        edit.setTranslateY(25);
        edit.setPrefSize(40, 40);
        edit.setPrefWidth(100);
        edit.setGraphic(eview);
        edit.setStyle("-fx-background-radius: 10;");

        edit.setOnAction(e -> scene3());
        GridPane.setConstraints(edit, 1, 70);


        Image del_img = new Image("button-icons/delete-icon.jpg");
        ImageView dview = new ImageView(del_img);
        dview.setFitHeight(25);
        dview.setPreserveRatio(true);
        Button delete = new Button(" delete ");
        delete.setTranslateX(200);
        delete.setTranslateY(25);
        delete.setPrefSize(40, 40);
        delete.setPrefWidth(100);
        delete.setGraphic(dview);
        delete.setStyle("-fx-background-radius: 10;");

        delete.setOnAction(e -> deleteEmployee());
        GridPane.setConstraints(delete, 2, 70);


        Image return_img = new Image("button-icons/return-icon.png");
        ImageView bview = new ImageView(return_img);
        bview.setFitHeight(25);
        bview.setPreserveRatio(true);
        Button back = new Button("Return");
        back.setTranslateX(200);
        back.setTranslateY(25);
        back.setPrefSize(40, 40);
        back.setPrefWidth(100);
        back.setGraphic(bview);
        back.setStyle("-fx-background-radius: 10;");

        back.setOnAction(e -> window.setScene(mainscene));
        GridPane.setConstraints(back, 30, 70);


        layout = new VBox();
        grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(3);
        grid.setHgap(20);
        grid.getChildren().addAll(edit, delete, back);

        layout.getChildren().addAll(table, grid);

        scene = new Scene(layout, 750, 470);
        scene.getStylesheets().add("stylesheets/styler3.css");
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
            box.setTitle("Edit");
            box.getIcons().add(new Image("button-icons/edit-icon.png"));
            box.setMinWidth(500);
            box.setMinHeight(385);
            box.setMaxWidth(500);
            box.setMaxHeight(385);
            box.setWidth(500);
            box.setHeight(385);

            grid.setPadding(new Insets(40, 40, 40, 40));
            grid.setVgap(30);
            grid.setHgap(20);

            Label fname = new Label("First_Name:");
            GridPane.setConstraints(fname, 3, 0);
            TextField fname_Input = new TextField();
            fname_Input.setText(updateEmployeeFname());
            GridPane.setConstraints(fname_Input, 4, 0);

            Label lname = new Label("Last_Name:");
            GridPane.setConstraints(lname, 3, 1);
            TextField lname_Input = new TextField();
            lname_Input.setText(updateEmployeeLname());
            GridPane.setConstraints(lname_Input, 4, 1);

            Label email = new Label("Email:");
            GridPane.setConstraints(email, 3, 2);
            TextField email_Input = new TextField();
            email_Input.setText(updateEmployeeEmail());
            GridPane.setConstraints(email_Input, 4, 2);

            Label phone = new Label("Phone:");
            GridPane.setConstraints(phone, 3, 3);
            TextField phone_Input = new TextField();
            phone_Input.setText(updateEmployeeTel().toString());
            GridPane.setConstraints(phone_Input, 4, 3);


            Image update_img = new Image("button-icons/update-icon.png");
            ImageView uview = new ImageView(update_img);
            uview.setFitHeight(20);
            uview.setPreserveRatio(true);
            Button update = new Button("update");
            update.setTranslateX(40);
            update.setTranslateY(15);
            update.setPrefSize(30, 30);
            update.setPrefWidth(80);
            update.setGraphic(uview);
            update.setStyle("-fx-background-radius: 10;");

            update.setOnAction(e -> {
                if(fname_Input.getText()!="" && lname_Input.getText()!="" && email_Input.getText()!="" && phone_Input.getText()!="") {
                    if(isInt(phone_Input)){
                        boolean result = AlertBox.alert("Update", "Are you sure you want to update this employee?");
                        if (result) {
                            dbObject.updateEmployee((fname_Input.getText() + " " + lname_Input.getText()), email_Input.getText(), phone_Input.getText(), id);
                            AlertBox.success("Employee updated succesfully!");
                            box.close();
                            scene2();
                        }
                    }

                }else {
                    TextField inputs[] = {fname_Input, lname_Input, email_Input, phone_Input};
                    for(TextField x: inputs){
                        if(x.getText()==""){
                            x.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
                            //x.setPromptText("Please fill this blank entry");
                        }
                    }
                    AlertBox.error("Please fill all the blank entries!");
                }


            });
            GridPane.setConstraints(update, 3, 5);

            Image cancel_img = new Image("button-icons/cancel-icon.png");
            ImageView cview = new ImageView(cancel_img);
            cview.setFitHeight(20);
            cview.setPreserveRatio(true);
            Button cancel = new Button("Cancel");
            cancel.setTranslateX(40);
            cancel.setTranslateY(15);
            cancel.setPrefSize(30, 30);
            cancel.setPrefWidth(80);
            cancel.setGraphic(cview);
            cancel.setStyle("-fx-background-radius: 10;");

            cancel.setOnAction(e -> {
                Boolean result = AlertBox.cancel("Are you sure you want to discard your changes?");
                if(result){
                    box.close();
                    scene2();
                }
            });
            GridPane.setConstraints(cancel, 4, 5);

            grid.getChildren().addAll(fname, fname_Input,lname, lname_Input,email, email_Input, phone, phone_Input, update, cancel);
            layout.getChildren().addAll(grid);

            Scene scene = new Scene(layout);
            scene.getStylesheets().add("stylesheets/styler4.css");
            box.setScene(scene);
            box.showAndWait();




        }

    }

//    public void info(){
//        Scene scene;
//        VBox vbox = new VBox();
//
//        Label text = new Label("    Write                 ");
//
//
//        Image return_img = new Image("button-icons/return-icon.png");
//        ImageView bview = new ImageView(return_img);
//        bview.setFitHeight(25);
//        bview.setPreserveRatio(true);
//        Button back = new Button("Return");
//        back.setTranslateX(200);
//        back.setTranslateY(25);
//        back.setPrefSize(40, 40);
//        back.setPrefWidth(100);
//        back.setGraphic(bview);
//        back.setStyle("-fx-background-radius: 10;");
//
//        back.setOnAction(e -> window.setScene(mainscene));
//
//        vbox.getChildren().addAll(text, back);
//
//        scene = new Scene(vbox, 750, 470);
//        scene.getStylesheets().add("stylesheets/styler5.css");
//        window.setScene(scene);
//
//
//    }



    //others

    //Retrieving data for the database
    public void employee (TextField fn_input, TextField ln_input, TextField em_input, TextField p_input){
        String firstName = fn_input.getText();
        String lastName = ln_input.getText();
        String fullName = firstName + " " + lastName;
        String email = em_input.getText();
        String phone = p_input.getText();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        dbObject.createEmployee(fullName, email, phone, dtf.format(now));

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
        box.getIcons().add(new Image("button-icons/delete-icon.jpg"));
        box.setTitle("Delete");
        box.setMinWidth(500);
        box.setMinHeight(385);
        box.setMaxWidth(500);
        box.setMaxHeight(385);
        box.setWidth(500);
        box.setHeight(385);

        grid.setPadding(new Insets(40, 40, 40, 40));
        grid.setVgap(30);
        grid.setHgap(20);

        Label fname = new Label("First_Name:");
        GridPane.setConstraints(fname, 3, 0);
        TextField fname_Input = new TextField();
        GridPane.setConstraints(fname_Input, 4, 0);

        Label lname = new Label("Last_Name:");
        GridPane.setConstraints(lname, 3, 1);
        TextField lname_Input = new TextField();
        GridPane.setConstraints(lname_Input, 4, 1);

        Label email = new Label("Email:");
        GridPane.setConstraints(email, 3, 2);
        TextField email_Input = new TextField();
        GridPane.setConstraints(email_Input, 4, 2);

        Label phone = new Label("Phone:");
        GridPane.setConstraints(phone, 3, 3);
        TextField phone_Input = new TextField();
        GridPane.setConstraints(phone_Input, 4, 3);


        Image del_img = new Image("button-icons/delete-icon.jpg");
        ImageView dview = new ImageView(del_img);
        dview.setFitHeight(20);
        dview.setPreserveRatio(true);
        Button del = new Button("Delete");
        del.setTranslateX(40);
        del.setTranslateY(15);
        del.setPrefSize(30, 30);
        del.setPrefWidth(80);
        del.setGraphic(dview);
        del.setStyle("-fx-background-radius: 10;");

        del.setOnAction(e -> {
            if(fname_Input.getText()!="" && lname_Input.getText()!="" && email_Input.getText()!="" && phone_Input.getText()!="") {
                if(isInt(phone_Input)) {
                    ArrayList<String> existing_emails = dbObject.EmployeesEmails();
                    ArrayList<String> existing_tels = dbObject.EmployeesPhones();
                    boolean result = AlertBox.alert("Delete", "Are you sure you want to delete this employee");
                    boolean email_exists = existing_emails.contains(email_Input.getText());
                    boolean tel_exists = existing_tels.contains(phone_Input.getText());
                    if(result && email_exists && tel_exists) {
                        dbObject.deleteEmployee(email_Input.getText());
                        AlertBox.success("Employee deleted succesfully!");
                        box.close();
                    }
                    if (result && (!email_exists || !tel_exists)) {
                        AlertBox.error("  Employee not removed!. Employee does not exists  ");
                    }
                }
            }else {
                TextField inputs[] = {fname_Input, lname_Input, email_Input, phone_Input};
                for(TextField x: inputs){
                    if(x.getText()==""){
                        x.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
                        //x.setPromptText("Please fill this blank entry");
                    }
                }
                AlertBox.error("Please fill all the blank entries!");
            }

        });
        GridPane.setConstraints(del, 3, 5);


        Image cancel_img = new Image("button-icons/cancel-icon.png");
        ImageView cview = new ImageView(cancel_img);
        cview.setFitHeight(20);
        cview.setPreserveRatio(true);
        Button cancel = new Button("Cancel");
        cancel.setTranslateX(40);
        cancel.setTranslateY(15);
        cancel.setPrefSize(30, 30);
        cancel.setPrefWidth(80);
        cancel.setGraphic(cview);
        cancel.setStyle("-fx-background-radius: 10;");

        cancel.setOnAction(e -> {
            if(fname_Input.getText()!="" || lname_Input.getText()!="" || email_Input.getText()!="" || phone_Input.getText()!="") {
                boolean result = AlertBox.cancel("Are you sure you want to cancel?");
                if (result){
                    box.close();
                }
            } else {
                box.close();
            }
        });
        GridPane.setConstraints(cancel, 4, 5);

        grid.getChildren().addAll(fname, fname_Input,lname, lname_Input,email, email_Input, phone, phone_Input, del, cancel);
        layout.getChildren().addAll(grid);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("stylesheets/styler4.css");
        box.setScene(scene);
        box.showAndWait();



    }



    //update methods for retrieving the employee information
    public String updateEmployeeFname(){

        ObservableList<Employee> selected;
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

        ObservableList<Employee> selected;
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

        ObservableList<Employee> selected;
        selected = table.getSelectionModel().getSelectedItems();

        String email = new String();

        if(selected.size()>0) {
            String required = selected.get(0).getEmail();
            email = required;
        }

        return email;
    }
    public String updateEmployeeTel(){

        ObservableList<Employee> selected;
        selected = table.getSelectionModel().getSelectedItems();

        String tel = new String();

        if(selected.size()>0) {
            String required = selected.get(0).getPhone();
            tel = required;
        }

        return tel;
    }
    //update methods end here


    //Validating text fields
    private boolean isInt (TextField input) {
        try {
            int num = (int) Long.parseLong(input.getText());
            return true;
        } catch (Exception e) {
            System.out.println(e);
            AlertBox.error("Input an integer in the Phone entry");
            return false;
        }
    }








}

