package sakhno.springframework.msscbeerservice.services.beer;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import sakhno.springframework.msscbeerservice.domain.BeerEntity;
import sakhno.springframework.msscbeerservice.repositories.BeerRepository;
import sakhno.springframework.msscbeerservice.exceptions.EntityNotFoundException;
import sakhno.springframework.msscbeerservice.web.mappers.BeerMapper;
import sakhno.springframework.msscbeerservice.web.model.beer.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sakhno.springframework.msscbeerservice.web.model.beer.BeerPagedList;

import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    /**
     * Метод позволяет получить все имеющееся пиво с пагинацией
     * @param pageRequest - параметры пагинации
     * @param showInventoryOnHand - параметр показа количества на складе
     * @param beerName - название пива
     * @param beerStyle - стиль пива
     * @return - список пива
     */
    @Override
    @Cacheable(value = "beerListCache", condition = "#showInventoryOnHand == false ")
    public BeerPagedList getAllBeer(PageRequest pageRequest, boolean showInventoryOnHand, String beerName, String beerStyle) {
        System.out.println("Я был вызван");
        Page<BeerEntity> beerPage;
        if(StringUtils.hasText(beerName) && StringUtils.hasText(beerStyle)) {
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if(StringUtils.hasText(beerName) && !StringUtils.hasText(beerStyle)) {
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if(!StringUtils.hasText(beerName) && StringUtils.hasText(beerStyle)) {
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        if (showInventoryOnHand) {
            return new BeerPagedList(beerPage.getContent().stream()
                    .map(beerMapper::beerToBeerDtoWithInventory)
                    .collect(Collectors.toList()), PageRequest.of(beerPage.getPageable().getPageNumber(), beerPage.getPageable().getPageSize()), beerPage.getTotalElements());

        }
        return new BeerPagedList(beerPage.getContent().stream()
                .map(beerMapper::beerToBeerDto)
                .collect(Collectors.toList()), PageRequest.of(beerPage.getPageable().getPageNumber(), beerPage.getPageable().getPageSize()), beerPage.getTotalElements());
    }

    /**
     * Метод позволяет получить пиво по ID
     * @param beerId - идентификатор пива
     * @param showInventoryOnHand - параметр показа количества на складе
     * @return - пиво
     */
    @Override
    @Cacheable(value = "beerCache", key = "#beerId", condition = "#showInventoryOnHand == false ")
    public BeerDto getById(UUID beerId, boolean showInventoryOnHand) {
        BeerEntity beerEntity = beerRepository.findById(beerId).orElseThrow(
                () -> new EntityNotFoundException("Пиво с идентификатором " + beerId + " не существует"));
        if(showInventoryOnHand) {
            return beerMapper.beerToBeerDtoWithInventory(beerEntity);
        }
        return beerMapper.beerToBeerDto(beerEntity);
    }

    /**
     * Метод позволяет получить пиво по UPC
     * @param upc - номер пива
     * @return - пиво
     */
    @Override
    @Cacheable(value = "beerCacheUpc", key = "#upc")
    public BeerDto getByUpc(String upc) {
        BeerEntity beerEntity = beerRepository.findByUpc(upc)
                .orElseThrow(() -> new EntityNotFoundException("Пиво с UPC " + upc + " не существует"));
        return beerMapper.beerToBeerDto(beerEntity);
    }

    /**
     * Метод позволяет сохранить новое пиво
     * @param beerDto - пиво для сохранения
     * @return - сохраненное пиво
     */
    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        BeerEntity beerForSave = beerMapper.beerDtoToBeer(beerDto);
        BeerEntity savedBeer = beerRepository.save(beerForSave);
        return beerMapper.beerToBeerDto(savedBeer);
    }

    /**
     * Метод позволяет отредактировать пиво
     * @param beerId - идентификатор пива
     * @param beerDto - пиво для редактирования
     * @return - отредактированное пиво
     */
    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
        BeerEntity beerEntity = beerRepository.findById(beerId).orElseThrow(
                () -> new EntityNotFoundException("Пиво с идентификатором " + beerId + " не существует"));

        beerEntity.setBeerName(beerDto.getBeerName());
        beerEntity.setBeerStyle(beerDto.getBeerStyle().name());
        beerEntity.setPrice(beerDto.getPrice());
        beerEntity.setUpc(beerDto.getUpc());

        BeerEntity updatedBeer = beerRepository.save(beerEntity);
        return beerMapper.beerToBeerDto(updatedBeer);
    }
}
