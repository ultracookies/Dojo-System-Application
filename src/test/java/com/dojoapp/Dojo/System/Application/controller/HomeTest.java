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
import org.springframework.core.io.Resource;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestConfig.class)
@ExtendWith(ApplicationExtension.class)
class HomeTest {

    @Value("/fxml/Home.fxml")
    private Resource resource;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Home controller;

    @Start
    public void start(Stage stage) throws IOException {
        var fxmlLoader = new FXMLLoader(resource.getURL());
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        Parent root = fxmlLoader.load();
        stage.setScene(new Scene(root));
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
    void addStudentAction() {
    }

    @Test
    void editAction() {
    }

    @Test
    void logoutAction() {
    }

    @Test
    void paymentAction() {
    }

    @Test
    void promoteAction() {
    }

    @Test
    void removeStudentAction() {
    }

    @Test
    void searchAction() {
    }

    @Test
    void viewAction() {
    }
}