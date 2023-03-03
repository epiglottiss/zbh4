package com.zerobase.cms.user.service;

import com.zerobase.cms.user.domain.SignUpForm;
import com.zerobase.cms.user.domain.model.Customer;
import com.zerobase.cms.user.domain.repository.CustomerRepository;
import com.zerobase.cms.user.exception.CustomException;
import com.zerobase.cms.user.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class SignUpCustomerService {
    private final CustomerRepository customerRepository;

    public Customer signUp(SignUpForm form){
        return customerRepository.save(Customer.from(form));
    }

    public boolean isEmailExist(String email){
        return customerRepository.findByEmail(email.toLowerCase(Locale.ROOT)).isPresent();
    }

    public void verifyEmail(String email, String code){
        Customer customer = customerRepository.findByEmail(email).orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));
        if(customer.isVerify()){
            throw new CustomException(ErrorCode.ALREADY_VERIFY);
        }
        else if(!customer.getVerificationCode().equals(code)){
            throw new CustomException(ErrorCode.VERIFICATION_CODE_UNMATCH);
        }
        else if(customer.getVerifyExpiredAt().isBefore(LocalDateTime.now())){
            throw new CustomException(ErrorCode.EXPIRED_VERIFICATION_CODE);
        }
        customer.allowVerification();
    }

    @Transactional
    public LocalDateTime changeCustomerValidation(Long customerId, String verificationCode){
        Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new CustomException(ErrorCode.USER_NOT_FOUND));

        customer.issueVerificationCode(verificationCode);
        return customer.getVerifyExpiredAt();
    }
}
