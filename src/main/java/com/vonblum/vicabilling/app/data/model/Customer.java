package com.vonblum.vicabilling.app.data.model;

import com.vonblum.vicabilling.app.config.AppConstant;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.Serializable;

public class Customer implements Serializable {

    public static final String FOLDER_NAME = "customer";
    public static final String FILENAME = "customer";
    public static final String DEF_NAME = "UNKNOWN";

    @Contract(pure = true)
    public static @NotNull String getFilepath() {
        return AppConstant.getFolderSystemBase() + FOLDER_NAME + File.separator + FILENAME;
    }

    public static @NotNull Customer getStandardCustomer() {
        Customer customer = new Customer();
        customer.setName("");
        customer.setPhone("");
        customer.setEmail("");
        customer.setWebpage("");
        customer.setAddress("");
        return customer;
    }

    private String name;
    private String phone;
    private String email;
    private String address;
    private String webpage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebpage() {
        return webpage;
    }

    public void setWebpage(String webpage) {
        this.webpage = webpage;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", webpage='" + webpage + '\'' +
                '}';
    }
}
