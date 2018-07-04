package pl.arimr.mongodbdemo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import pl.arimr.mongodbdemo.domain.Customer;

import java.util.List;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
    List<Customer> findByAddress_Country(String country);

    @Query("{'address.country':  ?0 }")
    List<Customer> findByAddress_CountryUsingQuery(String country);

    List<Customer> findByAgeBetween(Long minAge, Long maxAge);

    @Query("{'age':  { '$gt': ?0 , '$lt': ?1 } }")
    List<Customer> findByAgeBetweenUsingQuery(Long minAge, Long maxAge);

}
