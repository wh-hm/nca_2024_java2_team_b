package nca;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
	//userDetailsService
	
	
	
	
	@Bean
	PasswordEncoder passwordEncoder() {
		// BCryptPasswordEncoderを使用してパスワードをエンコード
		return new BCryptPasswordEncoder();
	}
	
	
	/*
	@SuppressWarnings("deprecation")
	@Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();  // パスワードをそのまま比較
    }
    */
	
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                                // 静的リソースへのアクセスを許可
                                // CSS、JS、画像などの静的リソースにアクセスを許可
                                .requestMatchers("/assets/**").permitAll()
                                // ログインページのアクセスを許可
                                .requestMatchers("/user/login").permitAll()
                                .requestMatchers("/user/login_please").permitAll()
                                .requestMatchers("/user/login/nologin").permitAll()
                                .requestMatchers("/user/login/add").permitAll()
                                .requestMatchers("/user/add_action").permitAll()
                               
                                .requestMatchers("/snack/list").permitAll()
                                .requestMatchers("/snack/search").permitAll()
                                .requestMatchers("/snack/snack_item/*").permitAll()
                                .requestMatchers("/snack/snack_list/**").permitAll()
                                .requestMatchers("/snack/snack_item/0505").permitAll()
                                .requestMatchers("/snack/item").permitAll()
                                // その他のリクエストは認証を要求
                                .anyRequest().authenticated()

                )
                .formLogin((form) -> form
                        // カスタムログインページ
                        .loginPage("/user/login")
                        // 認証処理を行うURL
                        .loginProcessingUrl("/perform_login")
                        // 認証成功後のリダイレクト先
                        .defaultSuccessUrl("/snack/list", true)
                        // 認証失敗時のリダイレクト先
                        .failureUrl("/user/login?error=true")
                        .permitAll())
                .logout((logout) -> logout
                		
                        // ログアウト処理のURL
                        .logoutUrl("/user/logout")
                        // ログアウト成功後のリダイレクト先
                        .logoutSuccessUrl("/user/login")
                        // セッションを無効化
                        .invalidateHttpSession(true)
                        .clearAuthentication(true) 
                        // クッキーを削除
                        .deleteCookies("JSESSIONID")
                        .permitAll())

                .exceptionHandling(handling -> handling
                        // 認証が必要なページへのアクセスで認証がされていない場合は、ログインページにリダイレクト
                        .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/user/login_please")));
		
				
		return http.build();
	}
	
	
	
	
    
    
	
}
