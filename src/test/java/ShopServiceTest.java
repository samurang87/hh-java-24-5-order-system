import de.neuefische.shopservice.Order;
import de.neuefische.shopservice.OrderMapRepo;
import de.neuefische.shopservice.Product;
import de.neuefische.shopservice.ShopService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ShopServiceTest {

    @Test
    void placeOrder_addsOrderToOrderList() {
        ShopService shop = new ShopService();
        Product toothpaste = new Product(1, "Toothpaste");
        shop.getPr().add(toothpaste);
        Product floss = new Product(2, "Floss");
        shop.getPr().add(floss);
        shop.getPr().add(new Product(3, "TP"));
        Order order = new Order(1, new ArrayList<Product>(List.of(toothpaste, floss)));
        shop.placeOrder(order);
        Order placedOrder = shop.getOlr().getSingle(1);
        Assertions.assertEquals(2, placedOrder.products().size());
    }

    @Test
    void placeOrder_addsOrderToOrderMap() {
        ShopService shop = new ShopService(new OrderMapRepo());
        Product toothpaste = new Product(1, "Toothpaste");
        shop.getPr().add(toothpaste);
        Product floss = new Product(2, "Floss");
        shop.getPr().add(floss);
        shop.getPr().add(new Product(3, "TP"));
        Order order = new Order(1, new ArrayList<Product>(List.of(toothpaste, floss)));
        shop.placeOrder(order);
        Order placedOrder = shop.getOlr().getSingle(1);
        Assertions.assertEquals(2, placedOrder.products().size());
    }

    @Test
    void placeOrder_withMissingProduct_removesProductFromOrder() {
        ShopService shop = new ShopService();
        Product toothpaste = new Product(1, "Toothpaste");
        shop.getPr().add(toothpaste);
        Product floss = new Product(2, "Floss");
        shop.getPr().add(new Product(3, "TP"));
        Order order = new Order(1, new ArrayList<Product>(List.of(toothpaste, floss)));
        shop.placeOrder(order);
        Order placedOrder = shop.getOlr().getSingle(1);
        Assertions.assertEquals(1, placedOrder.products().size());
    }
}
