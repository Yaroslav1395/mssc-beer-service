package sakhno.springframework.msscbeerservice.web.model.beer;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -8303500196167963072L;

    @Null(message = "Идентификатор пива должен быть null")
    private UUID id;

    @Null(message = "Версия пива должна быть null")
    private Integer version;

    @Null(message = "Дата создания пива должна быть null")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ", shape=JsonFormat.Shape.STRING)
    private OffsetDateTime createdDate;

    @Null(message = "Дата изменения пива должна быть null")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ", shape=JsonFormat.Shape.STRING)
    private OffsetDateTime lastModifiedDate;

    @NotBlank(message = "Название пива обязательно")
    private String beerName;

    @NotNull(message = "Стиль пива обязателен")
    private BeerStyleEnum beerStyle;

    @NotNull(message = "Уникальный код пива обязателен")
    private String upc;

    @NotNull(message = "Цена пива обязательна")
    @Positive(message = "Цена пива должна быть положительной")
    @NotNull
    private BigDecimal price;

    @Positive(message = "Количество на складе должно быть положительным")
    private Integer quantityOnHand;

}
