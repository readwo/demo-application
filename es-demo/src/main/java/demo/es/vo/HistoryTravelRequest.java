package demo.es.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

/**
 * 2.5.1	行驶轨迹线历史查询请求实体
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryTravelRequest implements Serializable {

    private static final long serialVersionUID = 1L;


    private String provinceId;

    private String vehPlate;

    @NotNull(message = "识别点类型(dataType)不能为空")
    private String dataType;

    private String dataTypeKey;

    @NotNull(message = "查询方式(queryType)不能为空")
    private String queryType;

    @NotNull(message = "查询值1(queryValue1)不能为空")
    private String queryValue1;


    private String queryValue2;

    @Positive(message = "页码需为正数")
    private Integer pageNo = 1;

    @Positive(message = "页码需为正数")
    private Integer pageSize = 10;
}
