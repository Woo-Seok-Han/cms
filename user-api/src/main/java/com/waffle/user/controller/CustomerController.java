package com.waffle.user.controller;

import com.waffle.domain.common.UserVo;
import com.waffle.domain.config.JwtAuthenticationProvider;
import com.waffle.user.domain.customer.ChangeBalanceForm;
import com.waffle.user.domain.customer.CustomerDto;
import com.waffle.user.domain.model.Customer;
import com.waffle.user.domain.model.CustomerBalanceHistory;
import com.waffle.user.exception.CustomException;
import com.waffle.user.exception.ErrorCode;
import com.waffle.user.service.cusotmer.CustomerBalanceHistoryService;
import com.waffle.user.service.cusotmer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerBalanceHistoryService customerBalanceHistoryService;
    private final JwtAuthenticationProvider provider;

    @GetMapping("/getInfo")
    public ResponseEntity<CustomerDto> getInfo(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
        UserVo userVo = provider.getUserVo(token);
        Customer customer = customerService.findByIdAndEmail(userVo.getId(), userVo.getEmail())
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        return ResponseEntity.ok(CustomerDto.from(customer));
    }

    @PostMapping("/balance")
    public ResponseEntity<Integer> changeBalance(@RequestHeader(name = "X-AUTH-TOKEN") String token,
        @RequestBody ChangeBalanceForm form) {

        UserVo userVo = provider.getUserVo(token);

        return ResponseEntity.ok(customerBalanceHistoryService.changeBalance(userVo.getId(), form).getCurrentMoney());
    }
}
