package sakhno.springframework.msscbeerservice.services.inventory.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sakhno.springframework.msscbeerservice.web.model.inventory.BeerInventoryDto;

import java.util.List;

@FeignClient(name = "MSSC-INVENTORY-FAILOVER")
public interface BeerInventoryServiceFailFeignClient {
    @RequestMapping(method = RequestMethod.GET, value = "/inventory-failover")
    ResponseEntity<List<BeerInventoryDto>> getOnHandInventory();
}
