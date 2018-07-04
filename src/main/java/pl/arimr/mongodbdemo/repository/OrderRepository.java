package pl.arimr.mongodbdemo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.arimr.mongodbdemo.domain.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

}
