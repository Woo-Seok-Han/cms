package com.waffle.user.application;

import com.waffle.user.client.mailgun.MailgunClient;
import com.waffle.user.client.mailgun.SendMailForm;
import com.waffle.user.domain.model.Customer;
import com.waffle.user.domain.model.Seller;
import com.waffle.user.domain.model.SignUpForm;
import com.waffle.user.exception.CustomException;
import com.waffle.user.exception.ErrorCode;
import com.waffle.user.service.cusotmer.SignUpCustomerService;
import com.waffle.user.service.seller.SellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SignUpApplication {

    private final MailgunClient mailgunClient;
    private final SignUpCustomerService signUpCustomerService;
    private final SellerService sellerService;

    public void customerVerify(String email, String code) {
        signUpCustomerService.verifyEmail(email, code);
    }

    public void sellerVerify(String email, String code) {
        sellerService.verifyEmail(email, code);
    }

    public String customerSignUp(SignUpForm signUpForm) {
        if (signUpCustomerService.isEmailExist(signUpForm.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
        } else {
            Customer c = signUpCustomerService.signUp(signUpForm);
            String code = getRandomCode();

            SendMailForm verficationEmail = SendMailForm.builder()
                .from("woosuk1893@naver.com")
                .to("woosuk1893@naver.com")
                .subject("Verfication Email")
                .text(getVerificationEmailBody(c.getEmail(), c.getName(), "customer", code))
                .build();

            log.info("Send email result : " + mailgunClient.sendEmail(verficationEmail));
            signUpCustomerService.changeCustomerValidateEmail(c.getId(), code);
            return "회원 가입에 성공 하였습니다.";
        }
    }

    public String sellerSignUp(SignUpForm signUpForm) {
        if (sellerService.isEmailExist(signUpForm.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
        } else {
            Seller seller = sellerService.signUp(signUpForm);
            String code = getRandomCode();

            SendMailForm verficationEmail = SendMailForm.builder()
                .from("woosuk1893@naver.com")
                .to("woosuk1893@naver.com")
                .subject("Verfication Email")
                .text(getVerificationEmailBody(seller.getEmail(), seller.getName(), "seller", code))
                .build();

            log.info("Send email result : " + mailgunClient.sendEmail(verficationEmail));
            sellerService.changeSellerValidateEmail(seller.getId(), code);
            return "회원 가입에 성공 하였습니다.";
        }
    }

    // 랜덤 코드 생성
    private String getRandomCode() {
        return RandomStringUtils.random(10, true, true);
    }

    private String getVerificationEmailBody(String email, String name, String type, String code) {
        StringBuilder builder = new StringBuilder();
        return builder.append("Hello")
            .append(name)
            .append("Please Click Link for verification.\n\n")
            .append("http://localhost:8081/signup/verify/" + type + "?email=")
            .append(email)
            .append("&code=")
            .append(code)
            .toString();

    }

}
