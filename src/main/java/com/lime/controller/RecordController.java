package com.lime.controller;

import com.lime.controller.dto.ChildAlertDto;
import com.lime.controller.dto.EmptyJsonResponse;
import com.lime.controller.dto.PersonInfoDto;
import com.lime.domain.Record;
import com.lime.service.RecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecordController {

    private final RecordService recordService;
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping("/medicalRecords")
    public List<Record> getRecords() {
        return recordService.getAllRecords();
    }

    @RequestMapping(path="/personInfo", method = RequestMethod.GET)
    public ResponseEntity<Object> getPersonByName(@RequestParam String firstName, @RequestParam String lastName ) {
        PersonInfoDto withRecord = recordService.getAPersonWithRecord(firstName, lastName);
        if (withRecord != null) {
            logger.info("Query to get person with record by firstName: " + firstName + ", lastName: " + lastName);
            return new ResponseEntity<>(withRecord, HttpStatus.OK);
        } else {
            logger.error("Failed to get person with firstName: " + firstName + ", lastName: " + lastName);
            return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
        }
    }
    
    @RequestMapping(path = "/childAlert", method = RequestMethod.GET)
    public ResponseEntity<Object> getChildrenAlert(@RequestParam String address) {
        ChildAlertDto child = recordService.findChildByAddress(address);
        if (child != null) {
            logger.info("Query to get children alert by address: " + address);
            return new ResponseEntity<>(child, HttpStatus.OK);
        } else {
            logger.error("Failed to get children alert by address: " + address);
            return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/medicalRecords")
    public ResponseEntity<Boolean> createRecord(@RequestBody Record record) {
        Boolean bool = recordService.addRecord(record);
        if (bool) {
            logger.info("Record " + record + " is created!");
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        } else {
            logger.error("Failed to create record: " + record);
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping( "/medicalRecords")
    public ResponseEntity<Boolean> updateRecord(@RequestBody Record record) {
        Boolean bool = recordService.updateRecord(record);
        if (bool) {
            logger.info("Record " + record + " is updated!");
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        } else {
            logger.error("Failed to update record: " + record);
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping( "/medicalRecords")
    public ResponseEntity<Boolean> deleteRecord(@RequestBody Record record) {
        Boolean bool = recordService.deleteRecord(record);
        if (bool) {
            logger.info("Record " + record + " is deleted!");
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        } else {
            logger.error("Failed to delete record: " + record);
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
}
