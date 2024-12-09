import de.neuefische.shopservice.Order;
import de.neuefische.shopservice.OrderMapRepo;
import de.neuefische.shopservice.OrderStatus;
import de.neuefische.shopservice.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderMapRepoTest {

    @Test
    void addOrder_addsOrderToMap() {

        ArrayList<Product> products1 = new ArrayList<Product>(List.of(new Product(1, "Toothpaste", BigDecimal.valueOf(1.99)), new Product(2, "Floss", BigDecimal.valueOf(1.29))));
        ArrayList<Product> products2 = new ArrayList<Product>(List.of(new Product(3, "Laundry Detergent", BigDecimal.valueOf(2.99)), new Product(4, "Deo", BigDecimal.valueOf(3.99))));
        OrderMapRepo olr = new OrderMapRepo();
        olr.add(new Order(UUID.randomUUID(), products1));
        olr.add(new Order(UUID.randomUUID(), products2));
        Assertions.assertEquals(2, olr.getAll().size());
    }

    @Test
    void removeOrder_removesOrderFromMap() {
        ArrayList<Product> products1 = new ArrayList<Product>(List.of(new Product(1, "Toothpaste", BigDecimal.valueOf(1.99)), new Product(2, "Floss", BigDecimal.valueOf(1.29))));
        ArrayList<Product> products2 = new ArrayList<Product>(List.of(new Product(3, "Laundry Detergent", BigDecimal.valueOf(2.99)), new Product(4, "Deo", BigDecimal.valueOf(3.99))));
        OrderMapRepo olr = new OrderMapRepo();
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
        OrderMapRepo olr = new OrderMapRepo();
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
        olr.add(new Order(UUID.randomUUID(), products2).withStatus(OrderStatus.COMPLETED));
        Order order3 = new Order(UUID.randomUUID(), products1);
        olr.add(order3);

        List<Order> res = olr.getAllByStatus(OrderStatus.PROCESSING);
        List<Order> exp = new ArrayList<Order>(List.of(order1, order3));
        Assertions.assertEquals(exp, res);
    }
}
