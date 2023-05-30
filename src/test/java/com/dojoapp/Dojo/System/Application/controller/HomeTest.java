package com.dojoapp.Dojo.System.Application.controller;

import com.dojoapp.Dojo.System.Application.TestConfig;
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
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

@SpringBootTest(classes = TestConfig.class)
@ExtendWith(ApplicationExtension.class)
class HomeTest extends ApplicationTest {

    @Value("/fxml/Home.fxml")
    private Resource resource;

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
    void tableViewButtonsEnabledWhenTextFieldIsNotEmpty(FxRobot robot) {
        var viewBtn = controller.getViewBtn();
        var editBtn = controller.getEditBtn();
        var promoteBtn = controller.getPromoteBtn();
        var studentSearchBar = controller.getStudentSearchBar();
        robot.clickOn(studentSearchBar).write("test");

        assertAll(() -> {
            assertFalse(viewBtn.isDisabled());
            assertFalse(editBtn.isDisabled());
            assertFalse(promoteBtn.isDisabled());
        });
    }

    @Test
    void tableViewButtonsDisabledWhenTextFieldIsEmpty() {
        var viewBtn = controller.getViewBtn();
        var editBtn = controller.getEditBtn();
        var promoteBtn = controller.getPromoteBtn();
        var studentSearchBar = controller.getStudentSearchBar();

        assertAll(() -> {
            assertTrue(studentSearchBar.getText().isEmpty());
            assertTrue(viewBtn.isDisabled());
            assertTrue(editBtn.isDisabled());
            assertTrue(promoteBtn.isDisabled());
        });
    }

    @Test
    void addStudentAction(FxRobot robot) {
        var originalTitle = stage.getTitle();
        robot.clickOn(controller.getAddStudentBtn());
        var actualTitle = stage.getTitle();
        var expectedTitle = environment.getProperty("#{${stageTitlesMap}.addStudent}");
        assertAll(() -> {
            assertEquals(actualTitle, expectedTitle);
            assertNotEquals(originalTitle, actualTitle);
        });
    }

    @Test
    void editAction() {
    }

    @Test
    void logoutAction() {
    }

    @Test
    void paymentAction(FxRobot robot) {
        var originalTitle = stage.getTitle();
        robot.clickOn(controller.getPaymentBtn());
        var actualTitle = stage.getTitle();
        var expectedTitle = environment.getProperty("#{${stageTitlesMap}.payment}");
        assertAll(() -> {
            assertEquals(actualTitle, expectedTitle);
            assertNotEquals(originalTitle, actualTitle);
        });
    }

    @Test
    void promoteAction() {
    }

    @Test
    void removeStudentAction(FxRobot robot) {
        var originalTitle = stage.getTitle();
        robot.clickOn(controller.getRemoveStudentBtn());
        var expectedTitle = stage.getTitle();
        var actualTitle = environment.getProperty("#{${stageTitlesMap}.removeStudent}");
        assertAll(() -> {
            assertEquals(expectedTitle, actualTitle);
            assertNotEquals(originalTitle, actualTitle);
        });
    }

    @Test
    void searchAction() {
    }

    @Test
    void viewAction() {
    }
}