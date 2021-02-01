package com.example.restful_sample.xample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    // GET
    // /hello-world -> (endpoint)
    // @RequestMapping(method = RequestMethod.GET, path="/hello-world") 와 같음
    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "hello-world";
    }

    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("hello-world");
    }

    @GetMapping(path = "/hello-world-bean/path-variable/{name}")
    public HelloWorldBean helloWorldBean(@PathVariable String name) {
        return new HelloWorldBean(String.format("Hello World, Path! %s", name));
    }
}
