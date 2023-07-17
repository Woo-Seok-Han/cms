package com.waffle.user.controller;

import com.waffle.domain.common.UserVo;
import com.waffle.domain.config.JwtAuthenticationProvider;
import com.waffle.user.domain.customer.CustomerDto;
import com.waffle.user.domain.model.Customer;
import com.waffle.user.domain.model.Seller;
import com.waffle.user.domain.seller.SellerDto;
import com.waffle.user.exception.CustomException;
import com.waffle.user.exception.ErrorCode;
import com.waffle.user.service.cusotmer.CustomerService;
import com.waffle.user.service.seller.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;
    private final JwtAuthenticationProvider provider;

    @GetMapping("/getInfo")
    public ResponseEntity<SellerDto> getInfo(@RequestHeader(name="X-AUTH-TOKEN") String token){
        UserVo userVo = provider.getUserVo(token);

        Seller seller = sellerService.findByIdAndEmail(userVo.getId(), userVo.getEmail())
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        return ResponseEntity.ok(SellerDto.from(seller));
    }
}
