package com.dojoapp.Dojo.System.Application.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@Data
public class Home implements Initializable {

    @FXML
    private TableView<?> studentSearchTV;

    @FXML
    private TableColumn<?, ?> firstNameCol;

    @FXML
    private TableColumn<?, ?> lastNameCol;

    @FXML
    private TableColumn<?, ?> rankCol;

    @FXML
    @Setter(AccessLevel.NONE)
    private Button viewBtn;

    @FXML
    @Setter(AccessLevel.NONE)
    private Button editBtn;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //boolean bind buttons next to student table view
        viewBtn.disableProperty().bind(booleanBindSearchStudentTF().not());
        editBtn.disableProperty().bind(booleanBindSearchStudentTF().not());
        promoteBtn.disableProperty().bind(booleanBindSearchStudentTF().not());
    }

    @FXML
    void addStudentAction(ActionEvent event) {

    }

    @FXML
    void editAction(ActionEvent event) {

    }

    @FXML
    void logoutAction(ActionEvent event) {

    }

    @FXML
    void paymentAction(ActionEvent event) {

    }

    @FXML
    void promoteAction(ActionEvent event) {

    }

    @FXML
    void removeStudentAction(ActionEvent event) {

    }

    @FXML
    void searchAction(ActionEvent event) {

    }

    @FXML
    void viewAction(ActionEvent event) {

    }

    private BooleanBinding booleanBindSearchStudentTF() {
        return Bindings.createBooleanBinding(() -> {
            var query = studentSearchBar.getText();
            if (query.isBlank() || query.isEmpty() || query == null)
                return false;
            else return true;
        }, studentSearchBar.textProperty());
    }
}
