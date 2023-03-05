package com.zerobase.cms.application;

import com.zerobase.cms.client.MailgunClient;
import com.zerobase.cms.client.mailgun.SendMailForm;
import com.zerobase.cms.domain.SignUpForm;
import com.zerobase.cms.domain.model.Customer;
import com.zerobase.cms.domain.model.Seller;
import com.zerobase.cms.exception.ErrorCode;
import com.zerobase.cms.service.customer.SignUpCustomerService;
import com.zerobase.cms.service.seller.SellerService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SignUpApplication {
    private final MailgunClient mailgunClient;
    private final SignUpCustomerService signUpCustomerService;
    private final SellerService sellerService;

    public void customerVerify(String email, String code){
        signUpCustomerService.verifyEmail(email, code);
    }
    public String customerSignUp(SignUpForm form){
        if(signUpCustomerService.isEmailExist(form.getEmail())){
            throw new ClassCastException(ErrorCode.ALREADY_REGISTER_ACCOUNT.getDetail());
        }
        else{
            Customer customer = signUpCustomerService.signUp(form);
            LocalDateTime now = LocalDateTime.now();

            String code = getRandomCode();
            SendMailForm sendMailForm = SendMailForm.builder().from("test@test111.com")
                    .to(form.getEmail())
                    .subject("subject")
                    .text(getVerificationEmailBody(form.getEmail(),form.getName(),"customer",code))
                    .build();
            mailgunClient.sendEmail(sendMailForm);
            return "회원가입 성공";
        }
    }

    public void sellerVerify(String email, String code){
        sellerService.verifyEmail(email, code);
    }

    public String sellerSignUp(SignUpForm form){
        if(sellerService.isEmailExist(form.getEmail())){
            throw new ClassCastException(ErrorCode.ALREADY_REGISTER_ACCOUNT.getDetail());
        }
        else{
            Seller seller = sellerService.signUp(form);
            LocalDateTime now = LocalDateTime.now();

            String code = getRandomCode();
            SendMailForm sendMailForm = SendMailForm.builder().from("test@test111.com")
                    .to(form.getEmail())
                    .subject("subject")
                    .text(getVerificationEmailBody(form.getEmail(),form.getName(),"seller",code))
                    .build();
            mailgunClient.sendEmail(sendMailForm);
            return "회원가입 성공";
        }
    }
    private String getRandomCode(){
        return RandomStringUtils.random(10, true, true);
    }

    private String getVerificationEmailBody(String email, String name, String type, String code){
        StringBuilder sb = new StringBuilder();
        return sb.append("zzz").append(name).append("click link\n")
                .append("http://localhost:8081/signup/"+type+"/verify/?email=")
                .append(email)
                .append("&code=")
                .append(code).toString();
    }

}
