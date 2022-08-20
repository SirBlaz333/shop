package com.my.entity.cpu;

import java.util.Objects;

public class Cpu {
    private int id;
    private String name;
    private String manufacturer;
    private double price;
    private int coreNumber;
    private String memoryType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCoreNumber() {
        return coreNumber;
    }

    public void setCoreNumber(int coreNumber) {
        this.coreNumber = coreNumber;
    }

    public String getMemoryType() {
        return memoryType;
    }

    public void setMemoryType(String memoryType) {
        this.memoryType = memoryType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cpu cpu = (Cpu) o;
        return id == cpu.id && Double.compare(cpu.price, price) == 0 && coreNumber == cpu.coreNumber && name.equals(cpu.name) && manufacturer.equals(cpu.manufacturer) && memoryType.equals(cpu.memoryType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, manufacturer, price, coreNumber, memoryType);
    }
}
