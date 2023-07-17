package com.waffle.user.service.cusotmer;

import com.waffle.user.domain.customer.ChangeBalanceForm;
import com.waffle.user.domain.model.CustomerBalanceHistory;
import com.waffle.user.domain.repository.CustomerBalanceHistoryRepository;
import com.waffle.user.domain.repository.CustomerRepository;
import com.waffle.user.exception.CustomException;
import com.waffle.user.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerBalanceHistoryService {

    private final CustomerBalanceHistoryRepository customerBalanceHistoryRepository;
    private final CustomerRepository customerRepository;

    @Transactional(noRollbackFor = {CustomException.class})
    public CustomerBalanceHistory changeBalance(Long customerId, ChangeBalanceForm form) {
        CustomerBalanceHistory customerBalanceHistory = customerBalanceHistoryRepository.findFirstByCustomer_IdOrderByIdDesc(
                customerId)
            .orElse(CustomerBalanceHistory.builder()
                .changeMoney(0)
                .currentMoney(0)
                .customer(customerRepository.findById(customerId)
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER)))
                .build());

        if(customerBalanceHistory.getCurrentMoney() + form.getMoney() < 0) {
            throw new CustomException(ErrorCode.NOT_ENOUGH_BALANCE);
        }

        customerBalanceHistory = CustomerBalanceHistory.builder()
            .changeMoney(form.getMoney())
            .currentMoney(customerBalanceHistory.getCurrentMoney() + form.getMoney())
            .description(form.getMessage())
            .fromMessage(form.getFrom())
            .customer(customerBalanceHistory.getCustomer())
            .build();

        customerBalanceHistory.getCustomer().setBalance(customerBalanceHistory.getCurrentMoney());

        return customerBalanceHistoryRepository.save(customerBalanceHistory);
    }

}
