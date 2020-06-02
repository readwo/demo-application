package demo.mongo.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
//@Document(collection = "carF")
public class Car {

    private String name;

    private String color;
}
