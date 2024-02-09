package com.thiago.demoproject.producer.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class EmailDTO implements Serializable {

    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private String cep;
    private String subject;
    private String text;

    public EmailDTO() {
    }

    public EmailDTO(UUID id, String email, String firstName, String lastName, String cep, String subject, String text) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cep = cep;
        this.subject = subject;
        this.text = text;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailDTO emailDTO = (EmailDTO) o;
        return Objects.equals(id, emailDTO.id) && Objects.equals(email, emailDTO.email) && Objects.equals(firstName, emailDTO.firstName) && Objects.equals(lastName, emailDTO.lastName) && Objects.equals(cep, emailDTO.cep) && Objects.equals(subject, emailDTO.subject) && Objects.equals(text, emailDTO.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, firstName, lastName, cep, subject, text);
    }
}
