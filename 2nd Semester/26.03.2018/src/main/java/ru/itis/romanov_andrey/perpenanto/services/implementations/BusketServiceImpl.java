package ru.itis.romanov_andrey.perpenanto.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.romanov_andrey.perpenanto.dao.interfaces.BusketDAOInterface;
import ru.itis.romanov_andrey.perpenanto.models.Busket;
import ru.itis.romanov_andrey.perpenanto.services.interfaces.BusketServiceInterface;

@Service
public class BusketServiceImpl implements BusketServiceInterface{

    @Autowired
    private BusketDAOInterface busketDAO;

    @Override
    public void addProductToBusket(Busket busket) {
        this.busketDAO.save(busket);
    }

    @Override
    public void updateProductInBusket(Busket busket) {
        this.busketDAO.update(busket);
    }

    @Override
    public void deleteOrderFromBusket(Long id) {
        this.busketDAO.delete(id);
    }
}
