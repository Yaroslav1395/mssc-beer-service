package sakhno.springframework.msscbeerservice.events;

import lombok.*;
import sakhno.springframework.msscbeerservice.web.model.beer.BeerDto;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerEvent implements Serializable {
    @Serial
    private static final long serialVersionUID = 105476142078104064L;
    private BeerDto beerDto;
}
