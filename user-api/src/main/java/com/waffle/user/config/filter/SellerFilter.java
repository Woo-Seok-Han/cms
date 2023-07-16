package com.waffle.user.config.filter;

import com.waffle.domain.common.UserVo;
import com.waffle.domain.config.JwtAuthenticationProvider;
import com.waffle.user.service.cusotmer.CustomerService;
import com.waffle.user.service.seller.SellerService;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebFilter(urlPatterns = "/seller/*")
@RequiredArgsConstructor
public class SellerFilter implements Filter {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final SellerService sellerService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader("X-AUTH-TOKEN");
        log.info("token string : " + token);

        if(!jwtAuthenticationProvider.validateToken(token)){
            throw new ServletException("Invalid Access");
        }

        UserVo vo = jwtAuthenticationProvider.getUserVo(token);
        sellerService.findByIdAndEmail(vo.getId(), vo.getEmail()).orElseThrow(() -> new SecurityException("Invalid Access"));

        chain.doFilter(request, response);
    }
}
