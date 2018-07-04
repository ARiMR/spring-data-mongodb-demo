package pl.arimr.mongodbdemo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Document(collection = "order")
public class Order {
    @Id
    private String id;
    private List<OrderPosition> positions = new ArrayList<>();
}
