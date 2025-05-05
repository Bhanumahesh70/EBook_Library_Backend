package com.ebook.configuration;

import com.ebook.service.FineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class UpdateFineOnStartUp {

    private static final Logger logger = Logger.getLogger(UpdateFineOnStartUp.class.getName());
    private final FineService fineService;

    @Autowired
    public UpdateFineOnStartUp(FineService fineService) {
        this.fineService = fineService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady(){
        logger.info("Updating fines on application startUp");
        fineService.updateFines();
    }
}
