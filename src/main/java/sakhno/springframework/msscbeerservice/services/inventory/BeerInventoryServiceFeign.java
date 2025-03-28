package sakhno.springframework.msscbeerservice.services.inventory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sakhno.springframework.msscbeerservice.services.inventory.feign.InventoryServiceFeignClient;
import sakhno.springframework.msscbeerservice.web.model.inventory.BeerInventoryDto;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BeerInventoryServiceFeign implements BeerInventoryService {
    private final InventoryServiceFeignClient inventoryServiceFeignClient;

    /**
     * Метод позволяет получить количество пива на складе
     * @param beerId - идентификатор пива
     * @return - количество
     */
    @Override
    public Integer getOnHandInventory(UUID beerId) {
        log.info("Вызов сервиса Inventory для получения остатка для пива с ID: {}", beerId);
        ResponseEntity<List<BeerInventoryDto>> beerInventoryResponse = inventoryServiceFeignClient.getOnHandInventory(
                beerId);
        Integer onHand = Objects.requireNonNull(beerInventoryResponse.getBody()).stream()
                .mapToInt(BeerInventoryDto::getQuantityOnHand)
                .sum();
        log.info("Остаток пива: {}", onHand);
        return onHand;
    }
}
