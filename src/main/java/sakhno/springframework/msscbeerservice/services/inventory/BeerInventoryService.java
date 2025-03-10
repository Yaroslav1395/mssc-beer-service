package sakhno.springframework.msscbeerservice.services.inventory;

import java.util.UUID;

public interface BeerInventoryService {

    /**
     * Метод позволяет получить количество пива на складе
     * @param beerId - идентификатор пива
     * @return - количество
     */
    Integer getOnHandInventory(UUID beerId);
}
