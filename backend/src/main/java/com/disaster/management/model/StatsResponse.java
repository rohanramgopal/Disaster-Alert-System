package com.disaster.management.model;

public class StatsResponse {
    private int reports;
    private int volunteers;
    private int resources;

    public StatsResponse() {}

    public StatsResponse(int reports, int volunteers, int resources) {
        this.reports = reports;
        this.volunteers = volunteers;
        this.resources = resources;
    }

    public int getReports() { return reports; }
    public void setReports(int reports) { this.reports = reports; }

    public int getVolunteers() { return volunteers; }
    public void setVolunteers(int volunteers) { this.volunteers = volunteers; }

    public int getResources() { return resources; }
    public void setResources(int resources) { this.resources = resources; }
}