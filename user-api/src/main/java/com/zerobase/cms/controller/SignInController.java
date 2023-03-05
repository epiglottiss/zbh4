package com.zerobase.cms.controller;

import com.zerobase.cms.application.SignInApplication;
import com.zerobase.cms.domain.SignInForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/sigIn")
public class SignInController {

    private  final SignInApplication signInApplication;
    @PostMapping("/customer")
    public ResponseEntity<String> signInCustomer(@RequestBody SignInForm form){
        return ResponseEntity.ok(signInApplication.customerLoginToken(form));
    }

    @PostMapping("/seller")
    public ResponseEntity<String> signInSeller(@RequestBody SignInForm form){
        return ResponseEntity.ok(signInApplication.sellerLoginToken(form));
    }
}
