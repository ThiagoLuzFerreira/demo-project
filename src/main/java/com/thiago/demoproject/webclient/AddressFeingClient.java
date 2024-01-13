package com.thiago.demoproject.webclient;


import com.thiago.demoproject.webclient.model.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "cep", url = "https://viacep.com.br", path = "/ws")
public interface AddressFeingClient {

    @GetMapping(value = "/{cep}/json")
    ResponseEntity<Address> getAddress(@PathVariable String cep);
}
