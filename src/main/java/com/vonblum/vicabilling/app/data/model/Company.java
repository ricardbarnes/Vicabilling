package com.vonblum.vicabilling.app.data.model;

import com.vonblum.vicabilling.app.config.AppConstant;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.Serializable;

public class Company implements Serializable {

    public static final String FOLDER_NAME = "company";
    public static final String FILENAME = "company";

    @Contract(pure = true)
    public static @NotNull String getFilepath() {
        return AppConstant.getFolderSystemBase() + FOLDER_NAME + File.separator + FILENAME;
    }

    public static @NotNull Company getDefaultCompany() {
        Company company = new Company();
        company.setName("Assessors de Manteniment VICA, SL");
        company.setPhone("93 478 56 46");
        company.setEmail("vica@vica.cat");
        company.setWebsite("vica.cat");
        company.setAddress("C/Enric Borras, 23-25, Local 6\n08820 El Prat de Llobregat\nBarcelona");
        company.setFooter("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim. McCalister.\n" +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim. McCalister.\n" +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim. McCalister.\n" +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim. McCalister.\n" +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim. McCalister.");
        return company;
    }

    private String name;
    private String website;
    private String phone;
    private String email;
    private String address;
    private String footer;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", website='" + website + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", footer='" + footer + '\'' +
                '}';
    }
}
