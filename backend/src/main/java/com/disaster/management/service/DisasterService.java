package com.disaster.management.service;

import com.disaster.management.model.Report;
import com.disaster.management.model.Resource;
import com.disaster.management.model.SearchResponse;
import com.disaster.management.model.StatsResponse;
import com.disaster.management.model.Volunteer;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class DisasterService {

    private final JdbcTemplate jdbcTemplate;

    public DisasterService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private String now() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void createReport(Report report) {
        jdbcTemplate.update("""
            INSERT INTO reports (
                disaster_type, place, description, severity,
                latitude, longitude, created_at
            ) VALUES (?, ?, ?, ?, ?, ?, ?)
        """,
                report.getDisaster_type(),
                report.getPlace(),
                report.getDescription(),
                report.getSeverity(),
                report.getLatitude(),
                report.getLongitude(),
                now()
        );
    }

    public void createVolunteer(Volunteer volunteer) {
        jdbcTemplate.update("""
            INSERT INTO volunteers (
                name, phone, skill, place, latitude, longitude, created_at
            ) VALUES (?, ?, ?, ?, ?, ?, ?)
        """,
                volunteer.getName(),
                volunteer.getPhone(),
                volunteer.getSkill(),
                volunteer.getPlace(),
                volunteer.getLatitude(),
                volunteer.getLongitude(),
                now()
        );
    }

    public void createResource(Resource resource) {
        jdbcTemplate.update("""
            INSERT INTO resources (
                resource_name, quantity, contact_person, contact_phone,
                place, latitude, longitude, created_at
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """,
                resource.getResource_name(),
                resource.getQuantity(),
                resource.getContact_person(),
                resource.getContact_phone(),
                resource.getPlace(),
                resource.getLatitude(),
                resource.getLongitude(),
                now()
        );
    }

    public SearchResponse search(String place, String disasterType) {
        String p = place == null ? "" : place.trim();
        String d = disasterType == null ? "" : disasterType.trim();

        List<Report> reports;
        if (!p.isEmpty() && !d.isEmpty()) {
            reports = jdbcTemplate.query("""
                SELECT * FROM reports
                WHERE lower(place) LIKE lower(?)
                AND lower(disaster_type) LIKE lower(?)
                ORDER BY id DESC
            """, new BeanPropertyRowMapper<>(Report.class), "%" + p + "%", "%" + d + "%");
        } else if (!p.isEmpty()) {
            reports = jdbcTemplate.query("""
                SELECT * FROM reports
                WHERE lower(place) LIKE lower(?)
                ORDER BY id DESC
            """, new BeanPropertyRowMapper<>(Report.class), "%" + p + "%");
        } else if (!d.isEmpty()) {
            reports = jdbcTemplate.query("""
                SELECT * FROM reports
                WHERE lower(disaster_type) LIKE lower(?)
                ORDER BY id DESC
            """, new BeanPropertyRowMapper<>(Report.class), "%" + d + "%");
        } else {
            reports = jdbcTemplate.query("""
                SELECT * FROM reports
                ORDER BY id DESC
            """, new BeanPropertyRowMapper<>(Report.class));
        }

        List<Volunteer> volunteers;
        List<Resource> resources;

        if (!p.isEmpty()) {
            volunteers = jdbcTemplate.query("""
                SELECT * FROM volunteers
                WHERE lower(place) LIKE lower(?)
                ORDER BY id DESC
            """, new BeanPropertyRowMapper<>(Volunteer.class), "%" + p + "%");

            resources = jdbcTemplate.query("""
                SELECT * FROM resources
                WHERE lower(place) LIKE lower(?)
                ORDER BY id DESC
            """, new BeanPropertyRowMapper<>(Resource.class), "%" + p + "%");
        } else {
            volunteers = jdbcTemplate.query("""
                SELECT * FROM volunteers
                ORDER BY id DESC
            """, new BeanPropertyRowMapper<>(Volunteer.class));

            resources = jdbcTemplate.query("""
                SELECT * FROM resources
                ORDER BY id DESC
            """, new BeanPropertyRowMapper<>(Resource.class));
        }

        return new SearchResponse(reports, volunteers, resources);
    }

    public StatsResponse getStats() {
        Integer reports = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM reports", Integer.class);
        Integer volunteers = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM volunteers", Integer.class);
        Integer resources = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM resources", Integer.class);

        return new StatsResponse(
                reports == null ? 0 : reports,
                volunteers == null ? 0 : volunteers,
                resources == null ? 0 : resources
        );
    }
}