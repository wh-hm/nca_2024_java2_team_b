package nca;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BuyRepository extends JpaRepository<BuyEntity, String>{

	
	//要確認ね
	@Query(name="buy.findByWhere")
	
	
	List<BuyEntity> findByWhere( @Param("pay")String pay,@Param("favorite")String favorite, @Param("history")String history,@Param("cart")String cart,@Param("email")String email,@Param("reading")String reading);
	
	
	List<BuyEntity> findByEmail(@Param("email") String email);

	/*List<UserEntity> findByWhere(String kana, String password, String name, String tel, String email, String zip,
			String address);
	*/


	
}
