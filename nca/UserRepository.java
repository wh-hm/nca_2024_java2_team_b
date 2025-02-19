package nca;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, String>{

	//@Query(name="zenki.findByWhere")
	//@Query("SELECT u FROM UserEntity u WHERE u.kana LIKE :kana AND u.bloodtype LIKE :bloodtype AND u.address LIKE :address ORDER BY u.kana")
	@Query(name="user.findByWhere")
	List<UserEntity> findByWhere(@Param("kana")String kana, @Param("password")String password,@Param("name")String name, @Param("tel")String tel,@Param("email")String email,@Param("zip")String zip,@Param("address")String address);
	Optional<UserEntity> findById(@Param("email")String email);
	List<UserEntity> findByEmail(@Param("email")String email);
	List<UserEntity> findByEmailStartingWith(String prefix); 
}
