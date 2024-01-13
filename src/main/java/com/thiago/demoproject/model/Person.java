package com.thiago.demoproject.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Person {

    public static final String AMERICA_SAO_PAULO = "America/Sao_Paulo";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String gender;
    @Column(nullable = false)
    private String cep;
    private LocalDateTime creationDate = LocalDateTime.now().atZone(ZoneId.of(AMERICA_SAO_PAULO)).toLocalDateTime();

    public Person() {
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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        if(cep != null && cep.isEmpty()){
            this.cep = null;
        } else {
            this.cep = cep;
        }
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(email, person.email) && Objects.equals(gender, person.gender) && Objects.equals(cep, person.cep) && Objects.equals(creationDate, person.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, gender, cep, creationDate);
    }
}
