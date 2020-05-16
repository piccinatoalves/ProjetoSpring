package br.biblioteca.livros.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.biblioteca.livros.model.User;
import br.biblioteca.livros.service.SecurityService;
import br.biblioteca.livros.service.UserService;
import br.biblioteca.livros.validator.LoginValidator;
import br.biblioteca.livros.validator.UserValidator;

import java.util.List;

@Controller
@RequestMapping("/auth")
public class UserControllers {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserValidator userValidator;

	@Autowired
	private LoginValidator loginValidator;
	
	@Autowired
	private SecurityService securityService;

	@GetMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("auth/form", "userForm", new User());
	}

	@PostMapping("/autentication")
	public ModelAndView autentication(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
		
		loginValidator.validate(userForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return new ModelAndView("auth/form");
		}
		
		securityService.login(userForm.getUsername(), userForm.getPassword());	
		return new ModelAndView("redirect:/");
	}

	@GetMapping("/list")
	public ModelAndView list() {
		return new ModelAndView("/auth/list");
	}
	
	@GetMapping("/listadmin")
	public ModelAndView listadmin(User user) {

		List<User> users = userService.findAll();
		return new ModelAndView("/auth/listadmin","users",users);
	}

	@GetMapping(value = "/registration")
	public ModelAndView registration() {
		
		return new ModelAndView("auth/registration", "userForm", new User());
	}

	@PostMapping(value = "/registration")
	public ModelAndView registrationform(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {

		userValidator.validate(userForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return new ModelAndView("auth/registration");
		}
		
		String password = userForm.getPassword();

		userService.save(userForm);

		try {
			
			securityService.login(userForm.getUsername(), password);
			return new ModelAndView("redirect:/auth/list");
			
		} catch (Exception e) {
			
			return new ModelAndView("redirect:/auth/login");
		}
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
	    SecurityContextHolder.clearContext();
	     if (session != null) {
	        session.invalidate();
	     }

		return "redirect:/";
	}

}
