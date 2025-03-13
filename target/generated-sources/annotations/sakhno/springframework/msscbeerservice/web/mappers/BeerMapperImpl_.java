package sakhno.springframework.msscbeerservice.web.mappers;

import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import sakhno.springframework.msscbeerservice.domain.BeerEntity;
import sakhno.springframework.msscbeerservice.web.model.beer.BeerDto;
import sakhno.springframework.msscbeerservice.web.model.beer.BeerStyleEnum;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-10T21:18:42+0600",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
@Component
@Qualifier("delegate")
public class BeerMapperImpl_ implements BeerMapper {

    @Autowired
    private DateMapper dateMapper;

    @Override
    public BeerDto beerToBeerDto(BeerEntity beer) {
        if ( beer == null ) {
            return null;
        }

        BeerDto.BeerDtoBuilder beerDto = BeerDto.builder();

        beerDto.id( beer.getId() );
        if ( beer.getVersion() != null ) {
            beerDto.version( beer.getVersion().intValue() );
        }
        beerDto.createdDate( dateMapper.asOffsetDateTime( beer.getCreatedDate() ) );
        beerDto.lastModifiedDate( dateMapper.asOffsetDateTime( beer.getLastModifiedDate() ) );
        beerDto.beerName( beer.getBeerName() );
        if ( beer.getBeerStyle() != null ) {
            beerDto.beerStyle( Enum.valueOf( BeerStyleEnum.class, beer.getBeerStyle() ) );
        }
        beerDto.upc( beer.getUpc() );
        beerDto.price( beer.getPrice() );

        return beerDto.build();
    }

    @Override
    public BeerDto beerToBeerDtoWithInventory(BeerEntity beer) {
        if ( beer == null ) {
            return null;
        }

        BeerDto.BeerDtoBuilder beerDto = BeerDto.builder();

        beerDto.id( beer.getId() );
        if ( beer.getVersion() != null ) {
            beerDto.version( beer.getVersion().intValue() );
        }
        beerDto.createdDate( dateMapper.asOffsetDateTime( beer.getCreatedDate() ) );
        beerDto.lastModifiedDate( dateMapper.asOffsetDateTime( beer.getLastModifiedDate() ) );
        beerDto.beerName( beer.getBeerName() );
        if ( beer.getBeerStyle() != null ) {
            beerDto.beerStyle( Enum.valueOf( BeerStyleEnum.class, beer.getBeerStyle() ) );
        }
        beerDto.upc( beer.getUpc() );
        beerDto.price( beer.getPrice() );

        return beerDto.build();
    }

    @Override
    public BeerEntity beerDtoToBeer(BeerDto dto) {
        if ( dto == null ) {
            return null;
        }

        BeerEntity.BeerEntityBuilder beerEntity = BeerEntity.builder();

        beerEntity.id( dto.getId() );
        if ( dto.getVersion() != null ) {
            beerEntity.version( dto.getVersion().longValue() );
        }
        beerEntity.createdDate( dateMapper.asTimestamp( dto.getCreatedDate() ) );
        beerEntity.lastModifiedDate( dateMapper.asTimestamp( dto.getLastModifiedDate() ) );
        beerEntity.beerName( dto.getBeerName() );
        if ( dto.getBeerStyle() != null ) {
            beerEntity.beerStyle( dto.getBeerStyle().name() );
        }
        beerEntity.upc( dto.getUpc() );
        beerEntity.price( dto.getPrice() );

        return beerEntity.build();
    }
}
