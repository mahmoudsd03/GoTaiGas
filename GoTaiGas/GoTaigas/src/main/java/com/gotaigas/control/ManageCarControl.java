package com.gotaigas.control;

import com.gotaigas.control.factories.CarFactory;
import com.gotaigas.dtos.CarDTO;
import com.gotaigas.dtos.UserDTO;
import com.gotaigas.entities.Car;
import com.gotaigas.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ManageCarControl {

    @Autowired
    private CarRepository repository;


    public void createCar( CarDTO carDTO , UserDTO userDTO ) {
        // Hier könnte man noch die Gültigkeit der Daten überprüfen
        // check( carDTO );

        //Erzeuge ein neues Car-Entity konsistent über eine Factory
        Car carEntity = CarFactory.createCar(  carDTO , userDTO  );

        // Abspeicherung des Entity in die DB
        this.repository.save( carEntity );
    }

    public List<CarDTO> readAllCars() {
        return repository.findCarsByDateIsNotNull();
    }

}
