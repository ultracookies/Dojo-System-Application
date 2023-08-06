package com.dojoapp.Dojo.System.Application.model;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.io.File;
import java.io.IOException;

public class JavaFXApplication extends Application {

	private ConfigurableApplicationContext applicationContext;

	@Override
	public void init() {
		var fileFolder = System.getProperty("user.home") + File.separator + "Desktop";
		var file = new File(fileFolder);
		if (file.exists()) {
			var idk = new File(file.getPath() + File.separator + "DojoApp");
			idk.mkdir();
			if (idk.exists()) {
				//TODO create a bunch of other dirs that'll be used by this application
				var profileImagesDir = new File(idk.getPath() + File.separator + "students");
				profileImagesDir.mkdir();
			}
		}
		this.applicationContext = new SpringApplicationBuilder().sources(SpringBootEntryPoint.class).run();
	}

	@Override
	public void start(Stage stage) {
		applicationContext.publishEvent(new StageReadyEvent(stage));
	}

	@Override
	public void stop() {
		applicationContext.close();
		Platform.exit();
	}

	static class StageReadyEvent extends ApplicationEvent {
		public StageReadyEvent(Stage stage) {
			super(stage);
		}

		public Stage getStage() {
			return ((Stage) getSource());
		}
	}
}

