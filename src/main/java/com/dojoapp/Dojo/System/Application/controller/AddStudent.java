package com.dojoapp.Dojo.System.Application.controller;

import com.dojoapp.Dojo.System.Application.CurrentStudent;
import com.dojoapp.Dojo.System.Application.model.*;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.*;

@Component
public class AddStudent implements Initializable {

    @Autowired
    private ApplicationContext applicationContext;

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

    @FXML
    private ImageView imageView;

    final private PseudoClass invalidInputClass = PseudoClass.getPseudoClass("invalidInput");

    private File studentImageFile;

    @FXML
    void uploadImageAction() {
        var fileChooser = new FileChooser();
        var extensionFilter = new FileChooser.ExtensionFilter("Image Files",
                "*.jpeg", "*.png", "*.bmp", "*.jpg");
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.setTitle("Set Student Image");
        studentImageFile = fileChooser.showOpenDialog(null);
        if (studentImageFile != null) {
            try {
                var bufferedInputStream = new BufferedInputStream(new FileInputStream(studentImageFile));
                imageView.setImage(new Image(bufferedInputStream));
                bufferedInputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void uploadPreMadeFileAction() {
        var fileChooser = new FileChooser();
        fileChooser.setTitle("Upload Pre-made File");
        var extensionFilter = new FileChooser.ExtensionFilter("Documents",
                "*.pdf", "*.pages");
        fileChooser.getExtensionFilters().add(extensionFilter);

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
            var currentStudent = studentRepository.save(student);
            createStudentDir(currentStudent);
            CurrentStudent.setStudent(currentStudent);
            try {
                copyAndSaveImage(studentImageFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }



//            var url = getClass().getResource("/fxml/AddProfileImage.fxml");
//            var newStage = new Stage();
//            var fxmlLoader = new FXMLLoader(url);
//            fxmlLoader.setControllerFactory(applicationContext::getBean);
//            try {
//                Parent root = fxmlLoader.load();
//                newStage.setScene(new Scene(root));
//                newStage.show();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }

            closeWindow(event);
        }
        else {
            invalidInputsList.stream()
                    .forEach(textField -> textField.pseudoClassStateChanged(invalidInputClass, true));
        }
    }

    private void copyAndSaveImage(File imageFile) throws IOException {
        var bufferedImage = ImageIO.read(imageFile);
        var student = Objects.requireNonNull(CurrentStudent.getStudent());
        var destPath = System.getProperty("user.home") + File.separator + "Desktop" +
                File.separator + "DojoApp" + File.separator + "students" +
                File.separator + student.getId() + File.separator;
        //TODO could probably store directory paths in application.properties
        var dest = new File(destPath + "profile-image.png");
        dest.createNewFile();
        ImageIO.write(bufferedImage, "png", dest);
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

    private void createStudentDir(Student student) {
        var appDataPath = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "DojoApp" + File.separator + "students" + File.separator;
        var studentDir = new File(appDataPath + student.getId());
        studentDir.mkdir();
        var studentDirPath = studentDir.getPath() + File.separator;
        var paymentsDir = new File(studentDirPath + "payments");
        paymentsDir.mkdir();
        var notesDir = new File(studentDirPath + "notes");
        notesDir.mkdir();
        var otherDocsDir = new File(studentDirPath + "otherDocuments");
        otherDocsDir.mkdir();
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
