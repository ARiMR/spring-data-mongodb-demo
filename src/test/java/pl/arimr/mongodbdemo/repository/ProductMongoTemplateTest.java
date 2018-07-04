package pl.arimr.mongodbdemo.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;
import pl.arimr.mongodbdemo.domain.Product;
import pl.arimr.mongodbdemo.domain.enums.Color;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductMongoTemplateTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void productMongoTemplateTest() {
        mongoTemplate.remove(new Query(), Product.class);

        Product p1 = new Product("p1", "SN/1", BigDecimal.valueOf(1000));
        p1.getColors().add(Color.RED);
        p1.getColors().add(Color.BLACK);

        Product p2 = new Product("p2", "SN/2", BigDecimal.valueOf(2000));
        p2.getColors().add(Color.GREEN);
        p2.getColors().add(Color.BLACK);

        Product p3 = new Product("p3", "SN/3", BigDecimal.valueOf(3000));
        p3.getColors().add(Color.GREEN);
        p3.getColors().add(Color.BLACK);

        mongoTemplate.save(p1);
        mongoTemplate.save(p2);
        mongoTemplate.save(p3);

        Assert.assertEquals(3L, mongoTemplate.findAll(Product.class).size());

        Assert.assertNotNull(p1.getId());
        Assert.assertNotNull(p2.getId());
        Assert.assertNotNull(p3.getId());

        Product byName = mongoTemplate.findOne(
                new Query().addCriteria(Criteria.where("name").is("p1")),
                Product.class
        );
        Assert.assertEquals(p1.getId(), byName.getId());


        Product bySerialNumber = mongoTemplate.findOne(
                new Query().addCriteria(Criteria.where("serialNumber").is("SN/1")),
                Product.class
        );
        Assert.assertEquals(p1.getId(), bySerialNumber.getId());

        Product byNameAndSerialNumber = mongoTemplate.findOne(
                new Query().addCriteria(Criteria
                        .where("serialNumber").is("SN/1")
                        .and("name").is("p1")),
                Product.class
        );
        Assert.assertEquals(p1.getId(), byNameAndSerialNumber.getId());


        List<Product> byColorBlack = mongoTemplate.find(
                new Query().addCriteria(Criteria.where("colors").in(Color.BLACK)),
                Product.class
        );
        Assert.assertEquals(3L, byColorBlack.size());


        List<Product> byColorRed = mongoTemplate.find(
                new Query().addCriteria(Criteria.where("colors").in(Color.RED)),
                Product.class
        );
        Assert.assertEquals(1L, byColorRed.size());

        p2.getColors().add(Color.RED);
        mongoTemplate.save(p2);

        byColorRed = mongoTemplate.find(
                new Query().addCriteria(Criteria.where("colors").in(Color.RED)),
                Product.class
        );
        Assert.assertEquals(2L, byColorRed.size());

        mongoTemplate.remove(p3);

        Assert.assertEquals(2L, mongoTemplate.findAll(Product.class).size());

    }

}
