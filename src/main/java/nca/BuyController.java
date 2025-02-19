package nca;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(path="${base.path.buy}")

//ここ違うよ
public class BuyController {

	@Autowired
	BuyService service;
	
	@Autowired
	SnackService snackservice;
	
	@Autowired
	UserService userservice;

	@Value("${base.path.buy}")
	String basePath;
	
	@GetMapping(path="/favorite")
	public String favorite(Model model, BuyForm form) {
	    service.data_true();
	    String[] snackid_list = service.favoriteAll(form);
	    ArrayList<SnackForm> list = snackservice.favorite_serach(snackid_list);
		
        // モデルに追加
        model.addAttribute("snackList", list);
        model.addAttribute("snackForm",new SnackForm());
        model.addAttribute("basePath", basePath);
	    return basePath + "/favorite";
	}
	
	@GetMapping(path="/history")
	public String history(Model model, BuyForm form) {
	    service.data_true();
	    String[] history_list = service.historyAll(form);
	    ArrayList<SnackForm> list = snackservice.history_serach(history_list);
		
        // モデルに追加
        model.addAttribute("snackList", list);
        model.addAttribute("form",new SnackForm());
        model.addAttribute("basePath", basePath);
	    return basePath + "/history";
	}
	
	@GetMapping(path="/bag")
	public String bag(Model model, BuyForm form) {
	    service.data_true();
	    String[] bag_list = service.bagAll(form);
	    ArrayList<SnackForm> list = snackservice.bag_serach(bag_list);
		
        String price_sum = snackservice.price_sum(bag_list);
        int price = Integer.parseInt(price_sum);
        model.addAttribute("price_sum", price_sum);
        model.addAttribute("price", price);
        model.addAttribute("snackList", list);
        model.addAttribute("form",new SnackForm());
        model.addAttribute("basePath", basePath);
	    return basePath + "/bag";
	}
	
	@GetMapping(path="/snack_item/favorite_add/{snackid}")
	public String favorite_add(Model model,SnackForm form,@PathVariable String snackid) {
	    log.info("お気に入りに追加する");
	    service.data_true();
	    service.favorite_add(snackid);
	    model.addAttribute("form",form);
	    model.addAttribute("basePath", basePath);
	    return  basePath + "/favorite_add";  // 遷移先のビュー
	}
	
	@PostMapping(path="/favorite/delete")
	public String favorite_delete(@RequestParam List<String> snackid,Model model) {
	    service.data_true();
		service.favorite_delete(snackid);
		
		model.addAttribute("form",new SnackForm());
        model.addAttribute("basePath", basePath);
	    return basePath + "/favorite_delete_msg";
	}
	
	@GetMapping(path="/bag_add/{snackid}")
	public String bag_add(Model model,SnackForm form,@PathVariable String snackid) {
	    log.info("買い物かごに追加する");
	    log.info(snackid);
	    service.data_true();
	    service.bag_add(snackid);
	    model.addAttribute("form",form);
	    model.addAttribute("basePath", basePath);
	    return  basePath + "/bag_msg";  // 遷移先のビュー
	}
	
	@PostMapping(path="/bag/delete")
	public String bag_delete(@RequestParam List<String> snackid,Model model) {
	    service.data_true();
	    service.bag_delete(snackid);
		
		model.addAttribute("form",new SnackForm());
        model.addAttribute("basePath", basePath);
	    return basePath + "/bag_delete_msg";
	}
	
	@GetMapping(path="/buy_history")
	public String buy_history(Model model) {
		String[] buy_list = service.buy_snackid();
	  
		ArrayList<SnackForm> list = snackservice.buy_history(buy_list);
		model.addAttribute("form",new SnackForm());
		model.addAttribute("snackList", list);
        model.addAttribute("basePath", basePath);
	    return basePath + "/buy_history";
	}
	
	@GetMapping(path="/{snackid}")
	public String buy_snackid(Model model,UserForm form,@PathVariable String snackid) {
		String email = service.user_email();
		UserForm userform = userservice.user_info(email);
		model.addAttribute("snackform",new SnackForm());
		model.addAttribute("userform",userform);
		model.addAttribute("snackid", snackid);
        model.addAttribute("basePath", basePath);
	    return basePath + "/buy_user_info";
	}
	
	@PostMapping(path="/pay/{snackid}")
	public String buy_user_info_post(Model model,UserForm form,@PathVariable String snackid) {
		String payhow = service.pay();
		UserForm userform = userservice.buy_save_user(form);
		BuyForm buyform = new BuyForm();
		buyform.setPay(payhow);
		
		model.addAttribute("payhow",payhow);
		model.addAttribute("userform",userform);
		model.addAttribute("buyform",buyform);
		model.addAttribute("form",new SnackForm());
		model.addAttribute("snackid", snackid);
        model.addAttribute("basePath", basePath);
	    return basePath + "/pay";
	}
	@PostMapping(path="/check/{snackid}")
	public String buy_check(Model model,UserForm userform,@PathVariable String snackid,BuyForm buyform) {
		String email = service.user_email();
		UserForm user_new_form = userservice.user_info(email);
		BuyForm buy_new_form = service.save_pay(buyform);
		String[] buy_list = service.buy(snackid);
		
	    ArrayList<SnackForm> snackList = snackservice.buy_serach(buy_list);
		model.addAttribute("userform",user_new_form);
		model.addAttribute("snackList",snackList);
		model.addAttribute("buyform",buy_new_form);
		model.addAttribute("form",new SnackForm());
		model.addAttribute("snackid", snackid);
        model.addAttribute("basePath", basePath);
	    return basePath + "/check";
	}
	
	
	@GetMapping(path="/buy_msg/{snackid}")
	public String buy_msg(Model model,SnackForm form,@PathVariable String snackid) {
		service.buy_history_add(snackid);
		snackservice.stock_degrese(snackid);
		model.addAttribute("form",new SnackForm());
        model.addAttribute("basePath", basePath);
	    return basePath + "/buy_msg";
	}
	
	@GetMapping(path="/bag_all_buy")
	public String bag_all_buy(Model model,UserForm userform,BuyForm buyform) {
		String email = service.user_email();
		UserForm user_new_form = userservice.user_info(email);
		
		
		
	    String snackid = service.bag_buy();
		model.addAttribute("userform",user_new_form);
		
		
		model.addAttribute("snackform",new SnackForm());
		model.addAttribute("snackid", snackid);
        model.addAttribute("basePath", basePath);
	    return basePath + "/buy_user_info";
	}
	
	

	
	
	

}
