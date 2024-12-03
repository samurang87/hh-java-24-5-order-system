import de.neuefische.shopservice.Product;
import de.neuefische.shopservice.ProductRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;


public class ProductRepoTest {

    @Test
    void add_addsProductToList() {
        ProductRepo pr = new ProductRepo();
        pr.add(new Product(1, "Toothpaste", BigDecimal.valueOf(1.99)));
        pr.add(new Product(2, "Floss", BigDecimal.valueOf(1.29)));
        Assertions.assertEquals(2, pr.getAll().size());
    }

    @Test
    void remove_removesProductFromProductList() {
        ProductRepo pr = new ProductRepo();
        Product toothpaste = new Product(1, "Toothpaste", BigDecimal.valueOf(1.99));
        pr.add(toothpaste);
        pr.add(new Product(2, "Floss", BigDecimal.valueOf(1.29)));
        pr.remove(toothpaste);
        Assertions.assertEquals(1, pr.getAll().size());
    }

    @Test
    void getSingle_canUseID() {
        ProductRepo pr = new ProductRepo();
        Product toothpaste = new Product(1, "Toothpaste", BigDecimal.valueOf(1.99));
        pr.add(toothpaste);
        pr.add(new Product(2, "Floss", BigDecimal.valueOf(1.29)));
        Assertions.assertEquals(pr.getSingle(1), toothpaste);
    }

    @Test
    void getSingle_canUseName() {
        ProductRepo pr = new ProductRepo();
        Product toothpaste = new Product(1, "Toothpaste", BigDecimal.valueOf(1.99));
        pr.add(toothpaste);
        pr.add(new Product(2, "Floss", BigDecimal.valueOf(1.29)));
        Assertions.assertEquals(pr.getSingle("Toothpaste"), toothpaste);
    }
}
