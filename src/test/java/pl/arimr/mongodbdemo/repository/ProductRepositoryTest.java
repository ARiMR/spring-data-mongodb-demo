package pl.arimr.mongodbdemo.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.arimr.mongodbdemo.domain.Product;
import pl.arimr.mongodbdemo.domain.enums.Color;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void productRepositoryTest() {
        productRepository.deleteAll();

        Product p1 = new Product("p1", "SN/1");
        p1.getColors().add(Color.RED);
        p1.getColors().add(Color.BLACK);

        Product p2 = new Product("p2", "SN/2");
        p2.getColors().add(Color.GREEN);
        p2.getColors().add(Color.BLACK);

        Product p3 = new Product("p3", "SN/3");
        p3.getColors().add(Color.GREEN);
        p3.getColors().add(Color.BLACK);

        p1 = productRepository.save(p1);
        p2 = productRepository.save(p2);
        p3 = productRepository.save(p3);

        Assert.assertEquals(3L, productRepository.findAll().size());

        Assert.assertNotNull(p1.getId());
        Assert.assertNotNull(p2.getId());
        Assert.assertNotNull(p3.getId());

        Assert.assertEquals(p1.getId(), productRepository.findByName("p1").getId());
        Assert.assertEquals(p1.getId(), productRepository.findByNameUsingQuery("p1").getId());

        Assert.assertEquals(p1.getId(), productRepository.findBySerialNumber("SN/1").getId());
        Assert.assertEquals(p1.getId(), productRepository.findBySerialNumberUsingQuery("SN/1").getId());

        Assert.assertEquals(p1.getId(), productRepository.findByNameAndSerialNumber("p1", "SN/1").getId());
        Assert.assertEquals(p1.getId(), productRepository.findByNameAndSerialNumberUsingQuery("p1", "SN/1").getId());

        Assert.assertEquals(3L, productRepository.findByColorsContaining(Color.BLACK).size());
        Assert.assertEquals(3L, productRepository.findByColorsContainingUsingQuery(Color.BLACK).size());

        Assert.assertEquals(1L, productRepository.findByColorsContaining(Color.RED).size());
        Assert.assertEquals(1L, productRepository.findByColorsContainingUsingQuery(Color.RED).size());

        p2.getColors().add(Color.RED);
        productRepository.save(p2);

        Assert.assertEquals(2L, productRepository.findByColorsContaining(Color.RED).size());
        Assert.assertEquals(2L, productRepository.findByColorsContainingUsingQuery(Color.RED).size());

        productRepository.delete(p3);

        Assert.assertEquals(2L, productRepository.findAll().size());

    }
}