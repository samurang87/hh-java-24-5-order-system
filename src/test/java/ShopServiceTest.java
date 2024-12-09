import de.neuefische.shopservice.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShopServiceTest {

    @Test
    void addOrder_addsOrderToOrderList() {
        ShopService shop = new ShopService();
        Product toothpaste = new Product(1, "Toothpaste", BigDecimal.valueOf(1.99));
        shop.addProduct(toothpaste);
        Product floss = new Product(2, "Floss", BigDecimal.valueOf(1.29));
        shop.addProduct(floss);
        shop.addProduct(new Product(3, "TP", BigDecimal.valueOf(1.99)));

        Clock fixedClock = Clock.fixed(Instant.parse("2025-01-01T10:00:00Z"), ZoneId.of("UTC"));

        UUID id1 = UUID.randomUUID();
        Order order = new Order(id1, new ArrayList<Product>(List.of(toothpaste, floss)), Instant.now(fixedClock));

        BigDecimal payable = shop.addOrder(order);

        Order placedOrder = shop.getOlr().getSingle(id1);
        Assertions.assertEquals(2, placedOrder.products().size());
        Assertions.assertEquals(BigDecimal.valueOf(3.28), payable);
        Assertions.assertEquals(Instant.parse("2025-01-01T10:00:00Z"), order.timestamp());
    }

    @Test
    void addOrder_addsOrderToOrderMap() {
        ShopService shop = new ShopService(new OrderMapRepo());
        Product toothpaste = new Product(1, "Toothpaste", BigDecimal.valueOf(1.99));
        shop.addProduct(toothpaste);
        Product floss = new Product(2, "Floss", BigDecimal.valueOf(1.29));
        shop.addProduct(floss);
        shop.addProduct(new Product(3, "TP", BigDecimal.valueOf(1.99)));
        UUID id1 = UUID.randomUUID();
        Order order = new Order(id1, new ArrayList<Product>(List.of(toothpaste, floss)));
        BigDecimal payable = shop.addOrder(order);
        Order placedOrder = shop.getOlr().getSingle(id1);
        Assertions.assertEquals(2, placedOrder.products().size());
        Assertions.assertEquals(BigDecimal.valueOf(3.28), payable);
    }

    @Test
    void addOrder_withMissingProduct_throwsException() {
        ShopService shop = new ShopService();
        Product toothpaste = new Product(1, "Toothpaste", BigDecimal.valueOf(1.99));
        shop.addProduct(toothpaste);
        Product floss = new Product(2, "Floss", BigDecimal.valueOf(1.29));
        shop.addProduct(new Product(3, "TP", BigDecimal.valueOf(1.99)));
        Order order = new Order(UUID.randomUUID(), new ArrayList<Product>(List.of(toothpaste, floss)));
        Assertions.assertThrows(ProductNotFoundException.class, () -> shop.addOrder(order));
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

    @Test
    void updateOrder_updatesOrderWithNewStatus() {
        ShopService shop = new ShopService(new OrderMapRepo());
        Product toothpaste = new Product(1, "Toothpaste", BigDecimal.valueOf(1.99));
        shop.addProduct(toothpaste);
        Product floss = new Product(2, "Floss", BigDecimal.valueOf(1.29));
        shop.addProduct(floss);

        UUID id1 = UUID.randomUUID();
        Order order = new Order(id1, new ArrayList<Product>(List.of(toothpaste, floss)));
        shop.addOrder(order);

        Order orderWithNewStatus = shop.updateOrder(id1, OrderStatus.COMPLETED);
        Assertions.assertEquals(OrderStatus.COMPLETED, orderWithNewStatus.status());
    }
}
