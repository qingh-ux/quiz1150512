package com.example.quiz1150512;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			// 1. 關閉跨站請求偽造防護 (測 API 必須關閉)
			.csrf(csrf -> csrf.disable())
			
			// 2. 設定網址權限
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/api/**").permitAll() // ✨ 所有 /api/ 開頭的網址通通放行！
				.anyRequest().permitAll()               // 其他請求也都放行
			);

		return http.build();
	}
}