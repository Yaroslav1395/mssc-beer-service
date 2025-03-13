package sakhno.springframework.msscbeerservice.events;

import sakhno.springframework.msscbeerservice.web.model.beer.BeerDto;

public class NewInventoryEvent extends BeerEvent {
    public NewInventoryEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
