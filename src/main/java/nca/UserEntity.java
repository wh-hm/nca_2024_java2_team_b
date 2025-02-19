package nca;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="customer_mst")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
	
	@Id
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;

	@Column(name="name")
	private String name;
	
	@Column(name="kana")
	private String kana;
	
	@Column(name="tel")
	private String tel;
	
	@Column(name="zip")
	private String zip;
	
	@Column(name="address")
	private String address;
	
	
	
}
