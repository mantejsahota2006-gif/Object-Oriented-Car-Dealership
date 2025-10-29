package com.pluralsight;

import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private Dealership dealership;
    private final Scanner scanner = new Scanner(System.in);
    private final DealershipFileManager fileManager = new DealershipFileManager();

    public UserInterface() {
        init();
    }

    private void init() {
        this.dealership = fileManager.getDealership();
    }

    public void display() {
        int command;

        do {
            displayMenu();
            System.out.print("Enter command: ");
            while (!scanner.hasNextInt()) {
                System.out.print("Please enter a valid number: ");
                scanner.next();
            }
            command = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (command) {
                case 1 -> processGetByPriceRequest();
                case 2 -> processGetByMakeModelRequest();
                case 3 -> processGetByYearRequest();
                case 4 -> processGetByColorRequest();
                case 5 -> processGetByMileageRequest();
                case 6 -> processGetByVehicleTypeRequest();
                case 7 -> processGetAllVehiclesRequest();
                case 8 -> processAddVehicleRequest();
                case 9 -> processRemoveVehicleRequest();
                case 99 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid option. Try again.");
            }

        } while (command != 99);
    }

    private void displayMenu() {
        System.out.println();
        System.out.println("----- DEALERSHIP MENU -----");
        System.out.println("1 - Find vehicles within a price range");
        System.out.println("2 - Find vehicles by make/model");
        System.out.println("3 - Find vehicles by year range");
        System.out.println("4 - Find vehicles by color");
        System.out.println("5 - Find vehicles by mileage range");
        System.out.println("6 - Find vehicles by type (car, truck, SUV, van)");
        System.out.println("7 - List ALL vehicles");
        System.out.println("8 - Add a vehicle");
        System.out.println("9 - Remove a vehicle");
        System.out.println("99 - Quit");
        System.out.println("-------------");
    }

    private void displayVehicles(List<Vehicle> vehicles) {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found.");
        } else {
            for (Vehicle v : vehicles) {
                System.out.println(v);
            }
        }
    }

    public void processGetAllVehiclesRequest() {
        List<Vehicle> allVehicles = dealership.getAllVehicles();
        displayVehicles(allVehicles);
    }

    public void processGetByPriceRequest() {
        System.out.print("Enter minimum price: ");
        double min = scanner.nextDouble();
        System.out.print("Enter maximum price: ");
        double max = scanner.nextDouble();
        scanner.nextLine();
        List<Vehicle> results = dealership.getVehiclesByPrice(min, max);
        displayVehicles(results);
    }

    public void processGetByMakeModelRequest() {
        System.out.print("Enter make: ");
        String make = scanner.nextLine();
        System.out.print("Enter model: ");
        String model = scanner.nextLine();
        List<Vehicle> results = dealership.getVehiclesByMakeModel(make, model);
        displayVehicles(results);
    }

    public void processGetByYearRequest() {
        System.out.print("Enter minimum year: ");
        int min = scanner.nextInt();
        System.out.print("Enter maximum year: ");
        int max = scanner.nextInt();
        scanner.nextLine();
        List<Vehicle> results = dealership.getVehiclesByYear(min, max);
        displayVehicles(results);
    }

    public void processGetByColorRequest() {
        System.out.print("Enter color: ");
        String color = scanner.nextLine();
        List<Vehicle> results = dealership.getVehiclesByColor(color);
        displayVehicles(results);
    }

    public void processGetByMileageRequest() {
        System.out.print("Enter minimum mileage: ");
        int min = scanner.nextInt();
        System.out.print("Enter maximum mileage: ");
        int max = scanner.nextInt();
        scanner.nextLine();
        List<Vehicle> results = dealership.getVehiclesByMileage(min, max);
        displayVehicles(results);
    }

    public void processGetByVehicleTypeRequest() {
        System.out.print("Enter vehicle type (car, truck, SUV, etc.): ");
        String type = scanner.nextLine();
        List<Vehicle> results = dealership.getVehiclesByType(type);
        displayVehicles(results);
    }

    public void processAddVehicleRequest() {
        System.out.print("Enter VIN: ");
        int vin = scanner.nextInt();
        System.out.print("Enter year: ");
        int year = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter make: ");
        String make = scanner.nextLine();
        System.out.print("Enter model: ");
        String model = scanner.nextLine();
        System.out.print("Enter vehicle type: ");
        String type = scanner.nextLine();
        System.out.print("Enter color: ");
        String color = scanner.nextLine();
        System.out.print("Enter odometer: ");
        int odometer = scanner.nextInt();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        Vehicle newVehicle = new Vehicle(vin, year, make, model, type, color, odometer, price);
        dealership.addVehicle(newVehicle);
        fileManager.saveDealership(dealership);
        System.out.println("Vehicle added successfully!");
    }

    public void processRemoveVehicleRequest() {
        System.out.print("Enter VIN of vehicle to remove: ");
        int vin = scanner.nextInt();
        scanner.nextLine();

        Vehicle toRemove = null;
        for (Vehicle v : dealership.getAllVehicles()) {
            if (v.getVin() == vin) {
                toRemove = v;
                break;
            }
        }

        if (toRemove != null) {
            dealership.removeVehicle(toRemove);
            fileManager.saveDealership(dealership);
            System.out.println("Vehicle removed successfully!");
        } else {
            System.out.println("Vehicle not found.");
        }
    }
}