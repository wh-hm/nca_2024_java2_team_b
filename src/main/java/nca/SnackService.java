package nca; // パッケージの宣言


import java.util.ArrayList;
import java.util.List; // Listインターフェースのインポート

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired; // Autowiredアノテーションのインポート
import org.springframework.stereotype.Service; // Serviceアノテーションのインポート

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service // サービスクラスとしてのアノテーション
public class SnackService { // No08UserServiceクラスの宣言

	@Autowired // 自動的に依存関係を注入するアノテーション
	SnackRepository repository; // No08UserRepositoryのインスタンス変数
	@Autowired
	BuyService buyservice;
	@Autowired
    private SecurityService securityService;
	
		
	public List<SnackEntity> getAllSnack() {
		return repository.findAll();
	}
	
	// 引数として検索条件（ここではフォーム）を受け取り、
		// DBから条件にマッチした情報（ユーザーリスト）を受け取りコントローラーに返す
		public ArrayList<SnackForm> category_search(SnackForm where,String category) {

			
			// ユーザーフォームの変数を準備
			SnackForm form = null;
			String category_name = null;
			switch (category) {
		    case "snacks": category_name = "スナック菓子"; 
		    	break;
		    case "gummi": category_name = "グミ"; 
		    	break;
		    case "baked_sweets": category_name = "焼き菓子"; 
		    	break;
		    case "chocolate": category_name = "チョコ"; 
		    	break;
		    case "dairy_products": category_name = "乳製品"; 
		    	break;
		    case "candy": category_name = "キャンディー"; 
		    	break;
		    case "evasion_of_the_law": category_name = "脱法"; 
		    	break;
		    case "educational_sweets": category_name = "知育菓子"; 
		    	break;
		    case "fruit": category_name = "フルーツ菓子"; 
		    	break;
		    case "jelly": category_name = "ゼリー"; 
		    	break;
		    default: category_name = "その他";
		}
			

	        //List<SnackEntity> entityList = repository.findByWhere(snackid, snackname, price, category_name, company, stock, picture);
	        
	        List<SnackEntity> entityList = repository.findByCategory(category_name);
	        log.info("カテゴリー検索: {}", category_name);
	        
	        log.info("カテゴリー検索: ",entityList);
	        
	        // フォームリストに変換
	        ArrayList<SnackForm> formList = new ArrayList<>();
	        for (SnackEntity entity : entityList) {
	            form = new SnackForm();
	            BeanUtils.copyProperties(entity, form);
	            formList.add(form);
	        }
	        log.info("カテゴリー検索: ",formList);
	        return formList;

	
	}
		
		public String title(String title) {

			
			String category_name = null;
			switch (title) {
		    case "snacks": category_name = "スナック菓子"; 
		    	break;
		    case "gummi": category_name = "グミ"; 
		    	break;
		    case "baked_sweets": category_name = "焼き菓子"; 
		    	break;
		    case "chocolate": category_name = "チョコ"; 
		    	break;
		    case "dairy_products": category_name = "乳製品"; 
		    	break;
		    case "candy": category_name = "キャンディー"; 
		    	break;
		    case "evasion_of_the_law": category_name = "脱法"; 
		    	break;
		    case "educational_sweets": category_name = "知育菓子"; 
		    	break;
		    case "fruit": category_name = "フルーツ菓子"; 
		    	break;
		    case "jelly": category_name = "ゼリー"; 
		    	break;
		    default: category_name = "その他";
			}
			return category_name;
		}
		
	public ArrayList<SnackForm> snackid_search(SnackForm where,String snackid) {

			
			// ユーザーフォームの変数を準備
			SnackForm form = null;
			
			
			
			

	        //List<SnackEntity> entityList = repository.findByWhere(snackid, snackname, price, category_name, company, stock, picture);
	        
			List<SnackEntity> entityList = repository.findBySnackid(snackid);
	        // フォームリストに変換
	        ArrayList<SnackForm> formList = new ArrayList<>();
	        for (SnackEntity entity : entityList) {
	            form = new SnackForm();
	            BeanUtils.copyProperties(entity, form);
	            formList.add(form);
	        }
	        try {
	        	String email = securityService.getUserEmail();
	        	buyservice.history_add(snackid);
	        }catch(Exception e){
	        	
	        	return formList;
	        }
	        
	        return formList;

	
	}
	
	public ArrayList<SnackForm> searchAll(String search_itemname) {
		//現時点でキーワードの検索は、カテゴリーと商品名のみ
		
		// ユーザーフォームの変数を準備
		SnackForm form = null;
		
		String category = "%" + search_itemname + "%";
		
		String snackname = "%" + search_itemname + "%";
		
		
		

        //List<SnackEntity> entityList = repository.findByWhere(snackid, snackname, price, category_name, company, stock, picture);
        
		List<SnackEntity> entityList = repository.findByCategoryOrSnackname(category,snackname);
		log.info("検索結果数: {}", entityList.size());
		// フォームリストに変換
        ArrayList<SnackForm> formList = new ArrayList<>();
        for (SnackEntity entity : entityList) {
            form = new SnackForm();
            BeanUtils.copyProperties(entity, form);
            formList.add(form);
        }
        
        return formList;


	}
	
