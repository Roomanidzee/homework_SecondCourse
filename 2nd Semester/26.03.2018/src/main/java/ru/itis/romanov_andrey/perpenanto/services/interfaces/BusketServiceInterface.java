package ru.itis.romanov_andrey.perpenanto.services.interfaces;

import ru.itis.romanov_andrey.perpenanto.models.Busket;

public interface BusketServiceInterface {

    void addProductToBusket(Busket busket);
    void updateProductInBusket(Busket busket);
    void deleteOrderFromBusket(Long id);

}
