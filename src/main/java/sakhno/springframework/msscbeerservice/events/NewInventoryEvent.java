package sakhno.springframework.msscbeerservice.events;

import lombok.NoArgsConstructor;
import sakhno.springframework.msscbeerservice.web.model.beer.BeerDto;

@NoArgsConstructor
public class NewInventoryEvent extends BeerEvent {
    public NewInventoryEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
