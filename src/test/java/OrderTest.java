import de.neuefische.shopservice.Order;
import de.neuefische.shopservice.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderTest {

    @Test
    void getTotalPrice_returnsTotalPrice() {
        ArrayList<Product> products1 = new ArrayList<Product>(
                List.of(
                        new Product(1, "Toothpaste", BigDecimal.valueOf(1.99)),
                        new Product(2, "Floss", BigDecimal.valueOf(1.29))));
        Order order1 = new Order(UUID.randomUUID(), products1);
        Assertions.assertEquals(BigDecimal.valueOf(3.28), order1.totalPrice());
    }
}
