package com.lime.controller;

import com.lime.controller.dto.ChildAlertDto;
import com.lime.controller.dto.PersonInfoDto;
import com.lime.domain.Record;
import com.lime.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecordController {

    private final RecordService recordService;

    @Autowired
    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @ResponseBody
    @RequestMapping("/medicalRecords")
    public List<Record> getRecords() {
        List<Record> medicalrecords = recordService.getAllRecords();
        return medicalrecords;
    }

    @ResponseBody
    @RequestMapping(path="/personInfo", method = RequestMethod.GET)
    public PersonInfoDto getPersonByName(@RequestParam String firstName, @RequestParam String lastName ) {
        return recordService.getAPersonWithRecord(firstName, lastName);
    }

    @ResponseBody
    @RequestMapping(path = "/childAlert", method = RequestMethod.GET)
    public ChildAlertDto getChildrenAlert(@RequestParam String address) {
        if (recordService.findChildByAddress(address) != null) {
            return recordService.findChildByAddress(address);
        } else return new ChildAlertDto();
    }
}
