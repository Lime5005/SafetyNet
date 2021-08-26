package com.lime.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

@Controller
public class RecordController {
    private static final ObjectMapper mapper = new ObjectMapper();

    @ResponseBody
    @RequestMapping("/medicalRecord")
    public static Object getRecords() {
        try {
            var allInfo = mapper.readValue(new URL("https://s3-eu-west-1.amazonaws.com/course.oc-static.com/projects/DA+Java+EN/P5+/data.json"), Map.class);

            Object records = allInfo.get("medicalrecords");

            return records;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
