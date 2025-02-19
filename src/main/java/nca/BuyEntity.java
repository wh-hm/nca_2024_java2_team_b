package nca;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="buy_mst")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyEntity {
	
	
	
	
	@Column(name="pay")
	private String pay;

	@Column(name="favorite")
	private String favorite;
	
	@Column(name="history")
	private String history;
	
	@Column(name="cart")
	private String cart;
	
	@Id
	@Column(name="email")
	private String email;
	
	@Column(name="reading")
	private String reading;
	
	
	
}
