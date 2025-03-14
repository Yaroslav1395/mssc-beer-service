package sakhno.springframework.msscbeerservice.web.model.beer.order.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sakhno.springframework.msscbeerservice.web.model.beer.order.BeerOrderDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidateOrderRequest {
    private BeerOrderDto beerOrderDto;
}
