package com.german.levelresults;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Random random = new Random();
        for(int i=1;i<100;i++) {
            CopyOnWriteArraySet<Level> levels = new CopyOnWriteArraySet<>();
            for(int j=1;j<100;j++) {
                levels.add(new Level(j,new AtomicInteger(random.nextInt(1000))));
            }
            InfoService.levelsByUser.put(i, levels);
        }
    }
}
