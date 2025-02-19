package nca;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SnackRepository extends JpaRepository<SnackEntity, String>{

	
	//要確認ね
	@Query(name="snack.findByWhere")
	
	
	List<SnackEntity> findByWhere(@Param("snackid")String snackid, @Param("snackname")String snackname,@Param("price")String price, @Param("category")String category,@Param("company")String company,@Param("stock")String stock,@Param("picture")String picture);

	List<SnackEntity> findByCategory(@Param("category")String category);
	
	List<SnackEntity> findBySnackid(@Param("snackid")String snackid);
	
	
	@Query("SELECT s FROM SnackEntity s WHERE s.category LIKE %:category% OR s.snackname LIKE %:snackname%")
	List<SnackEntity> findByCategoryOrSnackname(@Param("category")String category,@Param("snackname")String snackname);

	List<SnackEntity> findByCategoryContainingOrSnacknameContaining(String category,String snackname);
	
		
}
