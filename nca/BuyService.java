package nca; // パッケージの宣言


import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired; // Autowiredアノテーションのインポート
import org.springframework.stereotype.Service; // Serviceアノテーションのインポート

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service // サービスクラスとしてのアノテーション
public class BuyService { // No08UserServiceクラスの宣言

	@Autowired // 自動的に依存関係を注入するアノテーション
	BuyRepository repository; // No08UserRepositoryのインスタンス変数
	
	@Autowired // 自動的に依存関係を注入するアノテーション
	SnackRepository snackrepository;
	
	@Autowired
    private SecurityService securityService;

	
	
	public void data_true() {
		String email = securityService.getUserEmail();
		List<BuyEntity> entityList = repository.findByEmail(email);
		if(entityList.isEmpty()){
			BuyEntity newEntity = new BuyEntity();
	        newEntity.setEmail(email);
	        newEntity.setPay("不明");
	        newEntity.setCart("");
	        newEntity.setFavorite("");
	        newEntity.setHistory("");
	        newEntity.setPay("");
	        newEntity.setReading("");
	       
	        repository.save(newEntity);
		}
	}
	
	
	public String[] favoriteAll(BuyForm where){
		String email = securityService.getUserEmail();
		
		
		List<BuyEntity> entityList = repository.findByEmail(email);
		BuyEntity entity = null;
		String[] favoriteArray = null;
		if(entityList.isEmpty()) {
			entity = null;
		}else {
			entity = entityList.get(0);
			if(entity.getFavorite() == null) {
				return null;
			}
			String favorite = entity.getFavorite();
			favoriteArray = favorite.split(",");
		}
		
		return favoriteArray;
		
	}
	public String[] historyAll(BuyForm form){
		
		String[] list = null;
	   
		
		String email = securityService.getUserEmail();
		   
	    List<BuyEntity> entityList = repository.findByEmail(email);
	    BuyEntity entity = entityList.get(0);
	    String history_list = entity.getReading();
	    if(history_list != null) {
	    	list = history_list.split(",");
	    }
	    
	    
	   
	    
	    return list;
	        
	       
	    
		
	}
	public String[] bagAll(BuyForm where){
		String[] list = null;
	   
		
		String email = securityService.getUserEmail();
		   
	    List<BuyEntity> entityList = repository.findByEmail(email);
	    
	    BuyEntity entity = entityList.get(0);
	    String bag_list = entity.getCart();
	    if(bag_list != null) {
	    	list = bag_list.split(",");
	    }
	    
	    	
	    
		
	    log.info("大丈夫よー"+ Arrays.toString(list));
		
		
		return list;
	}
	
	public void favorite_add(String snackid) {
	    
	    String email = securityService.getUserEmail();
	   
	    List<BuyEntity> entityList = repository.findByEmail(email);

	    if (entityList.isEmpty()) {
	        
	        BuyEntity newEntity = new BuyEntity();
	        newEntity.setEmail(email); 
	        newEntity.setFavorite( snackid + ",");
	        newEntity.setPay("不明");
	        repository.save(newEntity);
	       
	    } else {
	        
	        BuyEntity entity = entityList.get(0);

	       
	        String favorite = entity.getFavorite();
	        if (favorite == null || favorite.isEmpty()) {
	        	favorite = snackid + ",";
	        } else {
	        	if(favorite.indexOf(snackid) != -1) {
	        		if(favorite.split(",")[0].equals(snackid)) {
	        			favorite = favorite.replaceFirst(snackid + ",", "");
	        		}else {
	        			favorite = favorite.replace("," + snackid + ",", ",");
	        		}
	        	}
	        	favorite = snackid + "," + favorite;
	            
	        }
		    int list_num = favorite.split(",").length;
		    
		    if(list_num > 20 ) {
		    	favorite = favorite.replace(favorite.split(",")[20] + ",", "");
		    }
		    
		    
	        
	       

	        entity.setFavorite(favorite);
	        repository.save(entity);
	        
	    }
	    
	}
	
	public void favorite_delete(List<String> favorite){
		
		String email = securityService.getUserEmail();
		   
	    List<BuyEntity> entityList = repository.findByEmail(email);
	    String favorite_list = null;
	   
	    if (!entityList.isEmpty()) {
	    	BuyEntity entity = entityList.get(0);
	    	favorite_list = entity.getFavorite();
	    	for(int a= 0; a < favorite.size(); a ++) {
	    		
	    		favorite_list = favorite_list.replace( favorite.get(a) + ",","");
	    	}

	        entity.setFavorite(favorite_list);
	        repository.save(entity);
	        
	       
	    }
		
	}
	
	public void history_add(String snackid) {
		
		String email = securityService.getUserEmail();
		   
	    List<BuyEntity> entityList = repository.findByEmail(email);
	    BuyEntity entity = entityList.get(0);
	    String history_id = entity.getReading();
	    
	   
	
	    if (history_id == null || history_id.isEmpty()) {
	    	history_id = snackid + ",";
        } else {
        	if(history_id.indexOf(snackid) != -1) {
        		if(history_id.split(",")[0].equals(snackid)) {
        			history_id = history_id.replaceFirst(snackid + ",", "");
        		}else {
        			history_id = history_id.replace("," + snackid + ",", ",");
        		}
        	}
        	history_id = snackid + "," + history_id;
            
        }
	    int list_num = history_id.split(",").length;
	    
	    if(list_num > 20 ) {
	    	history_id = history_id.replace(history_id.split(",")[20] + ",", "");
	    }
	
	    
	    entity.setReading(history_id);
	    repository.save(entity);
	    
	   
	}
	
