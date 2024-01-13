package com.thiago.demoproject.service;

import com.thiago.demoproject.dto.PersonAddressDTO;
import com.thiago.demoproject.dto.PersonDTO;
import com.thiago.demoproject.exception.DataIntegrityViolationException;
import com.thiago.demoproject.mapper.GenericModelMapper;
import com.thiago.demoproject.model.Person;
import com.thiago.demoproject.repository.PersonRepository;
import com.thiago.demoproject.webclient.AddressFeingClient;
import com.thiago.demoproject.webclient.dto.AddressDTO;
import com.thiago.demoproject.webclient.model.Address;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class PersonService {

    Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    private PersonRepository repository;

    @Autowired
    private AddressFeingClient addressFeingClient;

    public Page<PersonDTO> findAll(Pageable pageable){

        logger.info("Finding all people");
        Page<Person> peoplePage = repository.findAll(pageable);
        return peoplePage.map(p -> GenericModelMapper.parseObject(p, PersonDTO.class));
    }

    public Page<PersonAddressDTO> findPeopleByEmail(String email, Pageable pageable) {

        logger.info("Finding people by email");
        Page<Person> peopleByEmail = repository.findPeopleByEmail(email, pageable);
        List<PersonAddressDTO> personAddressDTOList = new ArrayList<>();
        for (Person p : peopleByEmail.getContent()){
            ResponseEntity<Address> address = addressFeingClient.getAddress(p.getCep());
            personAddressDTOList.add(new PersonAddressDTO(p.getId(), p.getFirstName(), p.getLastName(), p.getEmail(), p.getGender(), new AddressDTO(address.getBody().getCep(), address.getBody().getLogradouro(), address.getBody().getComplemento(), address.getBody().getBairro(), address.getBody().getLocalidade(), address.getBody().getUf())));
        }

        List<PersonAddressDTO> mappedList = personAddressDTOList.stream().map(p -> GenericModelMapper.parseObject(p, PersonAddressDTO.class)).collect(Collectors.toList());
        return new PageImpl<>(mappedList, peopleByEmail.getPageable(), peopleByEmail.getTotalElements());
    }

    @Transactional
    public PersonDTO save(PersonDTO person) {

        logger.info("Saving a person");
        Optional<Person> foundPerson = repository.findByEmail(person.getEmail());
        if(foundPerson.isPresent() && foundPerson.get().getEmail().equals(person.getEmail())){
            throw new DataIntegrityViolationException("Already registred email");
        } else if (person.getCep().isEmpty()) {
            throw new DataIntegrityViolationException("CEP cannot be empty");
        }
        Person savedPerson = repository.save(GenericModelMapper.parseObject(person, Person.class));
        return GenericModelMapper.parseObject(savedPerson, PersonDTO.class);
    }

    public AddressDTO getAddress(String cep){
        Address address = addressFeingClient.getAddress(cep).getBody();
        return GenericModelMapper.parseObject(address, AddressDTO.class);
    }
}
