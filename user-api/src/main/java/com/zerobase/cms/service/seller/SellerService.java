package com.zerobase.cms.service.seller;

import com.zerobase.cms.domain.SignUpForm;
import com.zerobase.cms.domain.model.Customer;
import com.zerobase.cms.domain.model.Seller;
import com.zerobase.cms.domain.repository.SellerRepository;
import com.zerobase.cms.exception.CustomException;
import com.zerobase.cms.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellerRepository sellerRepository;

    public Optional<Seller> findByIdAndEmail(Long id, String email){
        return sellerRepository.findById(id).stream().filter(seller -> seller.getEmail().equals(email)).findFirst();
    }

    public Optional<Seller> findValidSeller(String email, String password){
        return sellerRepository.findByEmailAndPasswordAndIsVerifyIsTrue(email,password);
    }

    public Seller signUp(SignUpForm form){
        return sellerRepository.save(Seller.from(form));
    }

    public boolean isEmailExist(String email){
        return sellerRepository.findByEmail(email).isPresent();
    }

    @Transactional
    public void verifyEmail(String email, String code){
        Seller seller = sellerRepository.findByEmail(email).orElseThrow(()-> new CustomException(ErrorCode.USER_NOT_FOUND));

        if(seller.isVerify()){
            throw new CustomException(ErrorCode.ALREADY_VERIFY);
        }
        else if(!seller.getVerificationCode().equals(code)){
            throw new CustomException(ErrorCode.VERIFICATION_CODE_UNMATCH);
        }
        else if(seller.getVerifyExpiredAt().isBefore(LocalDateTime.now())){
            throw new CustomException(ErrorCode.EXPIRED_VERIFICATION_CODE);
        }
        seller.allowVerification();

    }

    @Transactional
    public LocalDateTime changeCustomerValidation(Long customerId, String verificationCode){
        Seller seller = sellerRepository.findById(customerId).orElseThrow(()-> new CustomException(ErrorCode.USER_NOT_FOUND));

        seller.issueVerificationCode(verificationCode);
        return seller.getVerifyExpiredAt();
    }

}
