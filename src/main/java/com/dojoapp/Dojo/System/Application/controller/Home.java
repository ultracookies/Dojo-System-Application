package com.dojoapp.Dojo.System.Application.controller;

import com.dojoapp.Dojo.System.Application.CurrentStudent;
import com.dojoapp.Dojo.System.Application.model.*;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.*;

@Component
@Data
public class Home implements Initializable {

    @Value("/fxml/AddStudent.fxml")
    private Resource addStudentResource;

    @Value("/fxml/StudentProfile.fxml")
    private Resource viewStudentResource;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private StudentRepository studentRepository;

    @FXML
    private TableView<StudentSearchModel> studentSearchTV;

    @FXML
    private TableColumn<StudentSearchModel, String> studentIDCol;

    @FXML
    private TableColumn<StudentSearchModel, String> firstNameCol;

    @FXML
    private TableColumn<StudentSearchModel, String> lastNameCol;

    @FXML
    private TableColumn<StudentSearchModel, String> rankCol;

    @FXML
    @Setter(AccessLevel.NONE)
    private Button viewBtn;

    @FXML
    @Setter(AccessLevel.NONE)
    private Button promoteBtn;

    @FXML
    @Setter(AccessLevel.NONE)
    private TextField studentSearchBar;

    @FXML
    private Button searchBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    @Setter(AccessLevel.NONE)
    private RadioButton searchByFirstNameRB;

    @FXML
    @Setter(AccessLevel.NONE)
    private RadioButton searchByLastNameRB;

    @FXML
    @Setter(AccessLevel.NONE)
    private Button addStudentBtn;

    @FXML
    @Setter(AccessLevel.NONE)
    private Button paymentBtn;

    @FXML
    @Setter(AccessLevel.NONE)
    private Button removeStudentBtn;

    private FXMLLoader fxmlLoader;
    private int count = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fxmlLoader = new FXMLLoader();
        searchByFirstNameRB.fire();

        searchBtn.disableProperty()
                .bind(booleanBindSearchStudentTF().not());

        viewBtn.disableProperty()
                        .bind(booleanBindStudentSelection().not());
        promoteBtn.disableProperty()
                        .bind(booleanBindSearchStudentButtons().not());

        studentIDCol.setCellValueFactory(new PropertyValueFactory<>("customID"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        rankCol.setCellValueFactory(new PropertyValueFactory<>("rank"));

        var address = new Address("123 Main St.", "FarAwayVille", "47363", "NY");
        var addressID = addressRepository.save(address).getId();

        var student1 = new Student("48324", "John", "Doe", "1/1/1980", Rank.WHITE, addressID, 'M');
        var student2 = new Student("843483", "Jane", "Doe", "1/1/1979", Rank.YELLOW, addressID, 'F');
        studentRepository.save(student1);
        studentRepository.save(student2);
    }

    @FXML
    void addStudentAction(ActionEvent event) {
        try {
            var fxmlLoader = new FXMLLoader(addStudentResource.getURL());
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent root = fxmlLoader.load();
            var windowTitle = "Add Student";
            var primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ++count;
            primaryStage.setOnCloseRequest(Event::consume);
            var scene = new Scene(root);
            var css = Objects.requireNonNull(this.getClass().getResource("/stylesheets/addStudent.css")).toExternalForm();
            scene.getStylesheets().add(css);
            var newStage = new Stage();
            newStage.setTitle(windowTitle);
            newStage.setScene(scene);
            newStage.show();
            newStage.setOnCloseRequest(we -> {
                if (--count == 0)
                    primaryStage.setOnCloseRequest(windowEvent -> {});
            });
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void logoutAction() {

    }

    @FXML
    void paymentAction(ActionEvent event) {
        try {
            var selectedStudent = getSelectedStudent();
            CurrentStudent.setStudent(selectedStudent);
            var resourceURL = getClass().getResource("/fxml/Payments.fxml");
            var fxmlLoader = new FXMLLoader(resourceURL);
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent root = fxmlLoader.load();
            var windowTitle = selectedStudent.getFirstName() + " " +
                    selectedStudent.getLastName() + "'s Payment";
            var primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ++count;
            primaryStage.setOnCloseRequest(Event::consume);
            var newStage = new Stage();
            newStage.setTitle(windowTitle);
            newStage.setScene(new Scene(root));
            newStage.show();
            newStage.setOnCloseRequest(we -> {
                if (--count == 0)
                    primaryStage.setOnCloseRequest(windowEvent -> {});
            });
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void promoteAction() {

    }

    @FXML
    void removeStudentAction() {
        //delete all files/documents associated with student
    }

    @FXML
    void searchAction() {
        var query = studentSearchBar.getText();
        List<Student> result;
        if (searchByLastNameRB.isSelected())
            result = studentRepository.findByLastName(query);
        else result = studentRepository.findByFirstName(query);
        var studentSearchModelList = result.stream()
                .map(StudentSearchModel::new)
                .toList();
        ObservableList<StudentSearchModel> observableList = FXCollections.observableList(studentSearchModelList);
        studentSearchTV.setItems(observableList);
    }

    @FXML
    void viewAction(ActionEvent event) {
        try {
            var selectedStudent = getSelectedStudent();
            CurrentStudent.setStudent(selectedStudent);
            var fxmlLoader = new FXMLLoader(viewStudentResource.getURL());
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent root = fxmlLoader.load();
            var windowTitle = selectedStudent.getFirstName() + " " +
                    selectedStudent.getLastName() + "'s Profile";
            var primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ++count;
            primaryStage.setOnCloseRequest(Event::consume);
            var newStage = new Stage();
            newStage.setTitle(windowTitle);
            newStage.setScene(new Scene(root));
            newStage.show();
            newStage.setOnCloseRequest(we -> {
                if (--count == 0)
                    primaryStage.setOnCloseRequest(windowEvent -> {});
            });
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Student getSelectedStudent() {
        var studentSearchModel = studentSearchTV.getSelectionModel().getSelectedItem();
        var studentID = studentSearchModel.getStudentID();
        var optional = studentRepository.findById(studentID);
        return optional.orElse(null);
    }

    private BooleanBinding booleanBindStudentSelection() {
        return Bindings.createBooleanBinding(() -> {
            var selectedItem = studentSearchTV.getSelectionModel().getSelectedItem();
            return selectedItem != null;
        }, studentSearchTV.getSelectionModel().selectedItemProperty());
    }

    private BooleanBinding booleanBindSearchStudentButtons() {
        return Bindings.createBooleanBinding(() ->
                !studentSearchTV.getItems().isEmpty(), studentSearchTV.itemsProperty());
    }

    private BooleanBinding booleanBindSearchStudentTF() {
        return Bindings.createBooleanBinding(() -> {
            var query = studentSearchBar.getText();
            return query != null && !query.isBlank() && !query.isEmpty();
        }, studentSearchBar.textProperty());
    }
}
