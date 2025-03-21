package sakhno.springframework.msscbeerservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.mapping.Collection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import sakhno.springframework.msscbeerservice.web.model.events.ValidateOrderRequest;
import sakhno.springframework.msscbeerservice.web.model.events.ValidateOrderResult;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JmsConfig {

    public static final String BREWING_REQUEST_QUEUE = "BREWING_REQUEST_QUEUE";
    public static final String NEW_INVENTORY_QUEUE = "NEW_INVENTORY_QUEUE";
    public static final String VALIDATE_ORDER_QUEUE = "VALIDATE_ORDER_QUEUE";
    public static final String VALIDATE_ORDER_RESPONSE_QUEUE = "VALIDATE_ORDER_RESPONSE_QUEUE";

    /**
     * Данный метод создает бин, который будет использоваться как конвертер сообщений при работе с JMS.
     * MappingJackson2MessageConverter — это конвертер сообщений, который использует Jackson для преобразования
     * объектов в JSON и обратно.
     * setTargetType(MessageType.TEXT) — указывает, что сообщения будут передаваться в текстовом формате (обычно
     * это JSON).
     * setTypeIdPropertyName("_type") — добавляет в JSON-объект специальное свойство _type, которое используется для
     * определения типа объекта при десериализации.
     * setObjectMapper(objectMapper) - указывает, что объекты будут преобразованы с помощью ObjectMapper который
     * создал Spring
     * @return - конвертер сообщений
     */
    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        Map<String, Class<?>> typeIdMappings = new HashMap<>();
        typeIdMappings.put("ValidateOrderRequest", ValidateOrderRequest.class);
        typeIdMappings.put("ValidateOrderResult", ValidateOrderResult.class);
        converter.setTypeIdMappings(typeIdMappings);
        converter.setObjectMapper(objectMapper);
        return converter;
    }
}
