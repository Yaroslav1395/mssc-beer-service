package sakhno.springframework.msscbeerservice.web.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import sakhno.springframework.msscbeerservice.domain.BeerEntity;
import sakhno.springframework.msscbeerservice.services.inventory.BeerInventoryService;
import sakhno.springframework.msscbeerservice.web.model.beer.BeerDto;

/**
 * Данный класс является декоратором для мапера пива. Он позволяет сочитать генерируемые классы mapstruct и контекст
 * spring. Это дает возможность использовать бины для маппера.
 */
@Component
public abstract class BeerMapperDecorator implements BeerMapper {
    private BeerMapper delegate;
    private BeerInventoryService beerInventoryService;

    @Autowired
    @Qualifier("delegate")
    public void setDelegate(BeerMapper delegate) {
        this.delegate = delegate;
    }

    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
        this.beerInventoryService = beerInventoryService;
    }

    @Override
    public BeerDto beerToBeerDtoWithInventory(BeerEntity beer) {
        BeerDto beerDto = delegate.beerToBeerDto(beer);
        if (beer != null) {
            beerDto.setQuantityOnHand(beerInventoryService.getOnHandInventory(beer.getId()));
        }
        return beerDto;
    }

    @Override
    public BeerDto beerToBeerDto(BeerEntity beer) {
        return delegate.beerToBeerDto(beer);
    }

    @Override
    public BeerEntity beerDtoToBeer(BeerDto beer) {
        return delegate.beerDtoToBeer(beer);
    }
}
