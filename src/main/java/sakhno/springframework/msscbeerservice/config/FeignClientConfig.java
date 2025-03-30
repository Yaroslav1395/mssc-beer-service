package sakhno.springframework.msscbeerservice.config;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    /**
     * Данный метод создает объект для авторизации при запросах на BEER-INVENTORY-SERVICE. При каждом запросе в заголовок
     * будет помещаться логин и пароль
     * @param inventoryUser - логин
     * @param inventoryPassword - пароль
     * @return - объект авторизации
     */
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor(@Value("${beer.inventory.username}") String inventoryUser,
                                                                   @Value("${beer.inventory.password}") String inventoryPassword){
        return new BasicAuthRequestInterceptor(inventoryUser, inventoryPassword);
    }
}
