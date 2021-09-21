import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.collections.*;



public class AlertBox {

    static boolean answer;

    public static boolean alert(String title, String message) {
        Stage box = new Stage();

        box.initModality(Modality.APPLICATION_MODAL);
        box.setTitle(title);
        box.setMinWidth(380);
        box.setMinHeight(140);
        box.setMaxWidth(380);
        box.setMaxHeight(140);

        Label label = new Label();
        label.setText(message);
        Button yes = new Button("Yes");
        yes.setOnAction(e -> {
            answer = true;
            box.close();
        });
        Button no = new Button("No");
        no.setOnAction(e -> {
            answer = false;
            box.close();
        });

        HBox layout1 = new HBox();
        layout1.getChildren().add(label);
        layout1.setAlignment(Pos.TOP_CENTER);
        HBox layout2 = new HBox(15);
        layout2.getChildren().addAll(yes, no);
        layout2.setAlignment(Pos.CENTER);

        StackPane con = new StackPane();
        con.getChildren().addAll(layout1, layout2);

        Scene scene = new Scene(con);
        box.setScene(scene);
        box.showAndWait();

        return answer;
    }

    public static boolean home(String title, String message) {
        Stage box = new Stage();

        box.initModality(Modality.APPLICATION_MODAL);
        box.setTitle(title);
        box.setMinWidth(350);

        Label label = new Label();
        label.setText(message);
        Button yes = new Button("Yes");
        yes.setOnAction(e -> {
            answer = true;
            box.close();
        });
        Button no = new Button("No");
        no.setOnAction(e -> {
            answer = false;
            box.close();
        });

        VBox layout = new VBox(15);
        layout.getChildren().addAll(label, yes, no);
        layout.setAlignment(Pos.BOTTOM_CENTER);

        Scene scene = new Scene(layout);
        box.setScene(scene);
        box.showAndWait();

        return answer;
    }

    public static boolean closeProgram(String title, String message) {
        Stage box = new Stage();

        box.initModality(Modality.APPLICATION_MODAL);
        box.setTitle(title);
        box.setMinWidth(350);
        box.setMinHeight(110);
        box.setMaxWidth(350);
        box.setMaxHeight(110);

        Label label = new Label();
        label.setText(message);
        Button yes = new Button("Yes");
        yes.setOnAction(e -> {
            answer = true;
            box.close();
        });
        Button no = new Button("No");
        no.setOnAction(e -> {
            answer = false;
            box.close();
        });

        HBox layout1 = new HBox();
        layout1.getChildren().add(label);
        layout1.setAlignment(Pos.TOP_CENTER);
        HBox layout2 = new HBox(15);
        layout2.getChildren().addAll(yes, no);
        layout2.setAlignment(Pos.CENTER);

        StackPane con = new StackPane();
        con.getChildren().addAll(layout1, layout2);

        Scene scene = new Scene(con);
        box.setScene(scene);
        box.showAndWait();

        return answer;
    }

    public static boolean success(String message) {
        Stage box = new Stage();

        box.initModality(Modality.APPLICATION_MODAL);
        box.setTitle("Success");
        box.setMinWidth(380);
        box.setMinHeight(120);

        Button ok = new Button("OK");
        ok.setOnAction(e -> box.close());

        Label label = new Label();
        label.setText(message);

        VBox layout = new VBox(15);
        layout.getChildren().addAll(label, ok);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        box.setScene(scene);
        box.showAndWait();

        return answer;
    }

    public static boolean error(String message) {
        Stage box = new Stage();

        box.initModality(Modality.APPLICATION_MODAL);
        box.setTitle("Error");
        box.setMinWidth(410);
        box.setMinHeight(140);

        Button ok = new Button("OK");
        ok.setOnAction(e -> box.close());

        Label label = new Label();
        label.setText(message);

        VBox layout = new VBox(15);
        layout.getChildren().addAll(label, ok);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        box.setScene(scene);
        box.showAndWait();

        return answer;

    }

    public static boolean cancel(String message) {
        Stage box = new Stage();

        box.initModality(Modality.APPLICATION_MODAL);
        box.setTitle("Cancel");
        box.setMinWidth(380);
        box.setMinHeight(140);
        box.setMaxWidth(380);
        box.setMaxHeight(140);

        Label label = new Label();
        label.setText(message);
        Button yes = new Button("Yes");
        yes.setOnAction(e -> {
            answer = true;
            box.close();
        });
        Button no = new Button("No");
        no.setOnAction(e -> {
            answer = false;
            box.close();
        });

        HBox layout1 = new HBox();
        layout1.getChildren().add(label);
        layout1.setAlignment(Pos.TOP_CENTER);
        HBox layout2 = new HBox(15);
        layout2.getChildren().addAll(yes, no);
        layout2.setAlignment(Pos.CENTER);

        StackPane con = new StackPane();
        con.getChildren().addAll(layout1, layout2);

        Scene scene = new Scene(con);
        box.setScene(scene);
        box.showAndWait();

        return answer;
    }


}
