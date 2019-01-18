package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ManageRegisterStudentController {

    @FXML
    private Button btnHome;

    @FXML
    void ClickExistingStudentBtn(ActionEvent event) {

    }

    @FXML
    void ClickHomeBtn(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(this.getClass().getResource("/Views/HomePage.fxml"));
        Scene mainScene = new Scene(root);
        Stage primaryStage = (Stage) btnHome.getScene().getWindow();
        primaryStage.setScene(mainScene);
        primaryStage.show();

    }

    @FXML
    void ClickNewStudentBtn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/Views/ResisterNewStudent.fxml"));
        Scene mainScene = new Scene(root);
        Stage primaryStage = (Stage) btnHome.getScene().getWindow();
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

}
