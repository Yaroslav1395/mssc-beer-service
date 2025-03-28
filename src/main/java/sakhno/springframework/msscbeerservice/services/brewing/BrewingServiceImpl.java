package sakhno.springframework.msscbeerservice.services.brewing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sakhno.springframework.msscbeerservice.config.JmsConfig;
import sakhno.springframework.msscbeerservice.domain.BeerEntity;
import sakhno.springframework.msscbeerservice.events.BrewBeerEvent;
import sakhno.springframework.msscbeerservice.repositories.BeerRepository;
import sakhno.springframework.msscbeerservice.services.inventory.BeerInventoryService;
import sakhno.springframework.msscbeerservice.web.mappers.BeerMapper;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BrewingServiceImpl implements BrewingService {
    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

    /**
     * Метод сопоставляет минимальное количество пива с фактическим количеством на складе. В случае если на складе
     * меньше, то в очередь отправляется событие на заказ варки пива
     */
    //@Scheduled(fixedRate = 5000)
    public void checkForLovInventory() {
        List<BeerEntity> beerEntities = beerRepository.findAll();
        beerEntities.forEach(beerEntity -> {
            log.info("Минимальное количество пива {} : {}", beerEntity.getBeerName(), beerEntity.getMinOnHand());
            Integer invQuantityOnHand = beerInventoryService.getOnHandInventory(beerEntity.getId());
            log.info("Количество пива {} в наличии на складе: {}", beerEntity.getBeerName(), invQuantityOnHand);
            if(beerEntity.getMinOnHand() >= invQuantityOnHand) {
                log.info("Недостаточно пива. Отправляем пиво в очередь на варку");
               jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE, new BrewBeerEvent(beerMapper.beerToBeerDto(beerEntity)));
            }
        });
    }
}
