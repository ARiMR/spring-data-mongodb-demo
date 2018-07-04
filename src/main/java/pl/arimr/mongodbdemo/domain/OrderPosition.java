package pl.arimr.mongodbdemo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

@NoArgsConstructor
@Data
public class OrderPosition {
    @DBRef(lazy = false)
    private Product product;
    private Long quantity;

    public OrderPosition(Product product, Long quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}
