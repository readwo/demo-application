package demo.es.vo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
//@Document(indexName = "trip_info_202005",type="_doc")
public class EsDataStrip implements Serializable {


    /**
     * 系统自增ID
     * 为了防止重复被替换掉
     */
    @Id
    private String uuid;
    private String travelId;
    private String vehicleNo;
    private String vehicleColor;
    private int gantryType;
    private String pointName;
    private String gantryId;
    private int confidenceLevel;
    private String gpstime;
    private int direction;
    private int speed;
    private Double range;

    private String tansTime;
    private long portraitTime;
    private long fittingTime;
    private long curareacode;




}