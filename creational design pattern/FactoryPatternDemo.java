/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VehicleFactory;

/**
 *
 * @author nivet
 */
interface Vehicle {
    void drive();
}

// Concrete products
class Car implements Vehicle {
    @Override
    public void drive() {
        System.out.println("Car is driving on the highway!");
    }
}

class Bike implements Vehicle {
    @Override
    public void drive() {
        System.out.println("Bike is zooming through traffic!");
    }
}

class Truck implements Vehicle {
    @Override
    public void drive() {
        System.out.println("Truck is carrying heavy cargo!");
    }
}

// Factory
class VehicleFactory {
    public static Vehicle createVehicle(String type) {
        switch (type.toLowerCase()) {
            case "car": return new Car();
            case "bike": return new Bike();
            case "truck": return new Truck();
            default: throw new IllegalArgumentException("Unknown vehicle type: " + type);
        }
    }
}

// Test
public class FactoryPatternDemo {
    public static void main(String[] args) {
        Vehicle v1 = VehicleFactory.createVehicle("car");
        v1.drive();

        Vehicle v2 = VehicleFactory.createVehicle("bike");
        v2.drive();

        Vehicle v3 = VehicleFactory.createVehicle("truck");
        v3.drive();
    }
}
