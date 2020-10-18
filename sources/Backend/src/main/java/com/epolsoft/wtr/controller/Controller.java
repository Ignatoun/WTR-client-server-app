package com.epolsoft.wtr.controller;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class Controller {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "Hello sir! Would you like right or left TWIX stick?";
    }

}
