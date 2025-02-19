package nca;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

   
    public String getUserEmail() {
        // Spring SecurityのContextから現在のユーザーを取得
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // ユーザーがログインしていない場合は例外を投げる
        if (authentication == null || authentication.getPrincipal() instanceof String) {
            throw new RuntimeException("ユーザーがログインしていません");
        }
        
        // ユーザーのメールアドレスを返す
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();  // Spring Securityで設定されているメールアドレス（username）
    }
}
