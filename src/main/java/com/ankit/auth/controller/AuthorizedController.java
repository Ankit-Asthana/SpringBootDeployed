package com.ankit.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/thor/")
public class AuthorizedController {

    @GetMapping
    public ResponseEntity authorizedCheck() {
        return ResponseEntity.ok("Accessed Authorized endpoint");
    }
}
