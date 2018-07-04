package pl.arimr.mongodbdemo.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.arimr.mongodbdemo.domain.Order;
import pl.arimr.mongodbdemo.domain.OrderPosition;
import pl.arimr.mongodbdemo.domain.Product;
import pl.arimr.mongodbdemo.domain.enums.Color;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void orderRepositoryTest() {
        orderRepository.deleteAll();

        String randomSuffix = UUID.randomUUID().toString();

        Product p1 = new Product("or_p1_" + randomSuffix, "SN/001/" + randomSuffix);
        p1.getColors().add(Color.RED);
        p1.getColors().add(Color.BLACK);

        Product p2 = new Product("or_p2_" + randomSuffix, "SN/002/" + randomSuffix);
        p2.getColors().add(Color.GREEN);
        p2.getColors().add(Color.BLACK);

        Product p3 = new Product("or_p3_" + randomSuffix, "SN/003/" + randomSuffix);
        p3.getColors().add(Color.GREEN);
        p3.getColors().add(Color.BLACK);

        p1 = productRepository.save(p1);
        p2 = productRepository.save(p2);
        p3 = productRepository.save(p3);

        Order order = new Order();
        order.getPositions().add(new OrderPosition(p1, 4L));
        order.getPositions().add(new OrderPosition(p2, 5L));
        order.getPositions().add(new OrderPosition(p3, 10L));

        order = orderRepository.save(order);

        Assert.assertEquals(3L, order.getPositions().size());

    }
}