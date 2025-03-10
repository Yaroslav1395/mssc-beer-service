package sakhno.springframework.msscbeerservice.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * Класс необходим для настройки кэширования. @EnableCaching дает spring понять что нужно совершать кэширование
 */
@EnableCaching
@Configuration
public class CacheConfig {
}
