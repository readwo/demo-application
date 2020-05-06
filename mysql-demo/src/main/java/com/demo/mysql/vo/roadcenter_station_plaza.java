package com.demo.mysql.vo;

import lombok.Data;

@Data
public class roadcenter_station_plaza {

    private int id;

    private String plazaId;

    private String plazaName;

    private String plazaType;

    private String provinceId;

    private String stationId;

    private double verifyWgsLat;

    private double verifyWgsLng;

}
