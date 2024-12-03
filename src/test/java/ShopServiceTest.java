import de.neuefische.shopservice.Order;
import de.neuefische.shopservice.OrderMapRepo;
import de.neuefische.shopservice.Product;
import de.neuefische.shopservice.ShopService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShopServiceTest {

    @Test
    void addOrder_addsOrderToOrderList() {
        ShopService shop = new ShopService();
        Product toothpaste = new Product(1, "Toothpaste", BigDecimal.valueOf(1.99));
        shop.addProduct(toothpaste);
        Product floss = new Product(2, "Floss", BigDecimal.valueOf(1.29));
        shop.addProduct(floss);
        shop.addProduct(new Product(3, "TP", BigDecimal.valueOf(1.99)));
        Order order = new Order(1, new ArrayList<Product>(List.of(toothpaste, floss)));
        BigDecimal payable = shop.addOrder(order);
        Order placedOrder = shop.getOlr().getSingle(1);
        Assertions.assertEquals(2, placedOrder.products().size());
        Assertions.assertEquals(BigDecimal.valueOf(3.28), payable);
    }

    @Test
    void addOrder_addsOrderToOrderMap() {
        ShopService shop = new ShopService(new OrderMapRepo());
        Product toothpaste = new Product(1, "Toothpaste", BigDecimal.valueOf(1.99));
        shop.addProduct(toothpaste);
        Product floss = new Product(2, "Floss", BigDecimal.valueOf(1.29));
        shop.addProduct(floss);
        shop.addProduct(new Product(3, "TP", BigDecimal.valueOf(1.99)));
        Order order = new Order(1, new ArrayList<Product>(List.of(toothpaste, floss)));
        BigDecimal payable = shop.addOrder(order);
        Order placedOrder = shop.getOlr().getSingle(1);
        Assertions.assertEquals(2, placedOrder.products().size());
        Assertions.assertEquals(BigDecimal.valueOf(3.28), payable);
    }

    @Test
    void addOrder_withMissingProduct_removesProductFromOrder() {
        ShopService shop = new ShopService();
        Product toothpaste = new Product(1, "Toothpaste", BigDecimal.valueOf(1.99));
        shop.addProduct(toothpaste);
        Product floss = new Product(2, "Floss", BigDecimal.valueOf(1.29));
        shop.addProduct(new Product(3, "TP", BigDecimal.valueOf(1.99)));
        Order order = new Order(1, new ArrayList<Product>(List.of(toothpaste, floss)));
        BigDecimal payable = shop.addOrder(order);
        Order placedOrder = shop.getOlr().getSingle(1);
        Assertions.assertEquals(1, placedOrder.products().size());
        Assertions.assertEquals(BigDecimal.valueOf(1.99), payable);
    }

    @Test
    void placeHolder_withMissingProduct_doesntAddProductToOrder() {
        ShopService shop = new ShopService();
        shop.addProduct(new Product(1, "Toothpaste", BigDecimal.valueOf(1.99)));
        shop.addProduct(new Product(2, "Floss", BigDecimal.valueOf(1.29)));
        shop.addProduct(new Product(3, "TP", BigDecimal.valueOf(1.99)));

        List<String> cart = new ArrayList<>(List.of("Toothpaste", "Floss", "TP", "Nail Polish"));
        BigDecimal payable = shop.placeOrder(cart);
        Order placedOrder = shop.listOrders().getFirst();

        Assertions.assertEquals(3, placedOrder.products().size());
        Assertions.assertEquals(BigDecimal.valueOf(5.27), payable);
    }
}
