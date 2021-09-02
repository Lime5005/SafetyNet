package com.lime.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lime.domain.Database;
import com.lime.domain.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RecordController {
    private static final ObjectMapper mapper = new ObjectMapper();
    Database database;

    @Autowired
    public RecordController(Database database) {
        this.database = database;
    }

    @ResponseBody
    @RequestMapping("/medicalRecord")
    public List<Record> getRecords() {
        List<Record> medicalrecords = database.getMedicalrecords();
        return medicalrecords;
    }
}
