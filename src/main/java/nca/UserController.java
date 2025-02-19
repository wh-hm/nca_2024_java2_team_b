package nca;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(path="${base.path.user}")

//ここ違うよ
public class UserController {

	@Autowired
	UserService service;
	
	@Value("${base.path.user}")
	String basePath;
	
	
	@GetMapping(path="/login")
	public String login(Model model,UserForm form) {

		log.info("ログイン画面");
		
		model.addAttribute("userform", form);
		
		return basePath + "/login";
	}

	
	
	

	

	@GetMapping(path="/info")
	public String edit(Model model, UserForm form) {

		UserForm userform = service.user_info();
		//form = service.findById(form);
		model.addAttribute("userform", userform);
		model.addAttribute("snackForm", new SnackForm());

		model.addAttribute("basePath", basePath);
		
		return basePath + "/info";
	}

	
	
	
	
	@GetMapping(path="/login/add")
	public String user_add(Model model, UserForm form) {

		model.addAttribute("form", form);
		model.addAttribute("snackForm", new SnackForm());
		return basePath + "/add";
	}
	
	@PostMapping(path="/add_action")
	public String add_action(Model model, UserForm form) {

		log.info("登録完了メッセージ画面");

		model.addAttribute("basePath", basePath);
		
		// この下にServiceのsaveメソッドを実行する
		// 実行結果をformで受け取る　※受け取った後の処理なし
		
		form = service.user_save(form);
		
		log.info("実行できてるよー");
		model.addAttribute("snackForm", new SnackForm());
		model.addAttribute("basePath", basePath);
		
		return basePath + "/add_msg";
	}
	
	@GetMapping(path="/login/nologin")
	public String user_nologin(Model model, UserForm form) {

		
		
		return "redirect:/snack/list";
	}
	
	

	@GetMapping(path="/login_please")
	public String login_please(Model model, UserForm form) {

		
		
		return basePath + "/login_please";
	}
	
	@GetMapping(path="/edit")
	public String user_edit(Model model, UserForm form) {
		UserForm userform = service.user_info();
		userform.setPassword("");
		model.addAttribute("userform", userform);
		model.addAttribute("snackForm", new SnackForm());
        model.addAttribute("basePath", basePath);
		
		
		return basePath + "/edit";
	}
	
	@PostMapping(path="/edit_msg")
	public String user_edit_msg(Model model, UserForm form) {
		service.save(form);
		model.addAttribute("snackForm", new SnackForm());
        model.addAttribute("basePath", basePath);
		
		
		return basePath + "/edit_msg";
	}
	
	@GetMapping(path="/logout")
	public String logout(Model model,UserForm form,HttpSession session){
		session.invalidate();
		return basePath + "/login";
	}

}
