package com.dojoapp.Dojo.System.Application.controller;

import com.dojoapp.Dojo.System.Application.TestConfig;
import com.dojoapp.Dojo.System.Application.model.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.test.context.jdbc.Sql;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

@SpringBootTest(classes = TestConfig.class)
@ExtendWith(ApplicationExtension.class)
@Sql("/schema.sql")
class HomeTest extends ApplicationTest {

    /*
    TODO
     - fix viewBtn, editBtn, promoteBtn visibility tests so that it only passes if student is selected in studentTV
     - if the above will be tested, find a way to populate studentTV as well
     - only viewBtn and editBtn should open separate windows
     */

    @Value("/fxml/Home.fxml")
    private Resource resource;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private Environment environment;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Home controller;

    private Stage stage;

    public void start(Stage stage) throws IOException {
        var fxmlLoader = new FXMLLoader(resource.getURL());
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        Parent root = fxmlLoader.load();
        var scene = new Scene(root);
        scene.getRoot().setStyle("-fx-font-family: 'serif'");
        var stageTitle = environment.getProperty("#{${stageTitlesMap}.home}");
        stage.setTitle(stageTitle);
        stage.setScene(scene);
        this.stage = stage;
        stage.show();
    }

    @Test
    void viewBtnIsDisabledWhenStudentTableViewIsEmpty() {
        assertAll(() -> {
            assertThat(controller.getStudentSearchTV().getItems()).isEmpty();
            assertThat(controller.getViewBtn()).isDisabled();
        });
    }

    @Test
    void studentSearchTableViewPopulates(FxRobot robot) {
        var address = new Address();
        var addressID = addressRepository.save(address).getId();
        var student = new Student("8834343", "John", "Doe", "1/1/1980", Rank.WHITE, addressID, 'M');
        studentRepository.save(student);
        robot.clickOn(controller.getStudentSearchBar())
                .write("John")
                .clickOn(controller.getSearchBtn());
        assertThat(controller.getStudentSearchTV().getItems()).asList().isNotEmpty();
        studentRepository.delete(student);
    }

    @Test
    void searchBtnIsEnabledWhenStudentTextFieldIsNotEmpty(FxRobot robot) {
        robot.clickOn(controller.getStudentSearchBar())
                .write("test");
        assertThat(controller.getSearchBtn()).isEnabled();
    }

    @Test
    void searchBtnIsDisabledWhenStudentTextFieldIsEmpty() {
        assertAll(() -> {
            assertThat(controller.getStudentSearchBar()).hasText("");
            assertThat(controller.getSearchBtn()).isDisabled();
        });
    }

    @Test
    void addStudentAction(FxRobot robot) {
        robot.clickOn(controller.getAddStudentBtn());
        var actualTitle = stage.getTitle();
        var expectedTitle = environment.getProperty("#{${stageTitlesMap}.addStudent}");
        assertEquals(actualTitle, expectedTitle);
    }

    @Test
    void editAction() {
    }

    @Test
    void logoutAction() {
    }

    @Test
    void paymentAction(FxRobot robot) {
        robot.clickOn(controller.getPaymentBtn());
        var actualTitle = stage.getTitle();
        var expectedTitle = environment.getProperty("#{${stageTitlesMap}.payment}");
        assertEquals(actualTitle, expectedTitle);
    }

    @Test
    void promoteAction() {
    }

    @Test
    void removeStudentAction(FxRobot robot) {
        robot.clickOn(controller.getRemoveStudentBtn());
        var expectedTitle = stage.getTitle();
        var actualTitle = environment.getProperty("#{${stageTitlesMap}.removeStudent}");
        assertEquals(actualTitle, expectedTitle);
    }

    @Test
    void searchAction() {
    }

    @Test
    void viewAction() {
    }
}