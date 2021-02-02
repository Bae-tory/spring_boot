package com.example.restful_sample.xample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    @Autowired // 같은 타입의 bean을 자동으로 주입
    private MessageSource messageSource;

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

    @GetMapping(path = "/hello-world-internationalized")
    public String helloWorldInternationalized(@RequestHeader(name = "Accept-Language", required = false/*필수 값이 아니므로 false*/) Locale locale) {

        return messageSource.getMessage("greeting.message", null, locale);
    }
}
