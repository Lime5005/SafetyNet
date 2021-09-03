package com.lime.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lime.domain.Database;
import com.lime.domain.Record;
import com.lime.service.DtoService;
import com.lime.service.SafetyNetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RecordController {

    SafetyNetService safetyNetService;

    @Autowired
    public RecordController(SafetyNetService safetyNetService) {
        this.safetyNetService = safetyNetService;

    }

    @ResponseBody
    @RequestMapping("/medicalRecords")
    public List<Record> getRecords() {
        List<Record> medicalrecords = safetyNetService.getAllRecords();
        return medicalrecords;
    }
}
