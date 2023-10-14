package com.cbim.epc.supply.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
        return http.formLogin()
                .loginPage("/login.html") // 表单登录
                .loginProcessingUrl("/user/login") // 设置哪个是登录的 url。
                .defaultSuccessUrl("/log.html") // 登录成功之后跳转到哪个 url
                .failureForwardUrl("/base/log/error").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/base/log/get/*")
                .authenticated()
                .anyRequest()
                .permitAll()
                .and().csrf().disable().build();
    }

        /*
     * 注入BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
//
//    public static void main(String[] args) {
//        String encode = new BCryptPasswordEncoder().encode("epc@2013");
//        System.out.println(encode);
//    }
}
