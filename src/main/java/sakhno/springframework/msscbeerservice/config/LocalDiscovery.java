package sakhno.springframework.msscbeerservice.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Этот класс настраивает обнаружение сервисов в Spring Boot с использованием Spring Cloud.
 * EnableDiscoveryClient — Включает механизм обнаружения сервисов (Service Discovery) через Eureka
 */
@Configuration
@EnableDiscoveryClient
public class LocalDiscovery {
}
