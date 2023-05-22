package com.dojoapp.Dojo.System.Application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class Home {

    @FXML
    private TableView<?> studentSearchTV;

    @FXML
    private TableColumn<?, ?> firstNameCol;

    @FXML
    private TableColumn<?, ?> lastNameCol;

    @FXML
    private TableColumn<?, ?> rankCol;

    @FXML
    private Button viewBtn;

    @FXML
    private Button editBtn;

    @FXML
    private Button promoteBtn;

    @FXML
    private TextField studentSearchTF;

    @FXML
    private Button searchBtn;

    @FXML
    private Button logoutBtn;

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

}
