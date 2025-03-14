package sakhno.springframework.msscbeerservice.events;

import lombok.NoArgsConstructor;
import sakhno.springframework.msscbeerservice.web.model.beer.BeerDto;

@NoArgsConstructor
public class BrewBeerEvent extends BeerEvent {
    public BrewBeerEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
