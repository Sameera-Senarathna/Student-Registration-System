package Controllers;

import Connectivity.DatabaseConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import util.CourseComboListitems;

import java.io.IOException;
import java.sql.*;

public class RegisterNewStudentController {

    @FXML
    private Button btnHome;

    @FXML
    private TextField txtFullName;

    @FXML
    private TextField txtNameWithInitials;

    @FXML
    private TextField txtStudentAddress;

    @FXML
    private TextField txtStudentPhone;

    @FXML
    private TextField txtStudentEmail;

    @FXML
    private DatePicker datePickerBirthday;

    @FXML
    private TextField txtSchool;

    @FXML
    private RadioButton radioMale;

    @FXML
    private RadioButton radioFemale;

    @FXML
    private TextField txtUniversity;

    @FXML
    private TextField txtFacuilty;

    @FXML
    private TextField txtNIC;

    @FXML
    private CheckBox checkBoxMaster;

    @FXML
    private CheckBox checkBoxDegree;

    @FXML
    private CheckBox checkBoxDiploma;

    @FXML
    private CheckBox checkBoxAL;

    @FXML
    private CheckBox checkBoxOL;

    @FXML
    private CheckBox checkBoxOther;

    @FXML
    private TextField txtStudentID;

    @FXML
    private TextField txtGuardianName;

    @FXML
    private TextField txtGuardianPhone;

    @FXML
    private TextField txtGuardianAddress;

    @FXML
    private TextField txtGuardianEmail;

    @FXML
    private TextField txtDesignation;

    @FXML
    private TextField txtWorkPlace;

    @FXML
    private ComboBox<CourseComboListitems> comboBoxCourse;

    @FXML
    private ComboBox<String> comboBoxBatch;

    //----------------- my Variables-----------------//

    Connection connection = DatabaseConnection.connection();
    private ToggleGroup sexGroup = new ToggleGroup();

    //-----------------------------------------------//

