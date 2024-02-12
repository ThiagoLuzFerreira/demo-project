package com.thiago.demoproject.producer;

import com.thiago.demoproject.model.Person;

public interface PersonProducer {

    void publishEmailMessage(Person person);
}
