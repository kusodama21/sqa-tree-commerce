package spring.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import spring.repository.AccountRepository;

@Controller
@RequestMapping("/admin/customers")
public class AdminCustomerController {
	
	public static final int ROLE_CUSTOMER_ID = 2;

	@Autowired
	private AccountRepository accountRepository;
	
	@GetMapping(value = "/index")
	public ModelAndView getCustomerIndex() {
		ModelAndView mav = new ModelAndView("admin/customers/index");
		mav.addObject("customers", accountRepository.getManyByRoleId(ROLE_CUSTOMER_ID));
		return mav;
	}
	
	@GetMapping(value = "/delete")
	public RedirectView deleteCustomer(
			@RequestParam(name = "id", required = true) int accountId,
			RedirectAttributes redirectAttributes) {
		RedirectView redirectView = new RedirectView("/admin/customers/index", true, true, false);
		boolean done = accountRepository.deleteOneById(accountId);
		
		redirectAttributes.addFlashAttribute("msg", done ? "A customer was deleted successfully" : "Delete operation failed");
		return redirectView;
	}
}
