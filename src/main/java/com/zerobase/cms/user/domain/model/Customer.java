package com.zerobase.cms.user.domain.model;

import com.zerobase.cms.user.domain.SignUpForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.AuditOverride;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
public class Customer extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;
    private String name;
    private String password;
    private LocalDate birth;
    private String phone;

    private LocalDateTime verifyExpiredAt;
    private  String verificationCode;
    private boolean isVerify;

    public static Customer from(SignUpForm form){
        return Customer.builder()
                .email(form.getEmail().toLowerCase(Locale.ROOT))
                .password(form.getPassword())
                .name(form.getName())
                .birth(form.getBirth())
                .phone(form.getPhone())
                .build();
    }

    public void issueVerificationCode(String verificationCode){
        this.verificationCode = verificationCode;
        this.verifyExpiredAt = LocalDateTime.now().plusDays(1);
    }
    public void allowVerification(){
        this.isVerify = true;
    }
}

