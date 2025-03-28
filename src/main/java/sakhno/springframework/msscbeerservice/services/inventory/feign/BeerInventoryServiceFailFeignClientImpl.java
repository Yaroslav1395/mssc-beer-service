package sakhno.springframework.msscbeerservice.services.inventory.feign;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import sakhno.springframework.msscbeerservice.web.model.inventory.BeerInventoryDto;

import java.util.List;
import java.util.UUID;

/**
 * Данный класс реализует альтернативный сценарий отправки запроса на получение остатков пива на складе.
 * Отрабатывает в случае если склад недоступен
 */
@Component
@RequiredArgsConstructor
public class BeerInventoryServiceFailFeignClientImpl implements InventoryServiceFeignClient {
    private final BeerInventoryServiceFailFeignClient beerInventoryServiceFailFeignClient;

    @Override
    public ResponseEntity<List<BeerInventoryDto>> getOnHandInventory(UUID beerId) {
        return beerInventoryServiceFailFeignClient.getOnHandInventory();
    }
}
