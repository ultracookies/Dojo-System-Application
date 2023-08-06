package com.dojoapp.Dojo.System.Application.controller;

import com.dojoapp.Dojo.System.Application.CurrentStudent;
import com.dojoapp.Dojo.System.Application.model.AddressRepository;
import com.dojoapp.Dojo.System.Application.model.StudentRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.WindowEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class StudentProfile implements Initializable {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AddressRepository addressRepository;

    @FXML
    private Text studentName;

    @FXML
    private Text firstNameTxt;

    @FXML
    private Text middleNameTxt;

    @FXML
    private Text lastNameTxt;

    @FXML
    private Text heightTxt;

    @FXML
    private Text weightTxt;

    @FXML
    private Text ageTxt;

    @FXML
    private Text sexTxt;

    @FXML
    private Text dobTxt;

    @FXML
    private Text dateBeganTxt;

    @FXML
    private Text addressTxt;

    @FXML
    private Text cityTxt;

    @FXML
    private Text stateTxt;

    @FXML
    private Text zipcodeTxt;

    @FXML
    private ImageView imageView;

    @FXML
    private Button closeBtn;

    @FXML
    private Button editBtn;

    @FXML
    private Text rankTxt;

    @FXML
    private Button otherFilesBtn;

    @FXML
    private Button changeDirectoryBtn;

    @FXML
    private TextArea notesTA;

    @FXML
    private Button loadNotesBtn;

    @FXML
    private Text customIDTxt;

    @FXML
    void loadNotesAction() {

    }

    @FXML
    void changeDirectoryAction() {

    }

    @FXML
    void otherFilesAction() {

    }

    @FXML
    void closeAction(ActionEvent event) {
        var source = (Node) event.getSource();
        var window = source.getScene().getWindow();
        window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    @FXML
    void editBtn() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var student = CurrentStudent.getStudent();
        studentName.setText(student.getFirstName() + " " + student.getLastName());
        firstNameTxt.setText(student.getFirstName());
        lastNameTxt.setText(student.getLastName());
        rankTxt.setText(student.getRank().toString());
        dobTxt.setText(student.getBirthDate());
        middleNameTxt.setText(student.getMiddleName());
        dateBeganTxt.setText(student.getDateBegan());
        customIDTxt.setText(String.valueOf(student.getCustomID()));

        var address = addressRepository.findById(student.getAddressID()).get();
        addressTxt.setText(address.getStreet());
        cityTxt.setText(address.getCity());
        zipcodeTxt.setText(address.getZipcode());
        stateTxt.setText(address.getState());
    }

}
