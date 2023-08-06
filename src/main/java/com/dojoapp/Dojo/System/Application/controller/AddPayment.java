package com.dojoapp.Dojo.System.Application.controller;

import com.dojoapp.Dojo.System.Application.CurrentStudent;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class AddPayment implements Initializable {

    @FXML
    private Text studentNameTxt;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private TextField paymentTF;

    @FXML
    private Button submitBtn;

    @FXML
    void cancelAction() {

    }

    @FXML
    void submitAction() {
        var student = CurrentStudent.getStudent();
        var csvFilePath = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "DojoApp" +
                File.separator + "students" + File.separator + student.getId() + File.separator + "payments.csv";
        var csvFile = new File(csvFilePath);
        try (var printer = new CSVPrinter(new FileWriter(csvFile), CSVFormat.EXCEL)) {
            if (!csvFile.exists()) {
                if (csvFile.createNewFile()) System.out.println("CSV file created!");
                printer.printRecord("amount,date");
            }
            writeToCSV(printer);
            printer.close(true);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var student = CurrentStudent.getStudent();
        var fullName = student.getFirstName() + " " + student.getLastName();
        studentNameTxt.setText(fullName + " Payment");

        var observableList = FXCollections.emptyObservableList();
        //TODO add other rates
        observableList.add("Other");

        paymentTF.disableProperty().bind(booleanBindPaymentTF().not());
        submitBtn.disableProperty().bind(booleanBindSubmitBtn().not());
    }

    private void writeToCSV(CSVPrinter printer) throws IOException {
        var amount = paymentTF.getText();
        var date = new Date();
        printer.printRecord(amount, date);
    }

    private BooleanBinding booleanBindSubmitBtn() {
        return Bindings.createBooleanBinding(() -> !paymentTF.getText().isEmpty());
    }

    private BooleanBinding booleanBindPaymentTF() {
        return Bindings.createBooleanBinding(() ->
                choiceBox.getSelectionModel().getSelectedItem()
                        .equals("Other"));
    }
}

