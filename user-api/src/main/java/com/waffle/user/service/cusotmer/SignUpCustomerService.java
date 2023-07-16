package com.waffle.user.service.cusotmer;

import com.waffle.user.domain.model.SignUpForm;
import com.waffle.user.domain.model.Customer;
import com.waffle.user.domain.repository.CustomerRepository;
import com.waffle.user.exception.CustomException;
import com.waffle.user.exception.ErrorCode;
import java.time.LocalDateTime;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignUpCustomerService {

    private final CustomerRepository customerRepository;

    public Customer signUp(SignUpForm signUpForm) {
        return customerRepository.save(Customer.from(signUpForm));
    }

    public boolean isEmailExist(String email) {
        return customerRepository.findByEmail(email.toLowerCase(Locale.ROOT))
            .isPresent();
    }

    @Transactional
    public void verifyEmail(String email, String code) {
        Customer customer = customerRepository.findByEmail(email)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        if (customer.isVerify()) {
            throw new CustomException(ErrorCode.ALREADY_VERIFIED);
        } else if (!customer.getVerficationCode().equals(code)) {
            throw new CustomException(ErrorCode.WRONG_VERIFICATION);
        } else if (customer.getVerifyExpiredAt().isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.EXPIRE_CODE);
        }

        customer.setVerify(true);
    }

    @Transactional
    public LocalDateTime changeCustomerValidateEmail(Long customerId, String verficationCode) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        customer.setVerficationCode(verficationCode);
        customer.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));
        return customer.getVerifyExpiredAt();
    }
}
