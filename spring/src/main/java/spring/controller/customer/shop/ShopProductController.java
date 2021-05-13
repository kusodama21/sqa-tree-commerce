package spring.controller.customer.shop;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import spring.controller.customer.HomeController;
import spring.holder.AccountHolder;
import spring.model.Product;
import spring.repository.CategoryRepository;
import spring.repository.ProductRepository;
import spring.service.AccountService;
import spring.service.ProductService;

@Controller
@RequestMapping("/shop/products")
public class ShopProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private AccountService accountService;
	
	/**
	 * This method returns a view with all the products available.
	 * 
	 * @version 7.3
	 * @return ModelAndView
	 */
	@GetMapping("/index")
	public ModelAndView getAllProducts(HttpSession session) {
		ModelAndView mav = new ModelAndView("customer/shop/products/list");
		
		// Session current id
		Integer currentId = (Integer) session.getAttribute("currentId");
		if (currentId != null) {
			AccountHolder accountHolder = accountService.getOneById(currentId);
			mav.addObject("accountHolder", accountHolder);
		}
		
		// Set heading
		mav.addObject("heading", "All products supported");
		
		// Category list
		mav.addObject("categories", categoryRepository.index());
				
		// Selling products (Random)
		mav.addObject("sellingProducts", productRepository.getManyAsRandom(HomeController.SIDEBAR_PRODUCT_COUNT));
		
		// Load list of product
		mav.addObject("products", productRepository.index());
		
		return mav;
	}
	
	/**
	 * This method returns a view with one product in details
	 * 
	 * @version 7.3
	 * @param productId
	 * @return ModelAndView
	 */
	@GetMapping("/show")
	public ModelAndView getProductById(@RequestParam(name = "id", required = true) int productId, HttpSession session) {
		ModelAndView mav = new ModelAndView("customer/shop/products/show");
		
		// Session current id
		Integer currentId = (Integer) session.getAttribute("currentId");
		if (currentId != null) {
			AccountHolder accountHolder = accountService.getOneById(currentId);
			mav.addObject("accountHolder", accountHolder);
		}
		
		// Set holder
		mav.addObject("holder", productService.getOneById(productId));
		
		// Category list
		mav.addObject("categories", categoryRepository.index());
						
		// Selling products (Random)
		mav.addObject("sellingProducts", productRepository.getManyAsRandom(HomeController.SIDEBAR_PRODUCT_COUNT));
		
		return mav;
	}
	
	/**
	 * This method returns a view with products that matches the given name.
	 * 
	 * @version 7.3
	 * @param productName
	 * @return ModelAndView
	 */
	@GetMapping(value = "/search")
	public ModelAndView getProductsByName(@RequestParam(name = "name", required = true) String productName, HttpSession session) {
		ModelAndView mav = new ModelAndView("customer/shop/products/list");
		
		// Session current id
		Integer currentId = (Integer) session.getAttribute("currentId");
		if (currentId != null) {
			AccountHolder accountHolder = accountService.getOneById(currentId);
			mav.addObject("accountHolder", accountHolder);
		}
		
		// Load list of product holders with name
		List<Product> products = productRepository.getManyByName(productName);
		
		// Set heading
		mav.addObject("heading", "Query " + "\"" + productName + "\"" + " matches " + products.size() + " results");
		
		// Category list
		mav.addObject("categories", categoryRepository.index());
								
		// Selling products (Random)
		mav.addObject("sellingProducts", productRepository.getManyAsRandom(HomeController.SIDEBAR_PRODUCT_COUNT));
		
		// Set products
		mav.addObject("products", products);
		
		return mav;
	}
}
