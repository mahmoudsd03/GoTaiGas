package com.gotaigas.control.factories;

import com.gotaigas.dtos.CarDTO;
import com.gotaigas.dtos.UserDTO;
import com.gotaigas.entities.Car;

public class CarFactory {
    public static Car createCar(CarDTO carDTO, UserDTO userDTO) {
        // Erzeuge ein Car-Entity; die ID wird intern hochgezählt (@GeneratedValue auf ID)
        Car car = new Car();
        // ID könnte man ggf. noch mal anpassen: car.setID( xx );

        // Übernehme die Grundparameter aus dem DTO, also den Werten, die in der UI eingegeben wurden:
        car.setBrand( carDTO.getBrand()  );
        car.setDate(  carDTO.getDate() );
        car.setDescription( carDTO.getDescription() );
        car.setModel( carDTO.getModel() );
        car.setPhone(  carDTO.getPhone() );
        car.setPrice( carDTO.getPrice() );

        // Setze die UserID des Users, welche das Auto eingestellt hat
        car.setUserid( userDTO.getId() );

        // und zurück das gute Stück:
        return car;
    }
}
