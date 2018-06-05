package com.xxx.rpc.sample.server;

import com.xxx.rpc.sample.api.HelloService;
import com.xxx.rpc.sample.api.model.Person;
import com.xxx.rpc.server.RpcService;

@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService {

    public String hello(String name) {
        return "Hello! " + name;
    }

    public String hello(Person person) {
        return "Hello! " + person.getFirstName() + " " + person.getLastName();
    }
}
