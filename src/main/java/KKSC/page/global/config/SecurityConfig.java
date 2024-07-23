package KKSC.page.global.config;

import KKSC.page.domain.member.repository.MemberRepository;
import KKSC.page.domain.member.service.impl.MemberDetailsService;
import KKSC.page.global.auth.*;
import KKSC.page.global.auth.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberDetailsService memberDetailsService;
    private final MemberRepository memberRepository;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // csrf 차단
                .csrf(AbstractHttpConfigurer::disable)

                // cors 설정
                .cors(cors -> cors.configurationSource(configurationSource()))

                // 시큐리티 기본 로그인 비활성화
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                // iframe 차단
                .headers(header -> header.frameOptions(
                        HeadersConfigurer.FrameOptionsConfig::sameOrigin
                ))

                // session 사용 중지
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 권한 url 설정
                .authorizeHttpRequests(req -> req.
                        requestMatchers("/").permitAll().
                        requestMatchers(HttpMethod.POST, "/member/").permitAll().
                        requestMatchers(HttpMethod.GET, "/notice/list", "/notice/search").permitAll().
                        requestMatchers(HttpMethod.GET, "/notice/").permitAll().
                        requestMatchers("/swagger-ui/**").permitAll().
                        requestMatchers("/v3/api-docs/**").permitAll().
                        anyRequest().authenticated())

                // logout 설정
                .logout(logout -> logout.
                        logoutSuccessUrl("/").
                        invalidateHttpSession(true))

                // Jwt 관련 설정
                .exceptionHandling(req -> req.authenticationEntryPoint(jwtAuthenticationEntryPoint()))
                .addFilterAfter(jsonUsernamePasswordAuthenticationFilter(), LogoutFilter.class)
                .addFilterBefore(jwtAuthenticationProcessingFilter(), JsonUsernamePasswordAuthenticationFilter.class)

                .build();
    }

    @Bean
    public CorsConfigurationSource configurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return urlBasedCorsConfigurationSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(memberDetailsService);
        return new ProviderManager(provider);
    }

    @Bean
    public JwtLoginSuccessHandler jwtLoginSuccessHandler() {
        return new JwtLoginSuccessHandler(jwtService, memberRepository);
    }

    @Bean
    public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {
        return new JwtAuthenticationProcessingFilter(jwtService, memberRepository);
    }

    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

    @Bean
    public JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter() throws Exception {
        JsonUsernamePasswordAuthenticationFilter filter = new JsonUsernamePasswordAuthenticationFilter(objectMapper);
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(jwtLoginSuccessHandler());
        filter.setAuthenticationFailureHandler(loginFailureHandler());
        return filter;
    }
}
