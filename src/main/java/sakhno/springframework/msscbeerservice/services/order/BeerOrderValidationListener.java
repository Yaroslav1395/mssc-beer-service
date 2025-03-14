package sakhno.springframework.msscbeerservice.services.order;

import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import sakhno.springframework.msscbeerservice.config.JmsConfig;
import sakhno.springframework.msscbeerservice.web.model.beer.order.events.ValidateOrderRequest;
import sakhno.springframework.msscbeerservice.web.model.beer.order.events.ValidateOrderResult;

@Component
@RequiredArgsConstructor
public class BeerOrderValidationListener {
    private final BeerOrderValidator beerOrderValidator;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
    public void listenValidation(ValidateOrderRequest validateOrderRequest) {
        Boolean isValid = beerOrderValidator.validateOrder(validateOrderRequest.getBeerOrderDto());
        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE,
                ValidateOrderResult.builder()
                        .isValid(isValid)
                        .orderId(validateOrderRequest.getBeerOrderDto().getId())
                        .build());
    }
}
