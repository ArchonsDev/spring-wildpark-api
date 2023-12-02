package com.archons.springwildparkapi.dto.requests;

import java.time.LocalDateTime;

import com.archons.springwildparkapi.model.Role;

public class UpdateAccountRequest {
    private String password;
    private String firstname;
    private String lastname;
    private LocalDateTime birthdate;
    private String contactNo;
    private String gender;
    private String street;
    private String municipality;
    private String province;
    private String country;
    private int zipCode;
    private Role role;

    public UpdateAccountRequest(String password, String firstname, String lastname, LocalDateTime birthdate,
            String contactNo, String gender, String street, String municipality, String province, String country,
            int zipCode, Role role) {
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.contactNo = contactNo;
        this.gender = gender;
        this.street = street;
        this.municipality = municipality;
        this.province = province;
        this.country = country;
        this.zipCode = zipCode;
        this.role = role;
    }

    public UpdateAccountRequest() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDateTime getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDateTime birthdate) {
        this.birthdate = birthdate;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
