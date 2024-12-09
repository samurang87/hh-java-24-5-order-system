import de.neuefische.shopservice.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OrderListRepoTest {

    @Test
    void addOrder_addsOrderToList() {

        ArrayList<Product> products1 = new ArrayList<Product>(List.of(new Product(1, "Toothpaste", BigDecimal.valueOf(1.99)), new Product(2, "Floss", BigDecimal.valueOf(1.29))));
        ArrayList<Product> products2 = new ArrayList<Product>(List.of(new Product(3, "Laundry Detergent", BigDecimal.valueOf(2.99)), new Product(4, "Deo", BigDecimal.valueOf(3.99))));
        OrderListRepo olr = new OrderListRepo();
        olr.add(new Order(UUID.randomUUID(), products1));
        olr.add(new Order(UUID.randomUUID(), products2));
        Assertions.assertEquals(2, olr.getAll().size());
    }

    @Test
    void removeOrder_removesOrderFromList() {
        ArrayList<Product> products1 = new ArrayList<Product>(List.of(new Product(1, "Toothpaste", BigDecimal.valueOf(1.99)), new Product(2, "Floss", BigDecimal.valueOf(1.29))));
        ArrayList<Product> products2 = new ArrayList<Product>(List.of(new Product(3, "Laundry Detergent", BigDecimal.valueOf(2.99)), new Product(4, "Deo", BigDecimal.valueOf(3.99))));
        OrderListRepo olr = new OrderListRepo();
        Order order1 = new Order(UUID.randomUUID(), products1);
        olr.add(order1);
        olr.add(new Order(UUID.randomUUID(), products2));
        olr.remove(order1);
        Assertions.assertEquals(1, olr.getAll().size());
    }

    @Test
    void getSingle_Order_byId() {
        ArrayList<Product> products1 = new ArrayList<Product>(List.of(new Product(1, "Toothpaste", BigDecimal.valueOf(1.99)), new Product(2, "Floss", BigDecimal.valueOf(1.29))));
        ArrayList<Product> products2 = new ArrayList<Product>(List.of(new Product(3, "Laundry Detergent", BigDecimal.valueOf(2.99)), new Product(4, "Deo", BigDecimal.valueOf(3.99))));
        OrderListRepo olr = new OrderListRepo();
        UUID id1 = UUID.randomUUID();
        Order order1 = new Order(id1, products1);
        olr.add(order1);
        olr.add(new Order(UUID.randomUUID(), products2));
        Assertions.assertEquals(olr.getSingle(id1), order1);
    }

    @Test
    void getsOrdersFilteredByStatus() {
        ArrayList<Product> products1 = new ArrayList<Product>(List.of(new Product(1, "Toothpaste", BigDecimal.valueOf(1.99)), new Product(2, "Floss", BigDecimal.valueOf(1.29))));
        ArrayList<Product> products2 = new ArrayList<Product>(List.of(new Product(3, "Laundry Detergent", BigDecimal.valueOf(2.99)), new Product(4, "Deo", BigDecimal.valueOf(3.99))));
        OrderMapRepo olr = new OrderMapRepo();
        Order order1 = new Order(UUID.randomUUID(), products1);
        olr.add(order1);
        Order order2 = new Order(UUID.randomUUID(), products2).withStatus(OrderStatus.COMPLETED);
        olr.add(order2);
        Order order3 = new Order(UUID.randomUUID(), products1);
        olr.add(order3);

        List<Order> res = olr.getAllByStatus(OrderStatus.COMPLETED);
        List<Order> exp = new ArrayList<Order>(List.of(order2));
        Assertions.assertEquals(exp, res);
    }

    @Test
    void getOldestOrderPerStatus_returnsOldestOrderForEachStatus() {
        ArrayList<Product> products1 = new ArrayList<Product>(List.of(new Product(1, "Toothpaste", BigDecimal.valueOf(1.99)), new Product(2, "Floss", BigDecimal.valueOf(1.29))));
        ArrayList<Product> products2 = new ArrayList<Product>(List.of(new Product(3, "Laundry Detergent", BigDecimal.valueOf(2.99)), new Product(4, "Deo", BigDecimal.valueOf(3.99))));
        ArrayList<Product> products3 = new ArrayList<Product>(List.of(new Product(5, "Shampoo", BigDecimal.valueOf(4.99)), new Product(6, "Soap", BigDecimal.valueOf(1.49))));

        OrderMapRepo olr = new OrderMapRepo();
        Order order1 = new Order(UUID.randomUUID(), products1).withStatus(OrderStatus.PROCESSING).withTimestamp(Instant.parse("2023-01-01T10:00:00Z"));
        Order order2 = new Order(UUID.randomUUID(), products2).withStatus(OrderStatus.PROCESSING).withTimestamp(Instant.parse("2023-01-02T10:00:00Z"));
        Order order3 = new Order(UUID.randomUUID(), products3).withStatus(OrderStatus.COMPLETED).withTimestamp(Instant.parse("2023-01-01T10:00:00Z"));

        olr.add(order1);
        olr.add(order2);
        olr.add(order3);

        Map<OrderStatus, Order> result = olr.getOldestOrderPerStatus();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(order1, result.get(OrderStatus.PROCESSING));
        Assertions.assertEquals(order3, result.get(OrderStatus.COMPLETED));
    }
}
