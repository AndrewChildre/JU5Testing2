package com.ac.JU5Testing2

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api2/hello2")
class helloController {
    @GetMapping()
    fun sayHello() = "This is the hello endpoint"
}