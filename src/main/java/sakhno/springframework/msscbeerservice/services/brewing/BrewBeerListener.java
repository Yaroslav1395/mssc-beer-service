package sakhno.springframework.msscbeerservice.services.brewing;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import sakhno.springframework.msscbeerservice.config.JmsConfig;
import sakhno.springframework.msscbeerservice.domain.BeerEntity;
import sakhno.springframework.msscbeerservice.events.BrewBeerEvent;
import sakhno.springframework.msscbeerservice.events.NewInventoryEvent;
import sakhno.springframework.msscbeerservice.repositories.BeerRepository;
import sakhno.springframework.msscbeerservice.web.model.beer.BeerDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class BrewBeerListener {

    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;

    /**
     * Данный метод является имитацией поставщика пива. Поставщик принимает заявку на варку пива. Узнает квоту на
     * изготовление. Готовит пиво. И отправляет его в очередь на склад.
     * @param event - событие заявки на варку пива
     */
    @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
    public void listen(BrewBeerEvent event) {
        log.info("Заявка на варку пива {} получено.", event.getBeerDto().getBeerName());
        BeerDto beerDto = event.getBeerDto();
        BeerEntity beerEntity = beerRepository.findById(beerDto.getId()).orElseThrow(
                () -> new EntityNotFoundException("Пиво с id {} не существует " + beerDto.getId()));
        beerDto.setQuantityOnHand(beerEntity.getQuantityToBrew());
        NewInventoryEvent newInventoryEvent = new NewInventoryEvent(beerDto);
        log.info("Пиво сварено. Минимальное количество на складе: {}. Количество сваренного пива: {}",
                beerEntity.getMinOnHand(), beerDto.getQuantityOnHand());
        jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, newInventoryEvent);
    }
}
