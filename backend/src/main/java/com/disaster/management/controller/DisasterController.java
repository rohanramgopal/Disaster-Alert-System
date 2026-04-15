package com.disaster.management.controller;

import com.disaster.management.model.Report;
import com.disaster.management.model.Resource;
import com.disaster.management.model.SearchResponse;
import com.disaster.management.model.StatsResponse;
import com.disaster.management.model.Volunteer;
import com.disaster.management.service.DisasterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(
        origins = {"http://localhost:3000", "http://127.0.0.1:3000"},
        allowedHeaders = "*",
        methods = {
                RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.OPTIONS
        }
)
public class DisasterController {

    private final DisasterService disasterService;

    public DisasterController(DisasterService disasterService) {
        this.disasterService = disasterService;
    }

    private ResponseEntity<Map<String, String>> error(String field) {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("error", field + " is required");
        return ResponseEntity.badRequest().body(response);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    @PostMapping("/report")
    public ResponseEntity<?> createReport(@RequestBody Report report) {
        if (isBlank(report.getDisaster_type())) return error("disaster_type");
        if (isBlank(report.getPlace())) return error("place");
        if (isBlank(report.getDescription())) return error("description");
        if (isBlank(report.getSeverity())) return error("severity");
        if (report.getLatitude() == null) return error("latitude");
        if (report.getLongitude() == null) return error("longitude");

        disasterService.createReport(report);

        Map<String, String> response = new LinkedHashMap<>();
        response.put("message", "Disaster reported successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/volunteer")
    public ResponseEntity<?> createVolunteer(@RequestBody Volunteer volunteer) {
        if (isBlank(volunteer.getName())) return error("name");
        if (isBlank(volunteer.getPhone())) return error("phone");
        if (isBlank(volunteer.getSkill())) return error("skill");
        if (isBlank(volunteer.getPlace())) return error("place");
        if (volunteer.getLatitude() == null) return error("latitude");
        if (volunteer.getLongitude() == null) return error("longitude");

        disasterService.createVolunteer(volunteer);

        Map<String, String> response = new LinkedHashMap<>();
        response.put("message", "Volunteer registered successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/resource")
    public ResponseEntity<?> createResource(@RequestBody Resource resource) {
        if (isBlank(resource.getResource_name())) return error("resource_name");
        if (resource.getQuantity() == null) return error("quantity");
        if (isBlank(resource.getContact_person())) return error("contact_person");
        if (isBlank(resource.getContact_phone())) return error("contact_phone");
        if (isBlank(resource.getPlace())) return error("place");
        if (resource.getLatitude() == null) return error("latitude");
        if (resource.getLongitude() == null) return error("longitude");

        disasterService.createResource(resource);

        Map<String, String> response = new LinkedHashMap<>();
        response.put("message", "Resource added successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<SearchResponse> search(
            @RequestParam(defaultValue = "") String place,
            @RequestParam(name = "disaster_type", defaultValue = "") String disasterType
    ) {
        return ResponseEntity.ok(disasterService.search(place, disasterType));
    }

    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> stats() {
        return ResponseEntity.ok(disasterService.getStats());
    }

    @RequestMapping(method = RequestMethod.OPTIONS, value = {"/report", "/volunteer", "/resource", "/search", "/stats"})
    public ResponseEntity<?> handleOptions() {
        return ResponseEntity.ok().build();
    }
}