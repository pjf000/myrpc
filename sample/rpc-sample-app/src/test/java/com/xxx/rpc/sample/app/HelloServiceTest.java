package com.xxx.rpc.sample.app;

import com.xxx.rpc.client.RpcProxy;
import com.xxx.rpc.sample.api.HelloService;
import com.xxx.rpc.sample.api.UserService;
import com.xxx.rpc.sample.api.model.Person;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class HelloServiceTest {

    @Autowired
    private RpcProxy helloServiceProxy;
    @Autowired
    private RpcProxy userServiceProxy;

    @Test
    public void helloTest1() {
        HelloService helloService = helloServiceProxy.create(HelloService.class);
        String result = helloService.hello("hshshshshsh");
        System.out.println("get response:"+result);
    }

    @Test
    public void helloTest2() {
        HelloService helloService = helloServiceProxy.create(HelloService.class);
        String result = helloService.hello(new Person("ssss", "bbbb",10));
        Assert.assertEquals("sasfd! sa sfd", result);
    }

    @Test
    public void userTest1(){
        UserService userService = userServiceProxy.create(UserService.class);
        System.out.println(userService.getPersonAge());
    }
}
