package pl.arimr.mongodbdemo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Data
@Document(collection = "customer")
public class Customer {
    @Id
    private String id;
    @Indexed(direction = IndexDirection.ASCENDING)
    private String name;
    @Indexed(direction = IndexDirection.ASCENDING)
    private String surname;
    private Address address;
    private Long age;

    public Customer(String name, String surname, Long age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.address = new Address();
    }
}
