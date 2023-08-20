package com.dojoapp.Dojo.System.Application.controller;

import com.dojoapp.Dojo.System.Application.CurrentStudent;
import com.dojoapp.Dojo.System.Application.model.Address;
import com.dojoapp.Dojo.System.Application.model.AddressRepository;
import com.dojoapp.Dojo.System.Application.model.Student;
import com.dojoapp.Dojo.System.Application.model.StudentRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
public class EditStudent implements Initializable {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AddressRepository addressRepository;

    @FXML
    private Text studentName;

    @FXML
    private Text ageTxt;

    @FXML
    private Text sexTxt;

    @FXML
    private Text dobTxt;

    @FXML
    private Text dateBeganTxt;

    @FXML
    private Text customIDTxt;

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
    private TextField addressTF;

    @FXML
    private TextField cityTF;

    @FXML
    private TextField stateTF;

    @FXML
    private TextField zipcodeTF;

    @FXML
    private ImageView imageView;

    @FXML
    private Button saveBtn;

    @FXML
    private Text rankTxt;

    private File studentImageFile;

    private boolean isStudentInfoChanged;
    private boolean isAddressInfoChanged;
    private boolean isImageChanged;

    @FXML
    void changeImageAction() {
        try {
            var fileChooser = new FileChooser();
            var extensionFilter = new FileChooser.ExtensionFilter("Image Files",
                    "*.jpeg", "*.png", "*.bmp", "*.jpg");
            fileChooser.getExtensionFilters().add(extensionFilter);
            fileChooser.setTitle("Set Student Image");
            studentImageFile = fileChooser.showOpenDialog(null);
            if (studentImageFile != null) {
                var bufferedInputStream = new BufferedInputStream(new FileInputStream(studentImageFile));
                imageView.setImage(new Image(bufferedInputStream));
                bufferedInputStream.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cancelAction(ActionEvent event) {
        backOutPage(event);
    }

    @FXML
    void saveAction(ActionEvent event) {
        var student = CurrentStudent.getStudent();
        if (isStudentInfoChanged)
            saveToStudentRepository(student);
        if (isAddressInfoChanged) {
            var address = addressRepository
                    .findById(student.getAddressID()).get();
            saveToAddressRepository(address);
        }
        if (isImageChanged) copyAndSaveImage(studentImageFile);
        backOutPage(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var student = CurrentStudent.getStudent();
        setStudentName(student);
        fillStudentTextFields(student);
        fillAddressTextFields(student);

        customIDTxt.setText(student.getCustomID());
        sexTxt.setText(student.getSex().toString());
        dobTxt.setText(student.getBirthDate());
        dateBeganTxt.setText(student.getDateBegan());
        rankTxt.setText(student.getRank().toString());

        setOriginalImage(student.getId());
        addListeners();
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

    private void saveToAddressRepository(Address address) {
        address.setStreet(addressTF.getText());
        address.setCity(cityTF.getText());
        address.setState(stateTF.getText());
        address.setZipcode(zipcodeTF.getText());
        addressRepository.save(address);
    }

    private void saveToStudentRepository(Student student) {
        student.setFirstName(firstNameTF.getText());
        student.setMiddleName(middleNameTF.getText());
        student.setLastName(lastNameTF.getText());
        student.setHeight(heightTF.getText());
        student.setWeight(weightTF.getText());
        studentRepository.save(student);
    }

    private void setOriginalImage(Long studentID) {
        var imagePath = System.getProperty("user.home") + File.separator + "Desktop" +
                File.separator + "DojoApp" + File.separator + "students" +
                File.separator + studentID + File.separator + "profile-image.png";
        var imageFile = new File(imagePath);
        if (imageFile.exists()) {
            try {
                var fileInputStream = new FileInputStream(imageFile);
                var image = new Image(fileInputStream);
                imageView.setImage(image);
                fileInputStream.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addListeners() {
        firstNameTF.textProperty().addListener(observable -> isStudentInfoChanged = true);
        middleNameTF.textProperty().addListener(observable -> isStudentInfoChanged = true);
        lastNameTF.textProperty().addListener(observable -> isStudentInfoChanged = true);
        firstNameTF.textProperty().addListener(observable -> isStudentInfoChanged = true);
        heightTF.textProperty().addListener(observable -> isStudentInfoChanged = true);
        weightTF.textProperty().addListener(observable -> isStudentInfoChanged = true);

        addressTF.textProperty().addListener(observable -> isAddressInfoChanged = true);
        cityTF.textProperty().addListener(observable -> isAddressInfoChanged = true);
        stateTF.textProperty().addListener(observable -> isAddressInfoChanged = true);
        zipcodeTF.textProperty().addListener(observable -> isAddressInfoChanged = true);

        imageView.imageProperty().addListener(((observableValue) -> isImageChanged = true));
    }

    private void backOutPage(ActionEvent event) {
        try {
            var source = (Node) event.getSource();
            var stage = (Stage) source.getScene().getWindow();
            var resource = getClass().getResource("/fxml/StudentProfile.fxml");
            var fxmlLoader = new FXMLLoader(resource);
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent root = fxmlLoader.load();
            stage.setTitle(studentName.getText() + "'s Profile");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillAddressTextFields(Student student) {
        var addressObj = addressRepository.findById(student.getAddressID()).get();
        addressTF.setText(addressObj.getStreet());
        cityTF.setText(addressObj.getCity());
        stateTF.setText(addressObj.getState());
        zipcodeTF.setText(addressObj.getZipcode());
    }

    private void fillStudentTextFields(Student student) {
        firstNameTF.setText(student.getFirstName());
        middleNameTF.setText(student.getMiddleName());
        lastNameTF.setText(student.getLastName());
        heightTF.setText(student.getHeight());
        weightTF.setText(student.getWeight());
    }

    private void setStudentName(Student student) {
        var fullName = new StringBuilder(student.getFirstName());
        if (student.getMiddleName() != null)
            fullName.append(" ").append(student.getMiddleName());
        fullName.append(" ").append(student.getLastName());
        studentName.setText(fullName.toString());
    }
}
