package sakhno.springframework.msscbeerservice.services.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sakhno.springframework.msscbeerservice.repositories.BeerRepository;
import sakhno.springframework.msscbeerservice.web.model.beer.order.BeerOrderDto;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
@Slf4j
public class BeerOrderValidator {
    private final BeerRepository beerRepository;

    public Boolean validateOrder(BeerOrderDto beerOrderDto) {
        AtomicInteger beerNotFound = new AtomicInteger();

        beerOrderDto.getBeerOrderLines().forEach(orderLine -> {
            if(beerRepository.findByUpc(orderLine.getUpc()).isEmpty()) {
                beerNotFound.incrementAndGet();
            }
        });
        return beerNotFound.get() == 0;
    }
}
