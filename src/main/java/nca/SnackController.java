package nca;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(path="${base.path.snack}")
public class SnackController {

	@Autowired
	SnackService service;
	
	@Autowired
	BuyService buyservice;

	@Value("${base.path.snack}")
	String basePath;
	
	@Autowired
    private SecurityService securityService;
	
	

	@GetMapping(path="/list")
	public String list(Model model, SnackForm form) {

		log.info("一覧画面");
		/*

		// ArrayList<No07UserForm> list = service.getUsersListStub();
	    ArrayList<SnackForm> list = service.search(form);

		model.addAttribute("list", list);
		*/
		
		
		// 全件取得
        List<SnackEntity> snackList = service.getAllSnack();
        
        // モデルに追加
        model.addAttribute("title", "一覧");
        model.addAttribute("snackForm", new SnackForm());
        model.addAttribute("snackList", snackList);
        model.addAttribute("basePath", basePath);

		
		return basePath + "/list";
	
	}
	
	
	@GetMapping(path="/snack_list/{category}")
	public String snack_list(Model model, SnackForm form,@PathVariable String category) {

		
		
		
		ArrayList<SnackForm> list = service.category_search(form, category);
        String title = service.title(category);
        // モデルに追加
		model.addAttribute("category", title);
        model.addAttribute("snackList", list);
        model.addAttribute("basePath", basePath);

		
		return basePath + "/search_list";
	}
	
	
	@GetMapping(path="/snack_item/{snackid}")
	public String snack_item(Model model, SnackForm form,@PathVariable String snackid) {
	    
	    ArrayList<SnackForm> list = service.snackid_search(form, snackid);
	    
	        model.addAttribute("snackList", list);
	        model.addAttribute("form", new SnackForm());
	        model.addAttribute("basePath", basePath);
	        
		    return basePath + "/item";
	    
	   
		
	}
	
	@PostMapping(path="/search")
	public String snack_search(Model model,  @ModelAttribute("snackForm") SnackForm form) {
		log.info("dekiteru");
		log.info("カテゴリー検索{}",form.getSnackname());
	    ArrayList<SnackForm> list = service.searchAll(form.getSnackname());
		
        // モデルに追加
	    String text = "検索 : " + form.getSnackname();
	    model.addAttribute("title", text);
        model.addAttribute("snackList", list);
        model.addAttribute("basePath", basePath);
	    return basePath + "/list";
	}
	
	/*@GetMapping(path="/picture/*")
	public String picture(Model model, SnackForm form) {
		
		SnackEntity snack = new SnackEntity();
        
        model.addAttribute("snack", snack);
	    
		log.info("picture------");
        String picture_url = "/assets/image/" + "snack.jpg";
        //picture_url = picture_url + picture;
	    return picture_url;
	}
	
	*/
	
	
	
	
	
	
	

	


	

}
