package com.dojoapp.Dojo.System.Application.controller;

import com.dojoapp.Dojo.System.Application.CurrentStudent;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.Year;
import java.util.*;

@Component
public class AddPayment implements Initializable {

    @Autowired
    private ApplicationContext applicationContext;

    @FXML
    private Text studentNameTxt;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private TextField paymentTF;

    @FXML
    private Button submitBtn;

    @FXML
    void cancelAction(ActionEvent event) {
        backOutOfScene(event);
    }

    @FXML
    void submitAction(ActionEvent event) {
        var student = CurrentStudent.getStudent();
        int year = Year.now().getValue();
        var csvFilePath = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "DojoApp" +
                File.separator + "students" + File.separator + student.getId() + File.separator + "payments" +
                File.separator + year + ".csv";
        var csvFile = new File(csvFilePath);
        try (var printer = new CSVPrinter(new FileWriter(csvFile, true), CSVFormat.EXCEL)) {
            writeToCSV(printer);
            printer.close(true);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            backOutOfScene(event);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var student = CurrentStudent.getStudent();
        var fullName = student.getFirstName() + " " + student.getLastName();
        studentNameTxt.setText(fullName + " Payment");

        //TODO add other rates
        choiceBox.setValue("Select Amount");
        choiceBox.getItems().add("120");
        choiceBox.setOnAction(event -> paymentTF.setText(choiceBox.getValue()));

        paymentTF.disableProperty().bind(booleanBindPaymentTF().not());
        submitBtn.disableProperty().bind(booleanBindSubmitBtn().not());
    }

    private void writeToCSV(CSVPrinter printer) throws IOException {
        var amount = paymentTF.getText();
        var date = new Date();
        printer.printRecord(amount, date);
    }

    private BooleanBinding booleanBindSubmitBtn() {
        return Bindings.createBooleanBinding(() -> {
            var paymentAmount = paymentTF.getText();
            return paymentAmount != null && !paymentAmount.isBlank() && !paymentAmount.isEmpty();
        }, paymentTF.textProperty());
    }

    private BooleanBinding booleanBindPaymentTF() {
        return Bindings.createBooleanBinding(() -> {
            var selectedItem = choiceBox.getSelectionModel().getSelectedItem();
            return !selectedItem.equals("Other");
        }, choiceBox.itemsProperty());
    }

    private void backOutOfScene(ActionEvent event) {
        try {
            var resource = getClass().getResource("/fxml/Payments.fxml");
            var fxmlLoader = new FXMLLoader(resource);
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent root = fxmlLoader.load();
            var source = (Node) event.getSource();
            var stage = (Stage) source.getScene().getWindow();
            stage.setTitle("Payments");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
