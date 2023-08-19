package com.dojoapp.Dojo.System.Application.controller;

import com.dojoapp.Dojo.System.Application.CurrentStudent;
import com.dojoapp.Dojo.System.Application.model.Payment;
import com.dojoapp.Dojo.System.Application.model.Student;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.Year;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class Payments implements Initializable {

    @Autowired
    private ApplicationContext applicationContext;

    @FXML
    private Text studentNameTxt;

    @FXML
    private TableView<Payment> paymentsTV;

    @FXML
    private TableColumn<Payment, BigDecimal> amountCol;

    @FXML
    private TableColumn<Payment, BigDecimal> dateCol;

    @FXML
    void addPaymentAction(ActionEvent event) {
        try {
            var resource = getClass().getResource("/fxml/AddPayment.fxml");
            var fxmlLoader = new FXMLLoader(resource);
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent root = fxmlLoader.load();
            var source = (Node) event.getSource();
            var stage = (Stage) source.getScene().getWindow();
            stage.setTitle("Add Payment");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void cancelAction(ActionEvent event) {
        closeWindow(event);
    }

    @FXML
    void otherPaymentsAction() {
        var fileChooser = new FileChooser();
        fileChooser.setTitle("Select Payment Record");
        var extensionFilter = new FileChooser.ExtensionFilter("CSV File", "*.csv");
        fileChooser.getExtensionFilters().add(extensionFilter);
        var student = CurrentStudent.getStudent();
        var csvDir = System.getProperty("user.home") +
                File.separator + "Desktop" +
                File.separator + "DojoApp" +
                File.separator + "students" +
                File.separator + student.getId() +
                File.separator + "payments";
        fileChooser.setInitialDirectory(new File(csvDir));
        var file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                var paymentsList = parseCSV(file);
                var observableList = FXCollections.observableList(paymentsList);
                paymentsTV.setItems(observableList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        var student = CurrentStudent.getStudent();
        var fullName = student.getFirstName() + " " + student.getLastName();
        studentNameTxt.setText(fullName + " Payment");
        var csvFile = loadCSV(student);
        try {
            var paymentsList = parseCSV(csvFile);
            var observableList = FXCollections.observableList(paymentsList);
            paymentsTV.setItems(observableList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO every year a new CSV file should be created
    //int year = Year.now().getValue();
    private File createAnnualCSV(String csvFilePath) throws IOException {
        var newCSV = new File(csvFilePath);
        if (newCSV.createNewFile()) {
            String[] headers = {"Amount", "Date"};
            var printer = new CSVPrinter(new FileWriter(newCSV), CSVFormat.EXCEL);
            printer.printRecord((Object[]) headers);
            printer.close(true);
            return newCSV;
        }
        else {
            System.out.println(newCSV.getPath() + " couldn't be created.");
            return null;
        }
    }

    //TODO latest CSV file should be loaded into the TableView
    private File loadCSV(Student student)  {
        var year = Year.now().getValue();
        var csvDir = System.getProperty("user.home") +
                File.separator + "Desktop" +
                File.separator + "DojoApp" +
                File.separator + "students" +
                File.separator + student.getId() +
                File.separator + "payments";
        var csvFilePath = csvDir + File.separator + year + ".csv";
        var csvFile = new File(csvFilePath);
        if (!csvFile.exists()) {
            try {
                csvFile = createAnnualCSV(csvFilePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return csvFile;
    }

    private List<Payment> parseCSV(File file) throws IOException {
        var parser = new CSVParser(new FileReader(file), CSVFormat.EXCEL);
        var records = parser.getRecords();
        parser.close();
        List<Payment> list = new LinkedList<>();
        var iterator = records.iterator();
        iterator.next();
        while (iterator.hasNext()) {
            var r = iterator.next().values();
            list.add(new Payment(r[0], r[1]));
        }
        return list;
    }

    private void closeWindow(ActionEvent event) {
        var source = (Node) event.getSource();
        var window = source.getScene().getWindow();
        window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
}

