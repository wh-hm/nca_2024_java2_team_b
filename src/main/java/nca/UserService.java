package nca; // パッケージの宣言


import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service // サービスクラスとしてのアノテーション
public class UserService { // No08UserServiceクラスの宣言

	@Autowired // 自動的に依存関係を注入するアノテーション
	UserRepository repository; // No08UserRepositoryのインスタンス変数
	
	@Autowired // 自動的に依存関係を注入するアノテーション
	BuyRepository buyrepository; // No08UserRepositoryのインスタンス変数
	
	
	@Autowired
    private SecurityService securityService;

	// 認証メソッドの宣言
	public boolean isAuth(UserForm form) { 
		// findByIdメソッドを実行する
		Optional<UserEntity> optional = repository.findById(form.getEmail()); // メールアドレスでユーザーを検索
		// エンティティが存在するか（DBから情報が取得できた）
		UserEntity entity = new UserEntity(); // 新しいエンティティのインスタンスを作成
		if (optional.isPresent()) { // オプショナルが値を持っているか確認
			// オプショナルからエンティティを取得する
			entity = optional.get(); // オプショナルからエンティティを取得
		}
		if(! form.getPassword().equals(entity.getPassword())) { // パスワードが一致しない場合
			return false; // 認証失敗
		}
		return true; // 認証成功
	}

	
	public UserForm save(UserForm form) {
		String email = securityService.getUserEmail();
        UserEntity entity = new UserEntity(); // 新しいエンティティのインスタンスを作成
		
        
        
		repository.deleteById(email);
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		entity.setEmail(form.getEmail());
		entity.setKana(form.getKana());
		entity.setName(form.getName());
		entity.setAddress(form.getAddress());
		entity.setTel(form.getTel());
		entity.setZip(form.getZip());
		String encodedPassword = passwordEncoder.encode(form.getPassword());
		entity.setPassword(encodedPassword);
		repository.save(entity);
		
		List<BuyEntity> buylist = buyrepository.findByEmail(email);
		BuyEntity buyentity = buylist.get(0);
		
		buyrepository.deleteById(email);
		buyentity.setEmail(form.getEmail());
		buyrepository.save(buyentity);
		
	
		
		 // 新しいUserFormオブジェクトを作成
	    UserForm userform = new UserForm();

	    // DBから取得したユーザー情報をFormにコピーする
	    BeanUtils.copyProperties(entity, userform);

	    // フォームを返す
	    return userform;
        
	}
	
	public UserForm user_info(String email) {
		email = getUserEmail();
		
		Optional<UserEntity> optional = repository.findById(email); // メールアドレスでユーザーを検索
		// エンティティが存在するか（DBから情報が取得できた）
		UserEntity entity = new UserEntity(); // 新しいエンティティのインスタンスを作成
		if (optional.isPresent()) { // オプショナルが値を持っているか確認
			// オプショナルからエンティティを取得する
			entity = optional.get(); // オプショナルからエンティティを取得
		}
	   
	    // 新しいUserFormオブジェクトを作成
	    UserForm form = new UserForm();

	    // DBから取得したユーザー情報をFormにコピーする
	    BeanUtils.copyProperties(entity, form);

	    // フォームを返す
	    return form;
	}
	

	public List<UserEntity> getAllUser() {
		return repository.findAll();
	}
	
	public boolean user() {
		String email = securityService.getUserEmail();
		boolean login_check = true;
		if (email == null || email.equals("")) {
			login_check = false;
		}
		
		return login_check;
	}
	
	public String getUserEmail() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
	        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	        return userDetails.getUsername();  // 通常、メールアドレスはユーザー名として保存されることが多い
	    }
	    return null;
	}

	public UserForm user_info() {
		String email = securityService.getUserEmail();
		Optional<UserEntity> optional = repository.findById(email); // メールアドレスでユーザーを検索
		// エンティティが存在するか（DBから情報が取得できた）
		UserEntity entity = new UserEntity(); // 新しいエンティティのインスタンスを作成
		if (optional.isPresent()) { // オプショナルが値を持っているか確認
			// オプショナルからエンティティを取得する
			entity = optional.get(); // オプショナルからエンティティを取得
		}
	   
	    // 新しいUserFormオブジェクトを作成
	    UserForm form = new UserForm();

	    // DBから取得したユーザー情報をFormにコピーする
	    BeanUtils.copyProperties(entity, form);

	    // フォームを返す
	    return form;
	}
	
	public UserForm user_save(UserForm form) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		UserEntity entity = new UserEntity(); // 新しいエンティティのインスタンスを作成
		
			entity.setEmail(form.getEmail());
			entity.setKana(form.getKana());
			entity.setName(form.getName());
			entity.setAddress(form.getAddress());
			entity.setTel(form.getTel());
			entity.setZip(form.getZip());
			String encodedPassword = passwordEncoder.encode(form.getPassword());
			entity.setPassword(encodedPassword);
			repository.save(entity);
			
		
			BuyEntity buyentity = new BuyEntity();
			buyentity.setEmail(form.getEmail());
			buyentity.setCart("");
			buyentity.setFavorite("");
			buyentity.setHistory("");
			buyentity.setPay("");
			buyentity.setReading("");
			buyrepository.save(buyentity);
			UserForm userform = new UserForm();

		    // DBから取得したユーザー情報をFormにコピーする
		    BeanUtils.copyProperties(entity, userform);

		
		return userform;
		
	}
	
	public UserForm buy_save_user(UserForm form) {
		String email = securityService.getUserEmail();
		Optional<UserEntity> optional = repository.findById(email); // メールアドレスでユーザーを検索
		// エンティティが存在するか（DBから情報が取得できた）
		UserEntity entity = new UserEntity(); // 新しいエンティティのインスタンスを作成
		if (optional.isPresent()) { // オプショナルが値を持っているか確認
			// オプショナルからエンティティを取得する
			entity = optional.get(); // オプショナルからエンティティを取得
			
			entity.setKana(form.getKana());
			entity.setName(form.getName());
			entity.setAddress(form.getAddress());
			entity.setTel(form.getTel());
			entity.setZip(form.getZip());
			
			repository.save(entity);

		}
		
		// 新しいUserFormオブジェクトを作成
	    UserForm userform = new UserForm();

	    // DBから取得したユーザー情報をFormにコピーする
	    BeanUtils.copyProperties(entity, userform);

	    // フォームを返す
	    return userform;
		
	}

	
	

}
