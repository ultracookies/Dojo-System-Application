package com.dojoapp.Dojo.System.Application.model;

import com.dojoapp.Dojo.System.Application.model.StageReadyEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StageListener implements ApplicationListener<StageReadyEvent> {

    private final String applicationTitle;

    StageListener(@Value("#{${stageTitlesMap}.home}") String applicationTitle) {
        this.applicationTitle = applicationTitle;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        var stage = event.getStage();
    }
}
