package de.neuefische.shopservice;

import java.math.BigDecimal;

public record Product(int id, String name, BigDecimal price) {
    @Override
    public String toString() {
        return name + " for " + price + "€";
    }
}
