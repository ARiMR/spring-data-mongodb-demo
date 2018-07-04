package pl.arimr.mongodbdemo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import pl.arimr.mongodbdemo.domain.Product;
import pl.arimr.mongodbdemo.domain.enums.Color;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    Product findByName(String name);

    Product findBySerialNumber(String serialNumber);

    Product findByNameAndSerialNumber(String name, String serialNumber);

    @Query("{'name':  ?0 }")
    Product findByNameUsingQuery(String name);

    @Query("{'serialNumber':  ?0 }")
    Product findBySerialNumberUsingQuery(String serialNumber);

    @Query("{'name':  ?0 , 'serialNumber':  ?1 }")
    Product findByNameAndSerialNumberUsingQuery(String name, String serialNumber);

    List<Product> findByColorsContaining(Color color);

    @Query("{'colors':  { '$in': [?0] } }")
    List<Product> findByColorsContainingUsingQuery(Color color);
}
