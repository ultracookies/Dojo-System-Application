package com.dojoapp.Dojo.System.Application.model;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class JavaFXApplication extends Application {

	private ConfigurableApplicationContext context;

	@Override
	public void init() throws Exception {
		ApplicationContextInitializer<GenericApplicationContext> initializer =
				new ApplicationContextInitializer<GenericApplicationContext>() {
			@Override
			public void initialize(GenericApplicationContext applicationContext) {
				applicationContext.registerBean(Application.class, () -> JavaFXApplication.this);
				applicationContext.registerBean(Parameters.class, () -> getParameters());
			}
		};

		this.context = new SpringApplicationBuilder()
				.sources(SpringBootEntryPoint.class)
				.initializers(initializer)
				.run(getParameters().getRaw().toArray(new String[0]));
	}

	@Override
	public void start(Stage stage) throws Exception {
		context.publishEvent(new StageReadyEvent(stage));
	}

	@Override
	public void stop() throws Exception {
		context.close();
		Platform.exit();
	}
}

