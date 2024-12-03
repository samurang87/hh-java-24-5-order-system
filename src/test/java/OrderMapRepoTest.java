import de.neuefische.shopservice.Order;
import de.neuefische.shopservice.OrderMapRepo;
import de.neuefische.shopservice.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderMapRepoTest {

    @Test
    void addOrder_addsOrderToMap() {

        ArrayList<Product> products1 = new ArrayList<Product>(List.of(new Product(1, "Toothpaste", BigDecimal.valueOf(1.99)), new Product(2, "Floss", BigDecimal.valueOf(1.29))));
        ArrayList<Product> products2 = new ArrayList<Product>(List.of(new Product(3, "Laundry Detergent", BigDecimal.valueOf(2.99)), new Product(4, "Deo", BigDecimal.valueOf(3.99))));
        OrderMapRepo olr = new OrderMapRepo();
        olr.add(new Order(1, products1));
        olr.add(new Order(2, products2));
        Assertions.assertEquals(2, olr.getAll().size());
    }

    @Test
    void removeOrder_removesOrderFromMap() {
        ArrayList<Product> products1 = new ArrayList<Product>(List.of(new Product(1, "Toothpaste", BigDecimal.valueOf(1.99)), new Product(2, "Floss", BigDecimal.valueOf(1.29))));
        ArrayList<Product> products2 = new ArrayList<Product>(List.of(new Product(3, "Laundry Detergent", BigDecimal.valueOf(2.99)), new Product(4, "Deo", BigDecimal.valueOf(3.99))));
        OrderMapRepo olr = new OrderMapRepo();
        Order order1 = new Order(1, products1);
        olr.add(order1);
        olr.add(new Order(2, products2));
        olr.remove(order1);
        Assertions.assertEquals(1, olr.getAll().size());
    }

    @Test
    void getSingle_Order_byId() {
        ArrayList<Product> products1 = new ArrayList<Product>(List.of(new Product(1, "Toothpaste", BigDecimal.valueOf(1.99)), new Product(2, "Floss", BigDecimal.valueOf(1.29))));
        ArrayList<Product> products2 = new ArrayList<Product>(List.of(new Product(3, "Laundry Detergent", BigDecimal.valueOf(2.99)), new Product(4, "Deo", BigDecimal.valueOf(3.99))));
        OrderMapRepo olr = new OrderMapRepo();
        Order order1 = new Order(1, products1);
        olr.add(order1);
        olr.add(new Order(2, products2));
        Assertions.assertEquals(olr.getSingle(1), order1);
    }
}