	public ArrayList<SnackForm> favorite_serach(String[] favorite) {
		ArrayList<SnackForm> formList = new ArrayList<>();
		
		if (favorite == null || favorite.length == 0) {
			
	        return new ArrayList<>();
	    }else {
	    	
	    	List<SnackEntity> entityList = new ArrayList<>();
			if(favorite != null) {
				for(int a = 0; a < favorite.length; a ++) {
					List<SnackEntity> karilist = repository.findBySnackid(favorite[a]);
					if (karilist.isEmpty() || karilist == null || karilist.size() == 0) {
						return null;
					}
					SnackEntity karientity= karilist.get(0);
					entityList.add(karientity);
					
				}
				
			}
			
	        for (SnackEntity entity : entityList) {
	            SnackForm form = new SnackForm();
	            BeanUtils.copyProperties(entity, form);
	            formList.add(form);
	        }
	    	
	    }

        
        return formList;


	}
	
	public ArrayList<SnackForm> history_serach(String[] favorite) {
		ArrayList<SnackForm> formList = new ArrayList<>();
		
		if (favorite == null || favorite.length == 0) {
			
	        return new ArrayList<>();
	    }else {
	    	
	    	List<SnackEntity> entityList = new ArrayList<>();
			if(favorite != null) {
				for(int a = 0; a < favorite.length; a ++) {
					List<SnackEntity> karilist = repository.findBySnackid(favorite[a]);
					if (karilist.isEmpty() || karilist == null || karilist.size() == 0) {
						return null;
					}
					SnackEntity karientity= karilist.get(0);
					entityList.add(karientity);
					
				}
				
			}
			
	        for (SnackEntity entity : entityList) {
	            SnackForm form = new SnackForm();
	            BeanUtils.copyProperties(entity, form);
	            formList.add(form);
	        }
	    	
	    }

        
        return formList;


	}
	
	public ArrayList<SnackForm> bag_serach(String[] bag) {
		ArrayList<SnackForm> formList = new ArrayList<>();
		
		if (bag == null || bag.length == 0) {
			
	        return formList;
	    }else {
	    	
	    	List<SnackEntity> entityList = new ArrayList<>();
			
			for(int a = 0; a < bag.length; a ++) {
				List<SnackEntity> karilist = repository.findBySnackid(bag[a]);
				if (karilist.isEmpty() || karilist == null || karilist.size() == 0) {
					return null;
				}
				SnackEntity karientity= karilist.get(0);
				entityList.add(karientity);
				
			}
				
			
			
	        for (SnackEntity entity : entityList) {
	            SnackForm form = new SnackForm();
	            BeanUtils.copyProperties(entity, form);
	            formList.add(form);
	        }
	    	
	    }

        
        return formList;


	}
	
	public ArrayList<SnackForm> buy_history(String[] bag) {
		ArrayList<SnackForm> formList = new ArrayList<>();
		
		if (bag == null || bag.length == 0) {
			
	        return formList;
	    }else {
	    	
	    	List<SnackEntity> entityList = new ArrayList<>();
			
			for(int a = 0; a < bag.length; a ++) {
				List<SnackEntity> karilist = repository.findBySnackid(bag[a]);
				if (karilist.isEmpty() || karilist == null || karilist.size() == 0) {
					return null;
				}
				SnackEntity karientity= karilist.get(0);
				entityList.add(karientity);
				
			}
				
			
			
	        for (SnackEntity entity : entityList) {
	            SnackForm form = new SnackForm();
	            BeanUtils.copyProperties(entity, form);
	            formList.add(form);
	        }
	    	
	    }

        
        return formList;


	}
	
	public ArrayList<SnackForm> buy_serach(String[] snackid) {
		ArrayList<SnackForm> formList = new ArrayList<>();
		
		
	    	List<SnackEntity> entityList = new ArrayList<>();
			
			for(int a = 0; a < snackid.length; a ++) {
				List<SnackEntity> karilist = repository.findBySnackid(snackid[a]);
				if (karilist.isEmpty() || karilist == null || karilist.size() == 0) {
					return null;
				}
				SnackEntity karientity= karilist.get(0);
				if(Integer.parseInt(karientity.getStock()) > 0) {
					entityList.add(karientity);
				}
				
				
			}
				
			
			
	        for (SnackEntity entity : entityList) {
	            SnackForm form = new SnackForm();
	            BeanUtils.copyProperties(entity, form);
	            formList.add(form);
	        }
	    	
	    

        
        return formList;
        
	}
	
	public String price_sum(String[] snackid) {
		int sum = 0;
		if (snackid == null || snackid.length == 0) {
			
		}else {
			
		
			for (int a = 0; a < snackid.length; a ++) {
				List<SnackEntity> entityList = repository.findBySnackid(snackid[a]);
				if (entityList.isEmpty() || entityList == null || entityList.size() == 0) {
					return "0";
				}
				SnackEntity entity = entityList.get(0);
				if(Integer.parseInt(entity.getStock()) > 0) {
					sum = sum + Integer.parseInt(entity.getPrice());
				}
				
			}
		}
		return String.valueOf(sum);
		
		
	}
	
	public void stock_degrese(String snackid) {
		String[] snacklist = snackid.split("_");
		for(int a = 0; a < snacklist.length; a ++) {
			List<SnackEntity> entityList = repository.findBySnackid(snacklist[a]);
			SnackEntity entity = entityList.get(0);
			int stock = Integer.parseInt(entity.getStock()) - 1;
			
			if(stock <= 0){
				stock = 0;
			}
			 
			entity.setStock(String.valueOf(stock));
			repository.save(entity);
			
		}
		
	}
	
	
	
	
	
}
