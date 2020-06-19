package demo.es.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 2.5.1	行驶轨迹线历史查询返回实体
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryTravelResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    //总页数
    private Integer totalPage = 1;

    //总记录数
    private Long totalCount = 0L;

    //当前页码
    private Integer pageNo = 1;

    //当前页条数
    private Integer pageSize = 10;

    //行程集合
    private List<VehTrack> vehTrack;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VehTrack{

        private String endLat;

        private String vehPlate;

        private String vehPlateColor;


        private String startTime;

        private String endTime;

        private String dataQualityLevel;



    }
}
