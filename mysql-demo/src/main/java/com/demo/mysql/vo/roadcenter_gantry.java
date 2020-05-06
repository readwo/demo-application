package com.demo.mysql.vo;


import lombok.Data;

@Data
public class roadcenter_gantry {

    private int id;

    private String gantryId;

    private String name;

    private String type;


    private double verifyWgsLat;

    private double verifyWgsLng;


    private String pileNumber;


    private String information2;


    private String provinceId;
}
