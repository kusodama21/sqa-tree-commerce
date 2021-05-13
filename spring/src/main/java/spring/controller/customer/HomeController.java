package spring.controller.customer;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import spring.holder.AccountHolder;
import spring.repository.CategoryRepository;
import spring.repository.ProductRepository;
import spring.service.AccountService;
import spring.service.CategoryService;

@Controller
public class HomeController {
	
	public static final int SIDEBAR_PRODUCT_COUNT = 10, SIDEBAR_CATEGORY_COUNT = 3, SIDEBAR_CATEGORY_PRODUCT_COUNT = 4;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping(value = "/home")
	public ModelAndView getHome(HttpSession session) {
		ModelAndView mav = new ModelAndView("customer/home/index");
		
		// Session current Id
		Integer currentId = (Integer) session.getAttribute("currentId");
		if (currentId != null) {
			AccountHolder accountHolder = accountService.getOneById(currentId);
			mav.addObject("accountHolder", accountHolder);
		}
		
		// Category list
		mav.addObject("categories", categoryRepository.index());
		
		// Selling products (Random)
		mav.addObject("sellingProducts", productRepository.getManyAsRandom(SIDEBAR_PRODUCT_COUNT));
		
		// Add category holders
		mav.addObject("categoryHolders", categoryService.getManyAsRandomRestrictProductQuantity(SIDEBAR_CATEGORY_COUNT, SIDEBAR_CATEGORY_PRODUCT_COUNT));
		
		return mav;
	}
	
	@GetMapping(value = "/404")
	public ModelAndView permissionDenied() {
		return new ModelAndView("404");
	}
}
