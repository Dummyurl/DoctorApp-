package com.bfurns.model;

/**
 * Created by Explicate1 on 12/7/2017.
 */

public class DoctorStaffModel {
    String email,name,contact;
    String Service_name,set_price,service_discount,service_tax,total_cost,amount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getService_name() {
        return Service_name;
    }

    public void setService_name(String service_name) {
        Service_name = service_name;
    }

    public String getSet_price() {
        return set_price;
    }

    public void setSet_price(String set_price) {
        this.set_price = set_price;
    }

    public String getService_discount() {
        return service_discount;
    }

    public void setService_discount(String service_discount) {
        this.service_discount = service_discount;
    }

    public String getService_tax() {
        return service_tax;
    }

    public void setService_tax(String service_tax) {
        this.service_tax = service_tax;
    }

    public String getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(String total_cost) {
        this.total_cost = total_cost;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
