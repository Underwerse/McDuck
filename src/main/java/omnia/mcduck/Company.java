/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omnia.mcduck;

import java.io.Serializable;

/**
 *
 * @author Underwerse
 */
public class Company implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String ceoName;
    private String address;
    private String phone;
    private double sales;
    
    public Company(String name, String ceoName, String address, String phone, double sales) {
        this.name = name;
        this.ceoName = ceoName;
        this.address = address;
        this.phone = phone;
        this.sales = sales;
    }

    public String getName() {
        return this.name;
    }
    
    public String getCeoName() {
        return this.ceoName;
    }

    public String getAddress() {
        return this.address;
    }

    public String getPhone() {
        return this.phone;
    }

    public double getSales() {
        return this.sales;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setCeoName(String ceoName) {
        this.ceoName = ceoName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSales(double sales) {
        this.sales = sales;
    }

    @Override
    public String toString() {
        return "Company: " + 
                this.name + 
                "\nCEO: " +
                this.ceoName +
                ",\naddress: " + 
                this.address + 
                ",\nphone: " + 
                this.phone + 
                ",\nlast year’s sales: " + 
                this.sales + 
                " EUR" +
                "\n************";
    }

}
