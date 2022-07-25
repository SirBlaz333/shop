package com.my.entity.dto;

import com.my.entity.CPU;

public class CpuDTO {
    private CPU cpu;
    private int manufacturerId;
    private int MemoryTypeId;

    public CPU getCpu() {
        return cpu;
    }

    public void setCpu(CPU cpu) {
        this.cpu = cpu;
    }

    public int getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public int getMemoryTypeId() {
        return MemoryTypeId;
    }

    public void setMemoryTypeId(int memoryTypeId) {
        MemoryTypeId = memoryTypeId;
    }
}
