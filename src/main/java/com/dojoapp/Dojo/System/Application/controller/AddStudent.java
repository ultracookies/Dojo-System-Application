package com.dojoapp.Dojo.System.Application.controller;

import com.dojoapp.Dojo.System.Application.CurrentStudent;
import com.dojoapp.Dojo.System.Application.model.*;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.WindowEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.URL;
import java.util.*;

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
    private ChoiceBox<Rank> choiceBox;

    @FXML
    private Button uploadPreMadeFileBtn;

    @FXML
    private TextField customIDTF;

    @FXML
    private ImageView imageView;

    @FXML
    private DatePicker datePicker;

    public ToggleGroup sex;

    public Button cancelBtn;

    private File studentImageFile;

    private File preMadeFile;

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
        preMadeFile = fileChooser.showOpenDialog(null);
    }

    @FXML
    void cancelAction(ActionEvent event) {
        closeWindow(event);
    }

    @FXML
    void saveAction(ActionEvent event) {
        var address = createAddress();
        address = addressRepository.save(address);
        var student = createStudent(address.getId());
        var currentStudent = studentRepository.save(student);
        createStudentDir(currentStudent);
        CurrentStudent.setStudent(currentStudent);
        if (studentImageFile != null)
            copyAndSaveImage(studentImageFile);
        if (!notesTA.getText().isBlank()) {
            saveNotes();
        }
        if (preMadeFile != null) copyAndSaveFile(preMadeFile);
        closeWindow(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datePicker.getEditor().setDisable(true);
        Collections.addAll(choiceBox.getItems(),
                Rank.WHITE,
                Rank.YELLOW,
                Rank.GREEN,
                Rank.PURPLE,
                Rank.BROWN,
                Rank.BLACK);

        saveBtn.disableProperty().bind(booleanBindButtons().not());
        uploadPreMadeFileBtn.disableProperty().bind(booleanBindButtons());
    }

    private void copyAndSaveImage(File imageFile) {
        try {
            var bufferedImage = ImageIO.read(imageFile);
            var student = Objects.requireNonNull(CurrentStudent.getStudent());
            var destPath = System.getProperty("user.home") + File.separator + "Desktop" +
                    File.separator + "DojoApp" + File.separator + "students" +
                    File.separator + student.getId() + File.separator;
            //TODO could probably store directory paths in application.properties
            var dest = new File(destPath + "profile-image.png");
            ImageIO.write(bufferedImage, "png", dest);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyAndSaveFile(File file) {
        try {
            var student = CurrentStudent.getStudent();
            var fileInputStream = new FileInputStream(file);
            var allBytes = fileInputStream.readAllBytes();
            fileInputStream.close();

            var destPath = System.getProperty("user.home") + File.separator + "Desktop" +
                    File.separator + "DojoApp" + File.separator + "students" +
                    File.separator + student.getId() + File.separator + "otherDocuments" +
                    File.separator + file.getName();
            var dest = new File(destPath);

            var fileOutputStream = new FileOutputStream(dest);
            fileOutputStream.write(allBytes);
            fileOutputStream.flush();
            fileOutputStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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
        var dateOfBirth = datePicker.getValue().toString();

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

    private void saveNotes() {
        var fileChooser = new FileChooser();
        fileChooser.setTitle("Save Notes");
        var student = CurrentStudent.getStudent();
        var notesFilePath = System.getProperty("user.home") +
                File.separator + "Desktop" +
                File.separator + "DojoApp" +
                File.separator + "students" +
                File.separator + student.getId() +
                File.separator + "notes";
        fileChooser.setInitialDirectory(new File(notesFilePath));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text document (*.txt)", "*.txt"));
        fileChooser.setInitialFileName("*.txt");
        var file = fileChooser.showSaveDialog(null);
        if (file != null) {
            if (file.getName().endsWith(".txt"))
                printText(file);
        }
    }

    private BooleanBinding booleanBindButtons() {
        return Bindings.createBooleanBinding(() -> {
            var id = customIDTF.getText();
            var firstName = firstNameTF.getText();
            var lastName = lastNameTF.getText();
            var dateOfBirth = datePicker.getValue();
                    return !id.isBlank() && !firstName.isBlank()
                            && !lastName.isBlank() && dateOfBirth != null
                            && maleRB.isSelected() != femaleRB.isSelected();
                }, customIDTF.textProperty(), firstNameTF.textProperty(),
                lastNameTF.textProperty(), datePicker.valueProperty(),
                maleRB.selectedProperty(), femaleRB.selectedProperty());
    }

    private void printText(File file) {
        try (final var printWriter = new PrintWriter(file)) {
            printWriter.print(notesTA.getText());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
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

    private void closeWindow(ActionEvent event) {
        var source = (Node) event.getSource();
        var window = source.getScene().getWindow();
        window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
}
