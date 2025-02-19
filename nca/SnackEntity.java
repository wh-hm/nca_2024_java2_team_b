package nca;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="goods_mst")
@Data
@NoArgsConstructor
@AllArgsConstructor
/*お菓子のEntity*/
public class SnackEntity {

	//Table名を後で確認してね
	
	@Id
	@Column(name="snackid")
	private String snackid;
	
	@Column(name="snackname")
	private String snackname;
	
	@Column(name="price")
	private String price;
	
	
	@Column(name="category")
	private String category;
	
	@Column(name="company")
	private String company;
	
	
	
	
	@Column(name="stock")
	private String stock;
	
	@Column(name="picture")
	private String picture;
}
