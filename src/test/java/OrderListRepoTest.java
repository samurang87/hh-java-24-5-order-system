import de.neuefische.shopservice.Order;
import de.neuefische.shopservice.OrderListRepo;
import de.neuefische.shopservice.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class OrderListRepoTest {

    @Test
    void addOrder_addsOrderToList() {

        ArrayList<Product> products1 = new ArrayList<Product>(List.of(new Product(1, "Toothpaste"), new Product(2, "Floss")));
        ArrayList<Product> products2 = new ArrayList<Product>(List.of(new Product(3, "Laundry Detergent"), new Product(4, "Deo")));
        OrderListRepo olr = new OrderListRepo();
        olr.add(new Order(1, products1));
        olr.add(new Order(2, products2));
        Assertions.assertEquals(2, olr.getAll().size());
    }

    @Test
    void removeOrder_removesOrderFromList() {
        ArrayList<Product> products1 = new ArrayList<Product>(List.of(new Product(1, "Toothpaste"), new Product(2, "Floss")));
        ArrayList<Product> products2 = new ArrayList<Product>(List.of(new Product(3, "Laundry Detergent"), new Product(4, "Deo")));
        OrderListRepo olr = new OrderListRepo();
        Order order1 = new Order(1, products1);
        olr.add(order1);
        olr.add(new Order(2, products2));
        olr.remove(order1);
        Assertions.assertEquals(1, olr.getAll().size());
    }

    @Test
    void getSingle_Order_byId() {
        ArrayList<Product> products1 = new ArrayList<Product>(List.of(new Product(1, "Toothpaste"), new Product(2, "Floss")));
        ArrayList<Product> products2 = new ArrayList<Product>(List.of(new Product(3, "Laundry Detergent"), new Product(4, "Deo")));
        OrderListRepo olr = new OrderListRepo();
        Order order1 = new Order(1, products1);
        olr.add(order1);
        olr.add(new Order(2, products2));
        Assertions.assertEquals(olr.getSingle(1), order1);
    }
}
