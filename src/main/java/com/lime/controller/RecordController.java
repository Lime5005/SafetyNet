package com.lime.controller;

import com.lime.controller.dto.ChildAlertDto;
import com.lime.controller.dto.EmptyJsonResponse;
import com.lime.controller.dto.PersonInfoDto;
import com.lime.domain.Record;
import com.lime.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecordController {

    private final RecordService recordService;

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
            return new ResponseEntity<>(withRecord, HttpStatus.OK);
        } else return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
    }
    
    @RequestMapping(path = "/childAlert", method = RequestMethod.GET)
    public ResponseEntity<Object> getChildrenAlert(@RequestParam String address) {
        ChildAlertDto child = recordService.findChildByAddress(address);
        if (child != null) {
            return new ResponseEntity<>(child, HttpStatus.OK);
        } else return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/medicalRecords")
    public ResponseEntity<Boolean> createRecord(@RequestBody Record record) {
        Boolean bool = recordService.addRecord(record);
        if (bool) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    @PutMapping( "/medicalRecords")
    public ResponseEntity<Boolean> updateRecord(@RequestBody Record record) {
        Boolean bool = recordService.updateRecord(record);
        if (bool) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping( "/medicalRecords")
    public ResponseEntity<Boolean> deleteRecord(@RequestBody Record record) {
        Boolean bool = recordService.deleteRecord(record);
        if (bool) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }
}
