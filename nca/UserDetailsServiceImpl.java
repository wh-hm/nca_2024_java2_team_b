package nca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository repository;
	

	// UserDetailsServiceインターフェイスで実装メソッドと設定している
	/*
	public UserDetails loadUserByUsername(String primaryKey) throws UsernameNotFoundException {
		// username（email）でユーザーを検索
		
		UserEntity entity = repository.findById(primaryKey).orElseThrow(() -> new UsernameNotFoundException("認証に失敗しました　：　" + primaryKey));

		// UserDetails を返す。username は email、password はエンティティの password を使用。
		//return User.withUsername(entity.getEmail()).password("{noop}" + entity.getPassword()).roles("USER").build();
		return User.withUsername(entity.getEmail()).password(entity.getPassword()).roles("USER").build();

	}
	*/
	/*
	private PasswordEncoder passwordEncoder() {
		// パスワードのエンコードを無効化する NoOpPasswordEncoder
		return NoOpPasswordEncoder.getInstance();
	}
	*/
	
	
	    
	    @Autowired
	    private PasswordEncoder passwordEncoder;  // PasswordEncoderのインジェクション

	    @Override
	    public UserDetails loadUserByUsername(String primaryKey) throws UsernameNotFoundException {
	        // ユーザー情報を検索
	        UserEntity entity = repository.findById(primaryKey).orElseThrow(() -> 
	            new UsernameNotFoundException("認証に失敗しました： " + primaryKey));

	        // ユーザー情報を返す。エンコードなしのパスワードをそのまま使用
	        return User.withUsername(entity.getEmail())
	                   .password(entity.getPassword()) // パスワードが平文のままでもそのまま返す
	                   .roles("USER")
	                   .build();
	    }
	

	
	

	   

	
	
}




    
    
