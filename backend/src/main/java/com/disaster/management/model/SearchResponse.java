package com.disaster.management.model;

import java.util.List;

public class SearchResponse {
    private List<Report> reports;
    private List<Volunteer> volunteers;
    private List<Resource> resources;

    public SearchResponse() {}

    public SearchResponse(List<Report> reports, List<Volunteer> volunteers, List<Resource> resources) {
        this.reports = reports;
        this.volunteers = volunteers;
        this.resources = resources;
    }

    public List<Report> getReports() { return reports; }
    public void setReports(List<Report> reports) { this.reports = reports; }

    public List<Volunteer> getVolunteers() { return volunteers; }
    public void setVolunteers(List<Volunteer> volunteers) { this.volunteers = volunteers; }

    public List<Resource> getResources() { return resources; }
    public void setResources(List<Resource> resources) { this.resources = resources; }
}