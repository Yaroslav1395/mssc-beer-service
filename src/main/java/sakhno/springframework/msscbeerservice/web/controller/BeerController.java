package sakhno.springframework.msscbeerservice.web.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import sakhno.springframework.msscbeerservice.services.beer.BeerService;
import sakhno.springframework.msscbeerservice.web.model.beer.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sakhno.springframework.msscbeerservice.web.model.beer.BeerPagedList;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/")
@RestController
public class BeerController {

    private final BeerService beerService;

    @GetMapping("beer")
    public ResponseEntity<BeerPagedList> getAllBeer(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "25") Integer pageSize,
            @RequestParam(value = "showInventoryOnHand", required = false, defaultValue = "false") Boolean showInventoryOnHand,
            @RequestParam(value = "beerName", required = false) String beerName,
            @RequestParam(value = "beerStyle", required = false) String beerStyle) {
        BeerPagedList beerPagedList = beerService.getAllBeer(PageRequest.of(pageNumber, pageSize), showInventoryOnHand, beerName, beerStyle);
        return new ResponseEntity<>(beerPagedList, HttpStatus.OK);
    }

    @GetMapping("beer/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(
            @PathVariable("beerId") UUID beerId,
            @RequestParam(value = "showInventoryOnHand", required = false, defaultValue = "false") Boolean showInventoryOnHand){
        return new ResponseEntity<>(beerService.getById(beerId, showInventoryOnHand), HttpStatus.OK);
    }

    @GetMapping("beer/upc/{upc}")
    public ResponseEntity<BeerDto> getBeerByUpc(@PathVariable("upc") String upc){
        return new ResponseEntity<>(beerService.getByUpc(upc), HttpStatus.OK);
    }

    @PostMapping("beer")
    public ResponseEntity saveNewBeer(@RequestBody @Validated BeerDto beerDto){
        HttpHeaders headers = new HttpHeaders();
        BeerDto newBeer = beerService.saveNewBeer(beerDto);
        headers.add(HttpHeaders.LOCATION, "/api/v1/beer/" + newBeer.getId());
        return new ResponseEntity<>(newBeer, headers, HttpStatus.CREATED);
    }

    @PutMapping("beer/{beerId}")
    public ResponseEntity updateBeerById(@PathVariable("beerId") UUID beerId, @RequestBody @Validated BeerDto beerDto){
        return new ResponseEntity<>(beerService.updateBeer(beerId, beerDto), HttpStatus.NO_CONTENT);
    }

}
