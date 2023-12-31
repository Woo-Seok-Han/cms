package com.waffle.user.application;

import com.waffle.domain.common.UserType;
import com.waffle.domain.config.JwtAuthenticationProvider;
import com.waffle.user.domain.model.Customer;
import com.waffle.user.domain.model.Seller;
import com.waffle.user.domain.model.SignInForm;
import com.waffle.user.exception.CustomException;
import com.waffle.user.exception.ErrorCode;
import com.waffle.user.service.cusotmer.CustomerService;
import com.waffle.user.service.seller.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SignInApplication {

    private final CustomerService customerService;
    private final SellerService sellerService;
    private final JwtAuthenticationProvider provider;
    public String customerLoginToken(final SignInForm form) {
        // 1. 로그인 가능 여부
        Customer c = customerService.findValidCustomer(form.getEmail(), form.getPassword())
            .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_CHECK_FAIL));
        // 2. 토큰을 발행


        // 3. 토큰을 response
        return provider.createToken(c.getEmail(), c.getId(), UserType.CUSTOMER);
    }

    public String sellerLoginToken(final SignInForm form) {
        // 1. 로그인 가능 여부
        Seller seller = sellerService.findValidSeller(form.getEmail(), form.getPassword())
            .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_CHECK_FAIL));
        // 2. 토큰을 발행


        // 3. 토큰을 response
        return provider.createToken(seller.getEmail(), seller.getId(), UserType.SELLER);
    }
}
