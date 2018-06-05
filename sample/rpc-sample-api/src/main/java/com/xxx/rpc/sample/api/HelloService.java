package com.xxx.rpc.sample.api;

import com.xxx.rpc.sample.api.model.Person;

public interface HelloService {

    String hello(String name);

    String hello(Person person);
}
