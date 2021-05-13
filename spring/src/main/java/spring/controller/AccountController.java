package spring.controller;

import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import spring.form.account.AccountLoginForm;
import spring.form.account.AccountRegisterForm;
import spring.holder.AccountHolder;
import spring.model.Account;
import spring.model.Cart;
import spring.repository.AccountRepository;
import spring.repository.CartRepository;
import spring.service.AccountService;
import spring.validator.AccountValidator;

@Controller
@RequestMapping(value = "/account")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AccountValidator accountValidator;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@GetMapping(value = "/register")
	public ModelAndView getRegisterForm(@RequestParam(name = "msg", required = false) String msg) {
		ModelAndView mav = new ModelAndView("account/register");
		if (msg != null && !msg.isEmpty())
			mav.addObject("msg", msg);
		mav.addObject("form", new AccountRegisterForm());
		return mav;
	}
	
	@PostMapping(value = "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public RedirectView processRegisterForm(@ModelAttribute(name = "accountRegisterForm") AccountRegisterForm form, HttpSession session) {
		String msg = accountValidator.validateRegisterForm(form);
		if (msg != null)
			return new RedirectView("/account/register?msg=" + msg, true, true, false);
		
		Account account = new Account();
		account.setUsername(form.getUsername());
		
		String hashPw = BCrypt.hashpw(form.getPassword(), BCrypt.gensalt());
		account.setPassword(hashPw);
		
		account.setFullname(form.getFullname());
		account.setAddress(form.getAddress());
		account.setRoleId(2);
		
		final int id = accountRepository.insertOneRetrieveId(account);
		cartRepository.insertOne(new Cart(id, null));
		
		session.setAttribute("currentId", id);
		
		return new RedirectView("/customer/cart/index", true, true, false);
	}
	
	@GetMapping(value = "/login")
	public ModelAndView getLoginForm(@RequestParam(name = "msg", required = false) String msg, HttpSession session) {
		
		Integer currentId = (Integer) session.getAttribute("currentId");
		if (currentId == null) {
			ModelAndView mav = new ModelAndView("account/login");
			mav.addObject("form", new AccountLoginForm());
			if (msg != null && !msg.isEmpty())
				mav.addObject("msg", msg);	
			return mav;
		}
		
		AccountHolder accountHolder = accountService.getOneById(currentId);
		if (accountHolder.getRole().getName().equals("ROLE_ADMIN"))
			return new ModelAndView("redirect:/admin/products/index");
		else
			return new ModelAndView("redirect:/customer/cart/index");
	}
	
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public RedirectView processLoginForm(
			@ModelAttribute(name = "form") AccountLoginForm form,
			RedirectAttributes redirectAttributes,
			HttpSession session) {
		String failedRoot = "/account/login?msg=";
		
		AccountHolder accountHolder = accountService.getOneByUsername(form.getUsername());
		if (accountHolder == null) {
			return new RedirectView(failedRoot + "Wrong Username", true, true, false);
		}
		
		String hashPw = accountHolder.getCentral().getPassword(), inputPw = form.getPassword();
		if (!BCrypt.checkpw(inputPw, hashPw))
			return new RedirectView(failedRoot + "Wrong Password", true, true, false);
		
		session.setAttribute("currentId", accountHolder.getCentral().getId());
		String role = accountHolder.getRole().getName();
		
		if (role.equals("ROLE_ADMIN"))
			return new RedirectView("/admin/products/index", true, true, false);
		else
			return new RedirectView("/customer/cart/index", true, true, false);
	}
	
	@GetMapping(value = "/logout")
	public RedirectView logout(HttpSession session) {
		Integer currentId = (Integer) session.getAttribute("currentId");
		if (currentId != null)
			session.removeAttribute("currentId");
		return new RedirectView("/home", true, true, false);
	}
}
