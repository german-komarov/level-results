package com.german.levelresults;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.util.Collections.singletonMap;

@RestController
public class InfoController {

    private final static Logger LOGGER = LoggerFactory.getLogger(InfoController.class);
    private final static String SERVER_ERROR_MESSAGE = "Oops, something wrong happened in the server";

    private final InfoService infoService;

    @Autowired
    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }


    @GetMapping("/userinfo/{user_id}")
    public Object getTop20ResultsByUser(@PathVariable(name = "user_id") int userId) {
        try {
            return this.infoService.readTop20Results(userId);
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(singletonMap("message", SERVER_ERROR_MESSAGE));
        }
    }


    @GetMapping("/levelinfo/{level_id}")
    public Object getTop20UsersByLevel(@PathVariable(name = "level_id") int levelId) {
        try {
            return this.infoService.readByTop20Users(levelId);
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(singletonMap("message", SERVER_ERROR_MESSAGE));
        }
    }

    @PutMapping("/setinfo")
    public Object setInfo(@RequestBody InfoDto infoDto) {
        try {
            return this.infoService.setInfo(infoDto);
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(singletonMap("message", SERVER_ERROR_MESSAGE));
        }
    }

}
