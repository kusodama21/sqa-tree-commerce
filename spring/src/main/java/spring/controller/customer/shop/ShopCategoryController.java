package spring.controller.customer.shop;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import spring.controller.customer.HomeController;
import spring.holder.AccountHolder;
import spring.repository.CategoryRepository;
import spring.repository.ProductRepository;
import spring.service.AccountService;
import spring.service.CategoryService;

@Controller
@RequestMapping("/shop/categories")
public class ShopCategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private AccountService accountService;
	
	
	/**
	 * This method returns a view will one category.
	 * 
	 * @version 7.3
	 * @param categoryId
	 * @return
	 */
	@GetMapping("/show")
	public ModelAndView getCategoryById(@RequestParam(name = "id", required = true) int categoryId, HttpSession session) {
		ModelAndView mav = new ModelAndView("customer/shop/categories/show");
		
		// Session current id
		Integer currentId = (Integer) session.getAttribute("currentId");
		if (currentId != null) {
			AccountHolder accountHolder = accountService.getOneById(currentId);
			mav.addObject("accountHolder", accountHolder);
		}
		
		// Get category holder by id
		mav.addObject("holder", categoryService.getOneById(categoryId));
			
		// Category list
		mav.addObject("categories", categoryRepository.index());
						
		// Selling products (Random)
		mav.addObject("sellingProducts", productRepository.getManyAsRandom(HomeController.SIDEBAR_PRODUCT_COUNT));
		
		return mav;
	}
}
