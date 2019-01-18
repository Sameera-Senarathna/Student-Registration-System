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
import util.CourseComboListitems;
import util.BatchTM;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class ManageManageBatchController {

    Connection connection = DatabaseConnection.connection();

    @FXML
    private ComboBox<CourseComboListitems> comboBoxCourse;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtCapacity;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnDelete;

    @FXML
    private DatePicker datePickerDate;

    @FXML
    private TableView<BatchTM> tblBatch;

    private ObservableList<BatchTM> batchTMObservableList = FXCollections.observableArrayList();

    @FXML
    void ClickDeleteBtn(ActionEvent event) throws SQLException {

        if(txtID.isEditable()){
            return;
        }

        String id = txtID.getText();

        PreparedStatement deleteBatchQuery = connection.prepareStatement("DELETE FROM batch WHERE b_id = ?");

        deleteBatchQuery.setString(1,id);

        int affectedRows = deleteBatchQuery.executeUpdate();

        if(affectedRows>0){

            new Alert(Alert.AlertType.ERROR,"Batch Successfully Deleted",ButtonType.OK).showAndWait();

            for (BatchTM batch : batchTMObservableList) {
                if(id.equals(batch.getB_id())){
                    batchTMObservableList.remove(batch);
                    break;
                }
            }

            clear();
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
        String description = txtDescription.getText();
        String capacity = txtCapacity.getText();
        LocalDate date = datePickerDate.getValue();
        CourseComboListitems course = comboBoxCourse.getSelectionModel().getSelectedItem();

        if(id.trim().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please Enter Batch ID",ButtonType.OK).showAndWait();
            txtID.requestFocus();
            return;
        }else if(description.trim().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please Enter Batch Description",ButtonType.OK).showAndWait();
            txtDescription.requestFocus();
            return;
        }else if(capacity.trim().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please Enter Class Capacity",ButtonType.OK).showAndWait();
            txtCapacity.requestFocus();
            return;
        }else if(date==null){
            new Alert(Alert.AlertType.ERROR,"Please Enter Batch Starting Date",ButtonType.OK).showAndWait();
            datePickerDate.requestFocus();
            return;
        }else if(course==null){
            new Alert(Alert.AlertType.ERROR,"Please Select Course to Create a Batch",ButtonType.OK).showAndWait();
            comboBoxCourse.requestFocus();
            return;
        }

        if(txtID.isEditable()){
            PreparedStatement createABatchQuery = connection.prepareStatement("INSERT INTO batch VALUES (?,?,?,?,?)");

            createABatchQuery.setString(1,id);
            createABatchQuery.setString(2,course.getId());
            createABatchQuery.setString(3,description);
            createABatchQuery.setDate(4,Date.valueOf(date));
            createABatchQuery.setInt(5,Integer.parseInt(capacity));

            int affectedRows = createABatchQuery.executeUpdate();

            if(affectedRows>0){
                new Alert(Alert.AlertType.ERROR,"Batch Successfully created",ButtonType.OK).showAndWait();

                BatchTM newBatch = new BatchTM(id, course.getId(), description, date, Integer.parseInt(capacity));

                batchTMObservableList.add(newBatch);

                clear();
            }

        }else {
            PreparedStatement updateBatchQurty = connection.prepareStatement("UPDATE batch SET c_id = ? , description = ? , start_date = ? , capacity = ? WHERE b_id = ?");

            updateBatchQurty.setString(1,course.getId());
            updateBatchQurty.setString(2,description);
            updateBatchQurty.setDate(3,Date.valueOf(date));
            updateBatchQurty.setInt(4,Integer.parseInt(capacity));
            updateBatchQurty.setString(5,id);

            int affectedRows = updateBatchQurty.executeUpdate();

            if(affectedRows>0){

                new Alert(Alert.AlertType.ERROR,"Batch Successfully Updated",ButtonType.OK).showAndWait();

                for (BatchTM batch : batchTMObservableList) {
                    if(id.equals(batch.getB_id())){
                        batch.setC_id(course.getId());
                        batch.setDescription(description);
                        batch.setDate(date);
                        batch.setCapacity(Integer.parseInt(capacity));
                        break;
                    }
                }

                clear();
            }

        }


    }

    private void clear() {

        txtCapacity.clear();
        txtDescription.clear();
        txtID.clear();
        datePickerDate.getEditor().clear();
        comboBoxCourse.getSelectionModel().clearSelection();
        tblBatch.refresh();
        txtID.setEditable(true);
        tblBatch.getSelectionModel().clearSelection();

    }

    public void initialize() throws SQLException {

        datePickerDate.setEditable(false);

        //--------------------------Combo Box Data -----------------------------------------//

        ObservableList<CourseComboListitems> comboItems = FXCollections.observableArrayList();

        Statement comboBoxQuery = connection.createStatement();

        ResultSet allCourse = comboBoxQuery.executeQuery("SELECT c_id , name FROM course");

        while (allCourse.next()){
            CourseComboListitems course = new CourseComboListitems(allCourse.getString(1), allCourse.getString(2));
            comboItems.add(course);
        }

        comboBoxCourse.setItems(comboItems);

        //--------------------- Fill Batch Table------------------------------------------//


        tblBatch.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("b_id"));
        tblBatch.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("c_id"));
        tblBatch.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblBatch.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("date"));
        tblBatch.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("capacity"));

        Statement allBatchQuery = connection.createStatement();
        ResultSet allBatchresultSet = allBatchQuery.executeQuery("SELECT * FROM batch INNER JOIN course ON batch.c_id = course.c_id");

        while (allBatchresultSet.next()){

            String b_id = allBatchresultSet.getString("b_id");
            String c_id = allBatchresultSet.getString("c_id");
            String description = allBatchresultSet.getString("description");
            int capacity = allBatchresultSet.getInt("capacity");
            Date start_date = allBatchresultSet.getDate("start_date");

            BatchTM oneBatch = new BatchTM(b_id, c_id, description, start_date.toLocalDate(), capacity);

            batchTMObservableList.add(oneBatch);

        }

        tblBatch.setItems(batchTMObservableList);

        //------------------------- table Listener ---------------------------------------//

        tblBatch.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BatchTM>() {
            @Override
            public void changed(ObservableValue<? extends BatchTM> observable, BatchTM oldValue, BatchTM newValue) {
                if(newValue==null){
                    return;
                }

                txtID.setEditable(false);

                txtID.setText(newValue.getB_id());
                txtCapacity.setText(newValue.getCapacity()+"");
                txtDescription.setText(newValue.getDescription());
                datePickerDate.setValue(newValue.getDate());

                for (CourseComboListitems item : comboItems) {
                    if(item.getId().equals(newValue.getC_id())){
                        comboBoxCourse.getSelectionModel().select(comboItems.indexOf(item));
                        break;
                    }
                }



            }
        });


    }

}
