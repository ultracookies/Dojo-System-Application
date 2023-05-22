package com.dojoapp.Dojo.System.Application.model;

import javafx.stage.Stage;
import org.springframework.context.ApplicationEvent;

class StageReadyEvent extends ApplicationEvent {

    public Stage getStage() {
        return Stage.class.cast(getSource());
    }

    public StageReadyEvent(Object source) {
        super(source);
    }
}
