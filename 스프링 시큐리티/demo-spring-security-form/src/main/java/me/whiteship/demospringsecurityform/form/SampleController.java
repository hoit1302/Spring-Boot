package me.whiteship.demospringsecurityform.form;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class SampleController {

	@GetMapping("/")
	public String index(Model model, Principal principal) {
		if (principal == null) {
			model.addAttribute("message", "Hello Spring Security");
		} else {
			model.addAttribute("message", "Hello, " + principal.getName());
		}

		return "index";
	}

	@GetMapping("/info")
	public String info(Model model) {
		model.addAttribute("message", "Info");
		return "info";
	}


	// 로그인한 사용자만이 접근 가능한 핸들러이기 때문에
	// 사용자에 대한 정보를 java.security에서 제공하는 principal 클래스를 통해 파라미터로 받아야 함. 그래야한다고 가정
	// 로그인한 사용자가 없을 땐, principal의 프로퍼티나 메소드에 접근할 시 NullPointerException가 발생함.
	@GetMapping("/dashboard")
	public String dashboard(Model model, Principal principal) {
		model.addAttribute("message", "Hello " + principal.getName());
		return "dashboard";
	}

	@GetMapping("/admin")
	public String admin(Model model, Principal principal) {
		model.addAttribute("message", "Hello Admin, " + principal.getName());
		return "admin";
	}
}
