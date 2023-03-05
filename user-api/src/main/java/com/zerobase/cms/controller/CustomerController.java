package com.zerobase.cms.controller;

import com.zerobase.cms.domain.customer.ChangeBalanceForm;
import com.zerobase.cms.domain.customer.CustomerDto;
import com.zerobase.cms.domain.model.Customer;
import com.zerobase.cms.exception.CustomException;
import com.zerobase.cms.exception.ErrorCode;
import com.zerobase.cms.service.customer.CustomerBalanceService;
import com.zerobase.cms.service.customer.CustomerService;
import com.zerobase.domain.common.UserVo;
import com.zerobase.domain.config.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private  final JwtAuthenticationProvider provider;
    private final CustomerService customerService;
    private final CustomerBalanceService customerBalanceService;

    @GetMapping("/getInfo")
    public ResponseEntity<CustomerDto> getInfo(@RequestHeader(name = "X-AUTH-TOKEN") String token){
        UserVo vo= provider.getUserVo(token);
        Customer customer = customerService.findByIdAndEmail(vo.getId(), vo.getEmail()).orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));
        return ResponseEntity.ok(new CustomerDto(customer.getId(), customer.getEmail(), customer.getBalance()));
    }

    @PostMapping("/balance")
    public ResponseEntity<Integer> changeBalance(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                 @RequestBody ChangeBalanceForm form){
        UserVo vo = provider.getUserVo(token);

        return ResponseEntity.ok(customerBalanceService.changeBalance(vo.getId(),form).getCurrentMoney());
    }

}
