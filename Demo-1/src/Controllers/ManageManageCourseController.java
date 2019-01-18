package Controllers;

import Connectivity.DatabaseConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import util.CourseTM;

import java.io.IOException;
import java.sql.*;

public class ManageManageCourseController {

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextArea txtDescription;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnDelete;

    @FXML
    private TableView<CourseTM> tblCourse;

    ObservableList<CourseTM> tabledata = FXCollections.observableArrayList();

    @FXML
    void ClickDeleteBtn(ActionEvent event) throws SQLException {
        if(txtID.isEditable()){
            return;
        }

        Connection connection = DatabaseConnection.connection();
        PreparedStatement deleteCourseQuery = connection.prepareStatement("DELETE FROM course WHERE c_id=?");
        deleteCourseQuery.setString(1,txtID.getText());
        int affectedRows = deleteCourseQuery.executeUpdate();

        if(affectedRows>0){

            new Alert(Alert.AlertType.INFORMATION,"Course Successfully Deleted",ButtonType.OK).showAndWait();

            for (CourseTM  course : tabledata) {
                if(course.getId().equals(txtID.getText())){
                    tabledata.remove(course);
                    break;
                }
            }


            clear();
            txtID.setEditable(true);
            txtID.requestFocus();

        }else {
            new Alert(Alert.AlertType.ERROR,"Operation Failed",ButtonType.OK).showAndWait();
        }
    }

    @FXML
    void ClickHomeBtn(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(this.getClass().getResource("/Views/HomePage.fxml"));
        Scene mainScene = new Scene(root);
        Stage primaryStage = (Stage) txtDescription.getScene().getWindow();
        primaryStage.setScene(mainScene);
        primaryStage.show();

    }

    @FXML
    void ClickSaveBtn(ActionEvent event) throws SQLException {

        String id = txtID.getText();
        String name = txtName.getText();
        String description = txtDescription.getText();
        String duration = txtDuration.getText();

        if(id.trim().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please Enter Course ID",ButtonType.OK).showAndWait();
            txtID.requestFocus();
            return;
        }else if(name.trim().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please Enter Course Name",ButtonType.OK).showAndWait();
            txtName.requestFocus();
            return;
        }else if(description.trim().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please Enter Course Description",ButtonType.OK).showAndWait();
            txtDescription.requestFocus();
            return;
        }else if(duration.trim().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please Enter Course Duration",ButtonType.OK).showAndWait();
            txtDuration.requestFocus();
            return;
        }else if(!isInt(duration)){
            new Alert(Alert.AlertType.ERROR,"Duration Should be Numeric ",ButtonType.OK).showAndWait();
            txtDuration.requestFocus();
            return;
        }

        Connection connection = DatabaseConnection.connection();

        if(txtID.isEditable()){

            PreparedStatement checkCourseIDdublicationQuery = connection.prepareStatement("SELECT * FROM course WHERE c_id = ?");
            checkCourseIDdublicationQuery.setString(1,id);
            ResultSet duplicated = checkCourseIDdublicationQuery.executeQuery();

            duplicated.last();


            if(duplicated.getRow()>0){
                new Alert(Alert.AlertType.INFORMATION,"Course ID already Exist in the system",ButtonType.OK).showAndWait();
                txtID.requestFocus();
                return;
            }

            PreparedStatement addCourseQuery = connection.prepareStatement("INSERT INTO course VALUES (?,?,?,?)");

            addCourseQuery.setString(1,id);
            addCourseQuery.setString(2,name);
            addCourseQuery.setString(3,description);
            addCourseQuery.setInt(4,Integer.parseInt(duration));

            int affectedrows = addCourseQuery.executeUpdate();

            if(affectedrows>0){
                new Alert(Alert.AlertType.INFORMATION,"Course Successfully added to the system",ButtonType.OK).showAndWait();
                tabledata.add(new CourseTM(id,name,description,Integer.parseInt(duration)));
                txtID.requestFocus();
                clear();
            }else {
                new Alert(Alert.AlertType.ERROR,"Operation Failed",ButtonType.OK).showAndWait();
            }

        }else {

            PreparedStatement updateCourseQuery = connection.prepareStatement("UPDATE course SET name=? , description=? , duration=? WHERE c_id = ?");
            updateCourseQuery.setString(1,name);
            updateCourseQuery.setString(2,description);
            updateCourseQuery.setInt(3,Integer.parseInt(duration));
            updateCourseQuery.setString(4,txtID.getText());

            int affectedRows = updateCourseQuery.executeUpdate();

            if(affectedRows>0){
                new Alert(Alert.AlertType.INFORMATION,"Course Successfully Updated",ButtonType.OK).showAndWait();

                for (CourseTM course:tabledata) {
                    if(course.getId().equals(txtID.getText())){
                        course.setName(name);
                        course.setDescription(description);
                        course.setDuration(Integer.parseInt(duration));
                    }
                }
                tblCourse.refresh();

                txtID.requestFocus();
                clear();
                txtID.setEditable(true);
            }else {
                new Alert(Alert.AlertType.ERROR,"Operation Failed",ButtonType.OK).showAndWait();
            }

        }



    }

    private void clear() {
        txtID.clear();
        txtName.clear();
        txtDescription.clear();
        txtDuration.clear();
        tblCourse.getSelectionModel().clearSelection();
    }

    private boolean isInt(String value){

        try {
            Integer.parseInt(value);
            return true;
        }catch (Exception ex){
            return false;
        }

    }

    public void initialize() throws SQLException {

        tblCourse.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblCourse.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCourse.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblCourse.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("duration"));


        Connection connection = DatabaseConnection.connection();

        Statement getAllquery = connection.createStatement();
        ResultSet allCourse = getAllquery.executeQuery("SELECT * FROM course");

        while (allCourse.next()){
            CourseTM courseTM = new CourseTM(allCourse.getString(1), allCourse.getString(2), allCourse.getString(3), allCourse.getInt(4));
            tabledata.add(courseTM);
        }

        tblCourse.setItems(tabledata);

        tblCourse.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CourseTM>() {
            @Override
            public void changed(ObservableValue<? extends CourseTM> observable, CourseTM oldValue, CourseTM newValue) {
                if(newValue==null){
                    return;
                }

                txtID.setEditable(false);
                txtID.setText(newValue.getId());
                txtName.setText(newValue.getName());
                txtDescription.setText(newValue.getDescription());
                txtDuration.setText(newValue.getDuration()+"");

            }
        });

    }

}
