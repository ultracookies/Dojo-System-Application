package com.dojoapp.Dojo.System.Application.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.dojoapp.Dojo.System.Application.model.JavaFXApplication.StageReadyEvent;

import java.io.IOException;

@Component
public class StageListener implements ApplicationListener<StageReadyEvent> {

    @Value("classpath:/fxml/Home.fxml")
    private Resource resource;

    private final String applicationTitle;

    final private ApplicationContext applicationContext;

    StageListener(@Value("#{${stageTitlesMap}.home}") String applicationTitle,
                  ApplicationContext applicationContext) {
        this.applicationTitle = applicationTitle;
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        try {
            var fxmlLoader = new FXMLLoader(resource.getURL());
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent root = fxmlLoader.load();
            var stage = event.getStage();
            stage.setTitle(applicationTitle);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
