package pl.arimr.mongodbdemo.repository;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import pl.arimr.mongodbdemo.domain.Customer;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void customerRepositoryTest() {
        customerRepository.deleteAll();

        Customer c1 = new Customer("John", "Doe", 42L);
        c1.getAddress().setCountry("USA");
        Customer c2 = new Customer("Jack", "Smith", 24L);
        c2.getAddress().setCountry("Canada");
        Customer c3 = new Customer("Adam", "Watson", 34L);
        c3.getAddress().setCountry("USA");


        c1 = customerRepository.save(c1);
        c2 = customerRepository.save(c2);
        c3 = customerRepository.save(c3);

        Assert.assertEquals(3L, customerRepository.findAll().size());

        Assert.assertEquals(2L, customerRepository.findByAddress_Country("USA").size());
        Assert.assertEquals(2L, customerRepository.findByAddress_CountryUsingQuery("USA").size());

        Assert.assertEquals(1L, customerRepository.findByAddress_Country("Canada").size());
        Assert.assertEquals(1L, customerRepository.findByAddress_CountryUsingQuery("Canada").size());

        Assert.assertEquals(2L, customerRepository.findByAgeBetween(23L, 35L).size());
        Assert.assertEquals(2L, customerRepository.findByAgeBetweenUsingQuery(23L, 35L).size());

        Assert.assertEquals(0L, customerRepository.findByAgeBetween(24L, 34L).size());
        Assert.assertEquals(0L, customerRepository.findByAgeBetweenUsingQuery(24L, 34L).size());


        // get avg age for customer in each country
        List<CustomerCountryAvgAgeResult> results = mongoTemplate.aggregate(
                newAggregation(
                        group("address.country").avg("age").as("avgAge"),
                        sort(Sort.Direction.ASC, "_id")
                ),
                Customer.class,
                CustomerCountryAvgAgeResult.class)
                .getMappedResults();

        Assert.assertEquals(2L, results.size());

        Assert.assertEquals("Canada", results.get(0).get_id());
        Assert.assertTrue(BigDecimal.valueOf(24.0).compareTo(results.get(0).getAvgAge()) == 0);

        Assert.assertEquals("USA", results.get(1).get_id());
        Assert.assertTrue(BigDecimal.valueOf(38.0).compareTo(results.get(1).getAvgAge()) == 0);
    }
}

@Data
@NoArgsConstructor
class CustomerCountryAvgAgeResult {
    private String _id;
    private BigDecimal avgAge;
}