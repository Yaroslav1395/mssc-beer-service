package sakhno.springframework.msscbeerservice.services.inventory.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sakhno.springframework.msscbeerservice.web.model.inventory.BeerInventoryDto;

import java.util.List;

/**
 * Данный класс создает под капотом HttpClient и отправляет запросы на сервис MSSC-INVENTORY-FAILOVER.
 * Это запасной сервис. Используется как альтернативный сценарий, если основной недоступен
 */
@FeignClient(name = "MSSC-INVENTORY-FAILOVER")
public interface BeerInventoryServiceFailFeignClient {

    /**
     * Метод отправляет запрос на сервис MSSC-INVENTORY-FAILOVER который дублирует запрос на склад, если сервис склада
     * недоступен
     * @return - список пива на складе
     */
    @RequestMapping(method = RequestMethod.GET, value = "/inventory-failover")
    ResponseEntity<List<BeerInventoryDto>> getOnHandInventory();
}
