package com.thiago.demoproject.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thiago.demoproject.webclient.model.dto.AddressDTO;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonAddressDTO {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private AddressDTO address;

    private String  cep;
    private String message;

    public PersonAddressDTO() {
    }

    public PersonAddressDTO(UUID id, String firstName, String lastName, String email, String gender, AddressDTO address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.address = address;
    }

    public PersonAddressDTO(UUID id, String firstName, String lastName, String email, String gender, String cep, String message) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.cep = cep;
        this.message = message;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
