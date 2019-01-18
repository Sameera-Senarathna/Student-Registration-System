package Controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPageController {

    @FXML
    private Button btnRegister;

    @FXML
    void ClickBatchBtn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/Views/ManageBatch.fxml"));
        Scene mainScene = new Scene(root);
        Stage primaryStage = (Stage)btnRegister.getScene().getWindow();
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    @FXML
    void ClickCourseBtn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/Views/ManageCourse.fxml"));
        Scene mainScene = new Scene(root);
        Stage primaryStage = (Stage)btnRegister.getScene().getWindow();
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    @FXML
    void ClickManageStudentBtn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/Views/ManageBatch.fxml"));
        Scene mainScene = new Scene(root);
        Stage primaryStage = (Stage)btnRegister.getScene().getWindow();
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    @FXML
    void ClickRegisterBtn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/Views/RegisterStudent.fxml"));
        Scene mainScene = new Scene(root);
        Stage primaryStage = (Stage)btnRegister.getScene().getWindow();
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

}