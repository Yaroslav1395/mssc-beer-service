package sakhno.springframework.msscbeerservice.web.mappers;

import org.mapstruct.*;
import sakhno.springframework.msscbeerservice.domain.BeerEntity;
import sakhno.springframework.msscbeerservice.web.model.beer.BeerDto;


@Mapper(componentModel = "spring", uses = {DateMapper.class})
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {

    /**
     * Метод позволяет преобразовать сущность класса BeerEntity в BeerDto
     * @param beer - сущность пива
     * @return - DTO пива
     */
    BeerDto beerToBeerDto(BeerEntity beer);

    /**
     * Метод позволяет преобразовать сущность класса BeerEntity в BeerDto
     * c использованием декоратора для получения количества на складе
     * @param beer - сущность пива
     * @return - DTO пива
     */
    BeerDto beerToBeerDtoWithInventory(BeerEntity beer);

    /**
     * Метод позволяет преобразовать сущность класса BeerDto в BeerEntity
     * @param dto - DTO пива
     * @return - сущность пива
     */
    BeerEntity beerDtoToBeer(BeerDto dto);
}
