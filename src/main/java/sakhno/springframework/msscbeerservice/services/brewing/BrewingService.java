package sakhno.springframework.msscbeerservice.services.brewing;

public interface BrewingService {

    /**
     * Метод сопоставляет минимальное количество пива с фактическим количеством на складе. В случае если на складе
     * меньше, то в очередь отправляется событие на заказ варки пива
     */
    void checkForLovInventory();
}
