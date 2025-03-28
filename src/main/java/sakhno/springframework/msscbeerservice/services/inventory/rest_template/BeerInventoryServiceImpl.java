package sakhno.springframework.msscbeerservice.services.inventory.rest_template;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import sakhno.springframework.msscbeerservice.services.inventory.BeerInventoryService;
import sakhno.springframework.msscbeerservice.web.model.inventory.BeerInventoryDto;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Данный сервис позволяет получить остаток пива на складе. Он реализован на основе RestTemplate.
 */
//TODO: удалить класс
@Deprecated
//@Service
@Slf4j
@RequiredArgsConstructor
public class BeerInventoryServiceImpl implements BeerInventoryService {
    public static final String INVENTORY_PATH = "/api/v1/inventory/beer/{beerId}";
    private final RestTemplate restTemplate;
    @Value("${sfg.beer.inventory.service.host}")
    private String beerInventoryServiceHost;


    /**
     * Метод позволяет получить количество пива на складе
     * @param beerId - идентификатор пива
     * @return - количество
     */
    @Override
    public Integer getOnHandInventory(UUID beerId) {
        log.info("Вызов сервиса Inventory для получения остатка для пива с ID: {}", beerId);
        ResponseEntity<List<BeerInventoryDto>> beerInventoryResponse = restTemplate
                .exchange(beerInventoryServiceHost + INVENTORY_PATH, HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {}, beerId);
        return Objects.requireNonNull(beerInventoryResponse.getBody())
                .stream().mapToInt(BeerInventoryDto::getQuantityOnHand).sum();
    }
}
