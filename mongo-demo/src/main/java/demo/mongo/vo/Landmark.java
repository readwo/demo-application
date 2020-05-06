package demo.mongo.vo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "landmarks")
@Data
public class Landmark {
    @Id
    private String id;
    private String name;
    private Integer type;

    @Field("id")
    private String markNo;

    private String provinceId;

    private Geometry geometry;

    @Data
    public class Geometry{
        private List coordinates;
        private String type;
    }
}