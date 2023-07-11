package com.hhc.domain;

import lombok.Data;
import java.util.Date;

@Data
public class DiningCar {

    private Integer locationid;
    private String applicant;
    private String facilityType;
    private Integer cnn;
    private String locationDescription;
    private String address;
    private String blocklot;
    private String block;
    private String lot;
    private String permit;
    private String status;
    private String foodItems;
    private Integer x;
    private Integer y;
    private Double latitude;
    private Double longitude;
    private String schedule;
    private String dayshours;
    private String nOISent;
    private String approved;
    private String received;
    private Integer priorPermit;
    private String expirationDate;
    private String location;
    private Integer firePreventionDistricts;
    private Integer policeDistricts;
    private Integer supervisorDistricts;
    private Integer zipCodes;
    private Integer neighborhoods;
}
