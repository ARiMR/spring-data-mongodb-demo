package pl.arimr.mongodbdemo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.arimr.mongodbdemo.domain.enums.Color;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Data
@Document(collection = "products")
public class Product {
    @Id
    private String id;
    @Indexed(unique = true, direction = IndexDirection.ASCENDING)
    private String name;
    @Indexed(unique = true, direction = IndexDirection.ASCENDING)
    private String serialNumber;
    private Set<Color> colors = new HashSet<>();
    private LocalDateTime createdAt = LocalDateTime.now();
    private BigDecimal price;

    public Product(String name, String serialNumber) {
        this(name, serialNumber, BigDecimal.ZERO);
    }

    public Product(String name, String serialNumber, BigDecimal price) {
        this.name = name;
        this.serialNumber = serialNumber;
        this.price = price;
    }
}
