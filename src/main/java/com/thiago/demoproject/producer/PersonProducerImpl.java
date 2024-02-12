package com.thiago.demoproject.producer;

import com.thiago.demoproject.model.Person;
import com.thiago.demoproject.producer.dto.EmailDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PersonProducerImpl implements PersonProducer{

    private final RabbitTemplate rabbitTemplate;

    public PersonProducerImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${mq.queues.email-queue}")
    private String routingKey;

    public void publishEmailMessage(Person person){
        var emailDTO = new EmailDTO();
        emailDTO.setId(person.getId());
        emailDTO.setEmail(person.getEmail());
        emailDTO.setFirstName(person.getFirstName());
        emailDTO.setLastName(person.getLastName());
        emailDTO.setCep(person.getCep());
        emailDTO.setSubject("Cadastro de " + person.getFirstName() + " realizado com sucessso");
        emailDTO.setText(person.getFirstName() + " " + person.getLastName() + ", seja bem vindo!\n" + "Seu cadastro foi realizado com sucesso.");

        rabbitTemplate.convertAndSend(routingKey, emailDTO);

    }
}