	public void bag_add(String snackid) {
			
		String email = securityService.getUserEmail();
		   
	    List<BuyEntity> entityList = repository.findByEmail(email);
	
	        
	        BuyEntity entity = entityList.get(0);
	
	       
	        String bag = entity.getCart();
	        
	       
	        
	        if (bag == null || bag.isEmpty()) {
	        	bag = snackid + ",";
	        } else {
	        	if(bag.indexOf(snackid) != -1) {
	        		if(bag.split(",")[0].equals(snackid)) {
	        			bag = bag.replaceFirst(snackid + ",", "");
	        		}else {
	        			bag = bag.replace("," + snackid + ",", ",");
	        		}
	        	}
	        	bag = snackid + "," + bag;
	            
	        }
		    int list_num = bag.split(",").length;
		    
		    if(list_num > 20 ) {
		    	bag = bag.replace(bag.split(",")[20] + ",", "");
		    }
	
	        entity.setCart(bag);
	        repository.save(entity);
	        
	    
	    
	   
	}

	public void bag_delete(List<String> bag){
		
		String email = securityService.getUserEmail();
		   
	    List<BuyEntity> entityList = repository.findByEmail(email);
	    String bag_list = null;
	    
	    if (!entityList.isEmpty()) {
	    	BuyEntity entity = entityList.get(0);
	    	bag_list = entity.getCart();
	    	for(int a= 0; a < bag.size(); a ++) {
	    		
	    		bag_list = bag_list.replace( bag.get(a) + ",","");
	    	}
	
		       
	        
	
	        entity.setCart(bag_list);
	        repository.save(entity);
	        
	       
	    }
		
	}
	
	
	public String[] buy_snackid(){
		String[] list = null;
	   
		
		String email = securityService.getUserEmail();
		   
	    List<BuyEntity> entityList = repository.findByEmail(email);
	    
	    BuyEntity entity = entityList.get(0);
	    String bag_list = entity.getHistory();
	    if(bag_list != null) {
	    	list = bag_list.split(",");
	    }
	    
	    	
	    log.info("大丈夫よー"+ Arrays.toString(list));
		
		
		return list;
	}
	
	public String pay(){
		
		String email = securityService.getUserEmail();
		   
	    List<BuyEntity> entity = repository.findByEmail(email);
	    BuyEntity buyentity = entity.get(0);
	    String payhow = buyentity.getPay();
	    
		return payhow;
	}
	
	public String user_email() {
		String email = securityService.getUserEmail();
		
		return email;
	}
	
	public BuyForm  save_pay(BuyForm form) {
		String email = securityService.getUserEmail();
		List<BuyEntity> entityList = repository.findByEmail(email);
		BuyEntity entity = entityList.get(0);
		String new_pay = form.getPay();
		entity.setPay(new_pay);
		repository.save(entity);
		 BuyForm buyform = new BuyForm();

	    // DBから取得したユーザー情報をFormにコピーする
	    BeanUtils.copyProperties(entity, buyform);
		return buyform;
	}
	
	public String[] buy(String snackid) {
		String[] snack_list = snackid.split("_");
		return snack_list;
	}
	
	public String bag_buy() {
		String email = securityService.getUserEmail();
		List<BuyEntity> entityList = repository.findByEmail(email);
		BuyEntity entity = entityList.get(0);
		String cart = entity.getCart();
		
		cart = cart.replace(",", "_");
		return cart;
	}
	
	public void buy_history_add(String snackid) {
		String[] snacklist = snackid.split("_");
		log.info("nuwa-" + snacklist.length);
		
		String email = securityService.getUserEmail();
		List<BuyEntity> entityList = repository.findByEmail(email);
		BuyEntity entity = entityList.get(0);
		
		String list = "";
		String cart = "";
		for(int a = 0; a < snacklist.length; a ++) {
			List<SnackEntity> true_snack = snackrepository.findBySnackid(snacklist[a]);
			SnackEntity snackentity = true_snack.get(0);
			
			if(Integer.parseInt(snackentity.getStock() ) > 0) {
				list = snackentity.getSnackid() + "," + list;
				
				
				
			}else  {
				cart =  snackentity.getSnackid() + "," + cart;
			}
			
		}
		
		entity.setCart(cart);
		
		String history = list + entity.getHistory();
		
	    int list_num = history.split(",").length;
	    
	    if(list_num > 20 ) {
	    	String result = "";
	    	for(int a = 0; a < list_num; a ++) {
	    		if(a >= 20) {
	    			break;
	    		}
	    		result =  result + history.split(",")[a] + ",";
	    		
	    	}
	    	history = result;
	    }
	    entity.setHistory(history);
		repository.save(entity);
		
		
	}
	

}
