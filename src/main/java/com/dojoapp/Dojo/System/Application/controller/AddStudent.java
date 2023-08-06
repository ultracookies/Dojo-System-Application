package com.dojoapp.Dojo.System.Application.controller;

import com.dojoapp.Dojo.System.Application.model.*;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class AddStudent implements Initializable {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private StudentRepository studentRepository;

    @FXML
    private TextField firstNameTF;

    @FXML
    private TextField middleNameTF;

    @FXML
    private TextField lastNameTF;

    @FXML
    private TextField dateOfBirthTF;

    @FXML
    private TextField heightTF;

    @FXML
    private TextField weightTF;

    @FXML
    private TextField phoneNumberTF;

    @FXML
    private RadioButton maleRB;

    @FXML
    private RadioButton femaleRB;

    @FXML
    private TextField streetTF;

    @FXML
    private TextField cityTF;

    @FXML
    private TextField stateTF;

    @FXML
    private TextField zipcodeTF;

    @FXML
    private ImageView imageView;

    @FXML
    private Button uploadImageBtn;

    @FXML
    private TextArea notesTA;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private ChoiceBox<Rank> choiceBox;

    @FXML
    private Button uploadPreMadeFileBtn;

    @FXML
    private TextField customIDTF;

    final private PseudoClass invalidInputClass = PseudoClass.getPseudoClass("invalidInput");

    @FXML
    void uploadPreMadeFileAction() {

    }

    @FXML
    void cancelAction(ActionEvent event) {
        closeWindow(event);
    }

    @FXML
    void saveAction(ActionEvent event) {
        var invalidInputsList = validateInput();
        if (invalidInputsList.isEmpty()) {
            var address = createAddress();
            address = addressRepository.save(address);
            var student = createStudent(address.getId());
            studentRepository.save(student);
            closeWindow(event);
        }
        else {
            invalidInputsList.stream()
                    .forEach(textField -> textField.pseudoClassStateChanged(invalidInputClass, true));
        }
    }

    @FXML
    void uploadImageAction() {
        var fileChooser = new FileChooser();

    }

    private Address createAddress() {
        var street = streetTF.getText();
        var city = cityTF.getText();
        var state = stateTF.getText();
        var zipcode = zipcodeTF.getText();
        return new Address(street, city, zipcode, state);
    }

    private Student createStudent(Long addressID) {
        var customID = customIDTF.getText();
        var firstName = firstNameTF.getText();
        var middleName = middleNameTF.getText();
        var lastName = lastNameTF.getText();
        var sex = maleRB.isSelected() ? maleRB.getText() : femaleRB.getText();
        var dateOfBirth = dateOfBirthTF.getText();

        var selectedRank = choiceBox.getSelectionModel().getSelectedItem();
        var rank = selectedRank == null ? Rank.WHITE : selectedRank;

        var student = new Student(customID, firstName, lastName, dateOfBirth, rank, addressID, sex.charAt(0));

        var height = heightTF.getText();
        var weight = weightTF.getText();
        var phoneNumber = phoneNumberTF.getText();
        student.setMiddleName(middleName);
        student.setHeight(height);
        student.setWeight(weight);
        student.setPhoneNumber(phoneNumber);
        return student;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Collections.addAll(choiceBox.getItems(),
                Rank.WHITE,
                Rank.YELLOW,
                Rank.GREEN,
                Rank.PURPLE,
                Rank.BROWN,
                Rank.BLACK);
    }

    private List<TextField> validateInput() {
        List<TextField> textFields = new LinkedList<>();
        textFields.add(customIDTF);
        textFields.add(firstNameTF);
        textFields.add(lastNameTF);
        textFields.add(dateOfBirthTF);

        var invalidatedInputs = new LinkedList<TextField>();

        for (var tf : textFields) {
            tf.pseudoClassStateChanged(invalidInputClass, false);
            var field = tf.getText();
            if (field.isEmpty()) invalidatedInputs.add(tf);
        }

        return invalidatedInputs;
    }

    private void closeWindow(ActionEvent event) {
        var source = (Node) event.getSource();
        var window = source.getScene().getWindow();
        window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
}
