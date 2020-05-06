package demo.kafka.producer;

import lombok.Data;

@Data
public class Trace {
    private String vehicleId; //车牌号
    private String deviceId;//终端ID
    private Integer vehicleColor; //车牌颜色
    private String sendTime; // 发送时间
    private Double longitude; // 经度
    private Double latitude; // 纬度
    private Integer city;//车辆归属地
    private Integer accessCode;//车辆归属省行政区域编码
    private Integer curAccesscode;//车辆当前归属县区行政区域编码
    private Integer trans;//车辆运输行业编码
    private String updateTime;//更新时间
    private Integer vec1;//终端速度：km/h
    private Integer vec2;//行驶记录速度，车辆行驶记录设备上传的行车速度信息 km/h
    private Integer vec3;//总里程，车辆上传的行车里程数，单位千米 km
    private Integer direction;//正北方向 （0-359）度 顺时针
    private Integer altitude;//海拔高度 单位米
    private Integer state;//车辆状态，
    private Integer alarm;//报警状态
    //平台唯一编码
    private String platformId;
    //车载终端厂商唯一编码
    private String producerId;
    //车载终端型号
    private String terminalModelType;
    //车载终端SIM卡电话号码
    private String terminalSimCode;
    //数据来源 -"LWLK"
    private String source;

}