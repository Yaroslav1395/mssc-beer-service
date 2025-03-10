package sakhno.springframework.msscbeerservice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import sakhno.springframework.msscbeerservice.domain.BeerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface BeerRepository extends JpaRepository<BeerEntity, UUID> {

    /**
     * Метод позволяет найти пиво по названию и стилю
     * @param beerName - название пива
     * @param beerStyle - стиль пива
     * @param pageable - параметры пагинация
     * @return - список найденного пива с пагинацией
     */
    Page<BeerEntity> findAllByBeerNameAndBeerStyle(String beerName, String beerStyle, Pageable pageable);

    /**
     * Метод позволяет найти пиво по названию
     * @param beerName - название пива
     * @param pageable - параметры пагинация
     * @return - список найденного пива с пагинацией
     */
    Page<BeerEntity> findAllByBeerName(String beerName, Pageable pageable);

    /**
     * Метод позволяет найти пиво по стилю
     * @param beerStyle - стиль пива
     * @param pageable - параметры пагинация
     * @return - список найденного пива с пагинацией
     */
    Page<BeerEntity> findAllByBeerStyle(String beerStyle, Pageable pageable);

    /**
     * Метод позволяет найти пиво по уникальному номеру
     * @param upc - уникальный номер
     * @return - пиво
     */
    Optional<BeerEntity> findByUpc(String upc);
}
