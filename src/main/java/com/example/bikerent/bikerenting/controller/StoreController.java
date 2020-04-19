package com.example.bikerent.bikerenting.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/store")
public class StoreController {
    @GetMapping("/all")
    public String store(){
        return  "store";
    }

    @GetMapping("/admin")
    public String admin(){
        return ("<h1>Admin </h1>");
    }

    @GetMapping("/user")
    public String user(){
        return ("<h1>User</h1>");
    }
}
