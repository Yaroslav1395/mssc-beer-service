package sakhno.springframework.msscbeerservice.services.inventory.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sakhno.springframework.msscbeerservice.services.inventory.rest_template.BeerInventoryServiceImpl;
import sakhno.springframework.msscbeerservice.web.model.inventory.BeerInventoryDto;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "MSSC-BEER-INVENTORY-SERVICE", fallback = BeerInventoryServiceFailFeignClientImpl.class)
public interface InventoryServiceFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = BeerInventoryServiceImpl.INVENTORY_PATH)
    //@CircuitBreaker(name="MSSC-BEER-INVENTORY-SERVICE-BREAKER")
    //@Retry(name="MSSC-BEER-INVENTORY-SERVICE-RETRY")
    ResponseEntity<List<BeerInventoryDto>> getOnHandInventory(@PathVariable UUID beerId);
}