    @FXML
    void ClickHomeBtn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/Views/RegisterStudent.fxml"));
        Scene mainScene = new Scene(root);
        Stage primaryStage = (Stage) btnHome.getScene().getWindow();
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    @FXML
    void clickOnRegisterBtn(ActionEvent event) throws SQLException {

        if(txtFullName.getText().trim().isEmpty()){
            new Alert(Alert.AlertType.CONFIRMATION,"Full Name Can not be empty",ButtonType.OK).showAndWait();
            txtFullName.requestFocus();
            return;
        }else if(txtNameWithInitials.getText().trim().isEmpty()){
            new Alert(Alert.AlertType.CONFIRMATION,"Name with Initials Can not be empty",ButtonType.OK).showAndWait();
            txtNameWithInitials.requestFocus();
            return;
        }else if (!radioMale.isSelected() && !radioFemale.isSelected()){
            new Alert(Alert.AlertType.CONFIRMATION,"Please select Gender of the Student",ButtonType.OK).showAndWait();
            return;
        } else if (txtStudentAddress.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.CONFIRMATION,"Address Can not be Empty",ButtonType.OK).showAndWait();
            txtStudentAddress.requestFocus();
            return;
        } else if (!txtStudentPhone.getText().matches("\\d{10}")){
            new Alert(Alert.AlertType.CONFIRMATION,"InValid Phone Number",ButtonType.OK).showAndWait();
            txtStudentPhone.requestFocus();
            return;
        }else if (!txtStudentEmail.getText().matches("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")){
            new Alert(Alert.AlertType.CONFIRMATION,"Invalid Email Address",ButtonType.OK).showAndWait();
            txtStudentEmail.requestFocus();
            return;
        }else if(datePickerBirthday.getValue()==null){
            new Alert(Alert.AlertType.CONFIRMATION,"Please Select Birthday",ButtonType.OK).showAndWait();
            datePickerBirthday.requestFocus();
            return;
        }else if (!txtNIC.getText().matches("\\d{9}[vV]")){
            new Alert(Alert.AlertType.CONFIRMATION,"Invalid NIC Number",ButtonType.OK).showAndWait();
            txtNIC.requestFocus();
            return;
        }else if (txtSchool.getText().trim().isEmpty()){
            new Alert(Alert.AlertType.CONFIRMATION,"Please Enter School",ButtonType.OK).showAndWait();
            txtSchool.requestFocus();
            return;
        }else if (!checkBoxMaster.isSelected() && !checkBoxDegree.isSelected() && !checkBoxDiploma.isSelected() && !checkBoxAL.isSelected() && !checkBoxOL.isSelected() && !checkBoxOther.isSelected()){
            new Alert(Alert.AlertType.CONFIRMATION,"Please Select Eduction Qualifications",ButtonType.OK).showAndWait();
            return;
        }if(txtGuardianName.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.CONFIRMATION, "Guardian Name Can not be empty", ButtonType.OK).showAndWait();
            txtGuardianName.requestFocus();
            return;
        }else if (!txtGuardianPhone.getText().matches("\\d{10}")){
            new Alert(Alert.AlertType.CONFIRMATION,"InValid Phone Number",ButtonType.OK).showAndWait();
            txtGuardianPhone.requestFocus();
            return;
        }else if (!txtGuardianEmail.getText().matches("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")){
            new Alert(Alert.AlertType.CONFIRMATION,"Invalid Email Address",ButtonType.OK).showAndWait();
            txtGuardianEmail.requestFocus();
            return;
        }else if (txtGuardianAddress.getText().trim().isEmpty()){
            new Alert(Alert.AlertType.CONFIRMATION,"Please enter Guardian Address",ButtonType.OK).showAndWait();
            txtGuardianAddress.requestFocus();
            return;
        }else if (comboBoxCourse.getSelectionModel().getSelectedItem()==null){
            new Alert(Alert.AlertType.CONFIRMATION,"Please Select Course",ButtonType.OK).showAndWait();
            comboBoxCourse.requestFocus();
            return;
        }else if (comboBoxBatch.getSelectionModel().getSelectedItem()==null){
            new Alert(Alert.AlertType.CONFIRMATION,"Plaese Select Batch",ButtonType.OK).showAndWait();
            comboBoxBatch.requestFocus();
            return;
        }

        //--------------- Insert student Table -----------------------//

        PreparedStatement addStudentQuery = connection.prepareStatement("INSERT INTO student VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
        addStudentQuery.setString(1,txtStudentID.getText());
        addStudentQuery.setString(2,txtFullName.getText());
        addStudentQuery.setString(3,txtNameWithInitials.getText());

        String sex = null ;

        if(radioMale.isSelected()){
            sex = "Male";
        }else if(radioFemale.isSelected()){
            sex = "Female";
        }

        addStudentQuery.setString(4, sex);
        addStudentQuery.setString(5,txtStudentAddress.getText());
        addStudentQuery.setString(6,txtStudentPhone.getText());
        addStudentQuery.setString(7,txtStudentEmail.getText());
        addStudentQuery.setDate(8,Date.valueOf(datePickerBirthday.getValue()));
        addStudentQuery.setString(9,txtSchool.getText());
        addStudentQuery.setString(10,txtUniversity.getText());
        addStudentQuery.setString(11,txtFacuilty.getText());
        addStudentQuery.setString(12,txtNIC.getText());

        int affectedRows = addStudentQuery.executeUpdate();



        //------------------- Insert Batch Table ------------------------//

        PreparedStatement addRegistration = connection.prepareStatement("INSERT INTO register VALUES (?,?)");

        addRegistration.setString(1,comboBoxCourse.getValue().getId());
        addRegistration.setString(2,comboBoxBatch.getValue());

        affectedRows = affectedRows + addRegistration.executeUpdate();

        if(affectedRows>0){
            System.out.println("Student Table Updated Successfully");
        }

    }

    public void initialize() throws SQLException {

        txtStudentID.setEditable(false);

        //------------------ generate Student ID --------------------------//

        Statement studentIdquery = connection.createStatement();
        ResultSet select_s_id_from_student = studentIdquery.executeQuery("SELECT s_id FROM student");
        select_s_id_from_student.last();
        txtStudentID.setText("S00"+ (select_s_id_from_student.getRow()+1));

        //---------------------- Course Combo Box ---------------------------//

        Statement courseComboBoxQuery = connection.createStatement();
        ResultSet allCourse = courseComboBoxQuery.executeQuery("SELECT c_id , name FROM course");

        ObservableList<CourseComboListitems> courseObservableList = comboBoxCourse.getItems();

        while (allCourse.next()){
            courseObservableList.add(new CourseComboListitems(allCourse.getString(1),allCourse.getString(2)));
        }

        //-------------------- Batch Combo Box -----------------------------//

        PreparedStatement batchComboBoxQuery = connection.prepareStatement("SELECT b_id FROM batch WHERE c_id = ?");

        comboBoxCourse.valueProperty().addListener(new ChangeListener<CourseComboListitems>() {
            @Override
            public void changed(ObservableValue<? extends CourseComboListitems> observable, CourseComboListitems oldValue, CourseComboListitems newValue) {

                String selectedCourse = newValue.getId();

                comboBoxBatch.getSelectionModel().clearSelection();
                comboBoxBatch.getItems().clear();

                try {
                    batchComboBoxQuery.setString(1,selectedCourse);
                    ResultSet allBatches = batchComboBoxQuery.executeQuery();

                    while (allBatches.next()){
                        comboBoxBatch.getItems().add(allBatches.getString(1));
                    }

                } catch (SQLException e) { e.printStackTrace(); }

            }
        });

        //--------------- group Radio Buttons --------------//

        radioMale.setToggleGroup(sexGroup);
        radioFemale.setToggleGroup(sexGroup);


    }

}
