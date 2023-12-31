package com.waffle.user.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;
    private String name;
    private String password;
    private String phone;
    private LocalDate birth;

    private LocalDateTime verifyExpiredAt;
    private String verficationCode;
    private boolean verify;

    @Column(columnDefinition = "int default 0")
    private Integer balance;

    @Builder
    public Customer(Long id, String email, String name, String password, String phone,
        LocalDate birth,
        LocalDateTime verifyExpiredAt,
        String verficationCode) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.birth = birth;
        this.verifyExpiredAt = verifyExpiredAt;
        this.verficationCode = verficationCode;
        this.verify = false;
        this.balance = 0;
    }

    public static Customer from(SignUpForm form) {
        return Customer.builder()
            .email(form.getEmail().toLowerCase(Locale.ROOT))
            .password(form.getPassword())
            .name(form.getName())
            .birth(form.getBirth())
            .phone(form.getPhone())
            .build();
    }

}
