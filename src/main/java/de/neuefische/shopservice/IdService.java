// Create an IdService for generating an ID, which returns a new UUID in
// the generateId method (using java.util.UUID).
// Create a concrete implementation of the IdService in the main method and pass it to
// the ShopService constructor.

package de.neuefische.shopservice;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IdService {

    private List<UUID> used;

    public IdService() {
        this.used = new ArrayList<UUID>();
    }

    public UUID generateUUID() {
        UUID newUUID;
        do {
            newUUID = UUID.randomUUID();
        } while (used.contains(newUUID));
        used.add(newUUID);
        return newUUID;
    }
}
