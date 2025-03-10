package sakhno.springframework.msscbeerservice.services.beer;

import org.springframework.data.domain.PageRequest;
import sakhno.springframework.msscbeerservice.web.model.beer.BeerDto;
import sakhno.springframework.msscbeerservice.web.model.beer.BeerPagedList;

import java.util.UUID;


public interface BeerService {


    /**
     * Метод позволяет получить все имеющееся пиво с пагинацией
     * @param pageRequest - параметры пагинации
     * @param showInventoryOnHand - параметр показа количества на складе
     * @param beerName - название пива
     * @param beerStyle - стиль пива
     * @return - список пива
     */
    BeerPagedList getAllBeer(PageRequest pageRequest, boolean showInventoryOnHand, String beerName, String beerStyle);

    /**
     * Метод позволяет получить пиво по ID
     * @param beerId - идентификатор пива
     * @param showInventoryOnHand - параметр показа количества на складе
     * @return - пиво
     */
    BeerDto getById(UUID beerId, boolean showInventoryOnHand);

    /**
     * Метод позволяет получить пиво по UPC
     * @param upc - номер пива
     * @return - пиво
     */
    BeerDto getByUpc(String upc);

    /**
     * Метод позволяет сохранить новое пиво
     * @param beerDto - пиво для сохранения
     * @return - сохраненное пиво
     */
    BeerDto saveNewBeer(BeerDto beerDto);

    /**
     * Метод позволяет отредактировать пиво
     * @param beerId - идентификатор пива
     * @param beerDto - пиво для редактирования
     * @return - отредактированное пиво
     */
    BeerDto updateBeer(UUID beerId, BeerDto beerDto);
}
