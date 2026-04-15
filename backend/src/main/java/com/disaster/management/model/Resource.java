package com.disaster.management.model;

public class Resource {
    private Integer id;
    private String resource_name;
    private Integer quantity;
    private String contact_person;
    private String contact_phone;
    private String place;
    private Double latitude;
    private Double longitude;
    private String created_at;

    public Resource() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getResource_name() { return resource_name; }
    public void setResource_name(String resource_name) { this.resource_name = resource_name; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getContact_person() { return contact_person; }
    public void setContact_person(String contact_person) { this.contact_person = contact_person; }

    public String getContact_phone() { return contact_phone; }
    public void setContact_phone(String contact_phone) { this.contact_phone = contact_phone; }

    public String getPlace() { return place; }
    public void setPlace(String place) { this.place = place; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public String getCreated_at() { return created_at; }
    public void setCreated_at(String created_at) { this.created_at = created_at; }
}