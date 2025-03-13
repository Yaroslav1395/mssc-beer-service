package sakhno.springframework.msscbeerservice.events;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import sakhno.springframework.msscbeerservice.web.model.beer.BeerDto;

import java.io.Serial;
import java.io.Serializable;

@Data
@RequiredArgsConstructor
@Builder
public class BeerEvent implements Serializable {
    @Serial
    private static final long serialVersionUID = 105476142078104064L;
    private final BeerDto beerDto;
}
