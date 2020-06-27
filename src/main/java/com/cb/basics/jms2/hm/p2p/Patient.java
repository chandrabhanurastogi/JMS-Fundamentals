package com.cb.basics.jms2.hm.p2p;

import java.io.Serializable;


public class Patient implements Serializable {
    private int id;
    private String name;
    private String insuranceProvider;
    private Double copay;
    private Double amountToBePaid;

    public Patient(int id, String name, String insuranceProvider, Double copay, Double amountToBePaid) {
        this.id = id;
        this.name = name;
        this.insuranceProvider = insuranceProvider;
        this.copay = copay;
        this.amountToBePaid = amountToBePaid;
    }

    public Patient() {
    }

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

    public String getInsuranceProvider() {
        return insuranceProvider;
    }

    public void setInsuranceProvider(String insuranceProvider) {
        this.insuranceProvider = insuranceProvider;
    }

    public Double getCopay() {
        return copay;
    }

    public void setCopay(Double copay) {
        this.copay = copay;
    }

    public Double getAmountToBePaid() {
        return amountToBePaid;
    }

    public void setAmountToBePaid(Double amountToBePaid) {
        this.amountToBePaid = amountToBePaid;
    }
}
