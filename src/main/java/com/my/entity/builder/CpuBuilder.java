package com.my.entity.builder;

import com.my.entity.Cpu;

public class CpuBuilder {
    private final Cpu cpu;

    public CpuBuilder(){
        cpu = new Cpu();
    }

    public CpuBuilder withId(int id){
        cpu.setId(id);
        return this;
    }

    public CpuBuilder withName(String name){
        cpu.setName(name);
        return this;
    }

    public CpuBuilder withManufacturer(String manufacturer){
        cpu.setManufacturer(manufacturer);
        return this;
    }

    public CpuBuilder withPrice(double price){
        cpu.setPrice(price);
        return this;
    }

    public CpuBuilder withCoreNumber(int coreNumber){
        cpu.setCoreNumber(coreNumber);
        return this;
    }

    public CpuBuilder withMemoryType(String memoryType){
        cpu.setMemoryType(memoryType);
        return this;
    }

    public Cpu buildCPU(){
        return cpu;
    }
}
