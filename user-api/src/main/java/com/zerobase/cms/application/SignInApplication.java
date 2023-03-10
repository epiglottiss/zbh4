package com.zerobase.cms.application;

import com.zerobase.cms.domain.SignInForm;
import com.zerobase.cms.domain.model.Customer;
import com.zerobase.cms.domain.model.Seller;
import com.zerobase.cms.exception.CustomException;
import com.zerobase.cms.exception.ErrorCode;
import com.zerobase.cms.service.customer.CustomerService;
import com.zerobase.cms.service.seller.SellerService;
import com.zerobase.domain.common.UserType;
import com.zerobase.domain.config.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInApplication {
    private final CustomerService customerService;
    private final JwtAuthenticationProvider provider;
    private final SellerService sellerService;

    public String customerLoginToken(SignInForm form){
        Customer customer = customerService.findValidCustomer(form.getEmail(), form.getPassword())
                .orElseThrow(()->new CustomException(ErrorCode.LOGIN_CHECK_FAIL));


        return provider.createToken(customer.getEmail(),customer.getId(), UserType.CUSTOMER);
    }

    public String sellerLoginToken(SignInForm form){
        Seller seller = sellerService.findValidSeller(form.getEmail(), form.getPassword())
                .orElseThrow(()->new CustomException(ErrorCode.LOGIN_CHECK_FAIL));

        return provider.createToken(seller.getEmail(),seller.getId(), UserType.CUSTOMER);
    }
}
