package com.my.entity.dto;

import com.my.entity.Cpu;

public class CpuDTO {
    private Cpu cpu;
    private int manufacturerId;
    private int MemoryTypeId;

    public CpuDTO(){
        cpu = new Cpu();
    }

    public Cpu getCpu() {
        return cpu;
    }

    public void setCpu(Cpu cpu) {
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
