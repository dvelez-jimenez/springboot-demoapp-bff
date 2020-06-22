package com.example.demobff.api;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.example.demobff.model.HelloMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RequestMapping("api/v1/hello")
@RestController
public class HelloController {

    @Autowired
    RestTemplate restTemplate;

    @Bean
    public RestTemplate getRestTemplate() { return new RestTemplate(); }

    String backendEndpoint = "http://springboot-demoapp:8080/api/v1/hello";

    @GetMapping
    public String hello(){

        InetAddress inetAddress = null;
        String hostAddress ="";
        String hostName  = "";
        try {
            inetAddress = InetAddress.getLocalHost();


            hostAddress = inetAddress.getHostAddress();

            hostName = inetAddress.getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return "Hello From " + hostName + " ip: " + hostAddress;
    }


    @GetMapping(path = "{target}")
    public String helloTarget(@PathVariable("target") String target){
        if("remote".equalsIgnoreCase(target)){
            HelloMessage helloMessage = restTemplate.getForObject(backendEndpoint, HelloMessage.class);
            return helloMessage.getMessage();
        }
        return hello();
    }


}

