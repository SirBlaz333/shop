package com.my.entity.builder;

import com.my.entity.CPU;

public class CPUBuilder {
    private final CPU cpu;

    public CPUBuilder(){
        cpu = new CPU();
    }

    public CPUBuilder withId(int id){
        cpu.setId(id);
        return this;
    }

    public CPUBuilder withName(String name){
        cpu.setName(name);
        return this;
    }

    public CPUBuilder withManufacturer(String manufacturer){
        cpu.setManufacturer(manufacturer);
        return this;
    }

    public CPUBuilder withPrice(double price){
        cpu.setPrice(price);
        return this;
    }

    public CPUBuilder withCoreNumber(int coreNumber){
        cpu.setCoreNumber(coreNumber);
        return this;
    }

    public CPUBuilder withMemoryType(String memoryType){
        cpu.setMemoryType(memoryType);
        return this;
    }

    public CPU buildCPU(){
        return cpu;
    }
}
