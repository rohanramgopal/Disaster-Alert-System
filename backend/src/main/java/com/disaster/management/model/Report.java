package com.disaster.management.model;

public class Report {
    private Integer id;
    private String disaster_type;
    private String place;
    private String description;
    private String severity;
    private Double latitude;
    private Double longitude;
    private String created_at;

    public Report() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getDisaster_type() { return disaster_type; }
    public void setDisaster_type(String disaster_type) { this.disaster_type = disaster_type; }

    public String getPlace() { return place; }
    public void setPlace(String place) { this.place = place; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public String getCreated_at() { return created_at; }
    public void setCreated_at(String created_at) { this.created_at = created_at; }
}