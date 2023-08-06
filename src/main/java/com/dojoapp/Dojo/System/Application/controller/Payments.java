package com.dojoapp.Dojo.System.Application.controller;

import com.dojoapp.Dojo.System.Application.CurrentStudent;
import com.dojoapp.Dojo.System.Application.model.Payment;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class Payments implements Initializable {

    @FXML
    private Text studentNameTxt;

    @FXML
    private TableView<Payment> paymentsTV;

    @FXML
    private TableColumn<Payment, BigDecimal> amountCol;

    @FXML
    private TableColumn<Payment, BigDecimal> dateCol;

    @FXML
    private Button addPaymentBtn;

    @FXML
    private Button otherPaymentsBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    void addPaymentAction(ActionEvent event) {

    }

    @FXML
    void cancelAction(ActionEvent event) {

    }

    @FXML
    void otherPaymentsAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var student = CurrentStudent.getStudent();
        var fullName = student.getFirstName() + " " + student.getLastName();
        studentNameTxt.setText(fullName + " Payment");


    }

    private List<Payment> parseCSV(File file) throws IOException {
        var parser = new CSVParser(new FileReader(file), CSVFormat.EXCEL);
        var records = parser.getRecords();
        parser.close();
        List<Payment> list = records.stream().map(r -> {
            var values = r.values();
            return new Payment(values[0], values[1]);
        }).toList();
        return list;
    }
}

