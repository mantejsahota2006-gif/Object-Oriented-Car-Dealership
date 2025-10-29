package com.pluralsight;

import java.io.*;
import java.util.ArrayList;

public class DealershipFileManager {

    private static final String FILE_NAME = "inventory.csv";

    public Dealership getDealership() {
        Dealership dealership = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String dealershipLine = reader.readLine();

            if (dealershipLine != null) {
                String[] dealerData = dealershipLine.split("\\|");
                String name = dealerData[0];
                String address = dealerData[1];
                String phone = dealerData[2];

                dealership = new Dealership(name, address, phone);
            }


            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");

                // Parse vehicle data
                int vin = Integer.parseInt(data[0]);
                int year = Integer.parseInt(data[1]);
                String make = data[2];
                String model = data[3];
                String vehicleType = data[4];
                String color = data[5];
                int odometer = Integer.parseInt(data[6]);
                double price = Double.parseDouble(data[7]);

                Vehicle vehicle = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);
                dealership.addVehicle(vehicle);
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return dealership;
    }


    public void saveDealership(Dealership dealership) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            writer.printf("%s|%s|%s%n", dealership.getName(), dealership.getAddress(), dealership.getPhone());

            for (Vehicle v : dealership.getAllVehicles()) {
                writer.printf("%d|%d|%s|%s|%s|%s|%d|%.2f%n",
                        v.getVin(), v.getYear(), v.getMake(), v.getModel(),
                        v.getVehicleType(), v.getColor(), v.getOdometer(), v.getPrice());
            }

        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}

