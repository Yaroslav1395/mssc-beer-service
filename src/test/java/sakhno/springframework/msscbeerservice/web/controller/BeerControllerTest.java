package sakhno.springframework.msscbeerservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.util.StringUtils;
import sakhno.springframework.msscbeerservice.services.beer.BeerService;
import sakhno.springframework.msscbeerservice.web.model.beer.BeerDto;
import sakhno.springframework.msscbeerservice.web.model.beer.BeerStyleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    BeerService beerService;

    @Test
    void getBeerById() throws Exception {

        given(beerService.getById(any(), anyBoolean())).willReturn(getValidBeerDto());

        mockMvc.perform(get("/api/v1/beer/{beerId}", UUID.randomUUID()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("v1/beer-get",
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("beerId").description("Идентификатор пива в виде UUID для поиска")
                        ),
                        responseFields(
                                fieldWithPath("id").description("Идентификатор пива в виде UUID"),
                                fieldWithPath("version").description("Номер версии пива"),
                                fieldWithPath("createdDate").description("Дата создания пива"),
                                fieldWithPath("lastModifiedDate").description("Дата изменения пива"),
                                fieldWithPath("beerName").description("Название пива"),
                                fieldWithPath("beerStyle").description("Стиль пива"),
                                fieldWithPath("upc").description("Уникальный код пива"),
                                fieldWithPath("price").description("Цена пива"),
                                fieldWithPath("quantityOnHand").description("Количество на складе")
                        )));

    }

    @Test
    void saveNewBeer() throws Exception {
        BeerDto beerDto = getValidBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);
        given(beerService.saveNewBeer(any())).willReturn(beerDto);
        ConstrainedRuFields fields = new ConstrainedRuFields(BeerDto.class);
        mockMvc.perform(post("/api/v1/beer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(beerDtoJson))
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andDo(document("v1/beer-post",
                        preprocessRequest(prettyPrint()),
                        requestFields(
                                fields.withPath("id", "Идентификатор пива должен быть null").ignored(),
                                fields.withPath("version", "Версия пива должна быть null").ignored(),
                                fields.withPath("createdDate", "Дата создания пива должна быть null").ignored(),
                                fields.withPath("lastModifiedDate", "Дата изменения пива должна быть null").ignored(),
                                fields.withPath("quantityOnHand", "Количество на складе должно быть положительным").ignored(),
                                fields.withPath("beerName", "Название пива обязательно. " +
                                        "Минимум 3 символа. Максимум 100 символов.").description("Название пива"),
                                fields.withPath("beerStyle", "Стиль пива обязателен").description("Стиль пива"),
                                fields.withPath("upc", "Уникальный код пива обязателен.").description("Уникальный код пива"),
                                fields.withPath("price", "Цена пива обязательна. Цена пива должна быть положительной").description("Цена пива")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Ссылка на созданный объект пива")
                        )));
    }

    @Test
    void updateBeerById() throws Exception {
        given(beerService.updateBeer(any(), any())).willReturn(getValidBeerDto());

        BeerDto beerDto = getValidBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isNoContent());
    }

    BeerDto getValidBeerDto(){
        return BeerDto.builder()
                .beerName("My Beer")
                .beerStyle(BeerStyleEnum.ALE)
                .price(new BigDecimal("2.99"))
                .upc("0631234200036")
                .build();
    }

    /**
     * Класс позволяет получить описание ошибки валидации для свойства объекта. Описание присваивается на английском
     */
    private static class ConstrainedEnFields {
        private final ConstraintDescriptions constraintDescriptions;

        public ConstrainedEnFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(
                    StringUtils.collectionToDelimitedString(
                            this.constraintDescriptions.descriptionsForProperty(path), ". ")));
        }
    }

    private static class ConstrainedRuFields {
        private final ConstraintDescriptions constraintDescriptions;

        public ConstrainedRuFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path, String description) {
            return fieldWithPath(path).attributes(key("constraints").value(description));
        }
    }
}