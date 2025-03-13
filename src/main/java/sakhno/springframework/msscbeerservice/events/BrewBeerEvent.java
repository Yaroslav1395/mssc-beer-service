package sakhno.springframework.msscbeerservice.events;

import sakhno.springframework.msscbeerservice.web.model.beer.BeerDto;

public class BrewBeerEvent extends BeerEvent {
    public BrewBeerEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
