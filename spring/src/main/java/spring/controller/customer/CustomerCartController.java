package spring.controller.customer;

import java.util.Map;

import javax.servlet.http.HttpSession;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import spring.form.customer.CartUpdateForm;
import spring.holder.AccountHolder;
import spring.holder.CartHolder;
import spring.repository.CartItemRepository;
import spring.repository.CategoryRepository;
import spring.repository.ProductRepository;
import spring.service.AccountService;
import spring.service.CartService;

@Controller
@RequestMapping("/customer/cart")
public class CustomerCartController {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping("/index")
	public ModelAndView getCart(HttpSession session) {
		ModelAndView mav = new ModelAndView("customer/cart/index");
		
		// Session current Id
		Integer currentId = (Integer) session.getAttribute("currentId");
		if (currentId == null)
			System.out.println("Something broken");
		AccountHolder accountHolder = accountService.getOneById(currentId);
		mav.addObject("accountHolder", accountHolder);
		
		// Get the cart
		final int id = accountHolder.getCentral().getId();
		CartHolder holder = cartService.getOneByAccountId(id);
		mav.addObject("cartHolder", holder);
		
		// Category list
		mav.addObject("categories", categoryRepository.index());
				
		// Selling products (Random)
		mav.addObject("sellingProducts", productRepository.getManyAsRandom(HomeController.SIDEBAR_PRODUCT_COUNT));
		
		// Form
		mav.addObject("form", new CartUpdateForm());
		
		return mav;
	}
	
	@GetMapping("/add")
	public RedirectView addProductToCart(
			@RequestParam(name = "productId", required = true) int productId, 
			@RequestParam(name = "quantity", required = false, defaultValue = "1") int quantity,
			RedirectAttributes redirectAttributes,
			HttpSession session) {
		RedirectView redirectView = new RedirectView("/customer/cart/index", true, true, false);
		
		// Session current id
		Integer currentId = (Integer) session.getAttribute("currentId");
		AccountHolder accountHolder = accountService.getOneById(currentId);
		
		// Operate
		final int id = accountHolder.getCentral().getId();
		boolean done = cartService.addProductToCart(id, productId, quantity);
		
		// Redirect
		redirectAttributes.addFlashAttribute("msg", done ? "Item added successfully" : "Insert operation failed, product may have already been in cart");
		return redirectView;
	}
	
	@GetMapping("/delete")
	public RedirectView deleteProductFromCart(
			@RequestParam(name = "productId", required = true) int productId,
			RedirectAttributes redirectAttributes,
			HttpSession session) {
		RedirectView redirectView = new RedirectView("/customer/cart/index", true, true, false);
		
		// Session current id
		Integer currentId = (Integer) session.getAttribute("currentId");
		AccountHolder accountHolder = accountService.getOneById(currentId);
				
		// Operate
		final int id = accountHolder.getCentral().getId();
		boolean done = cartItemRepository.deleteOneByAccountIdAndProductId(id, productId);
		
		// Redirect
		redirectAttributes.addFlashAttribute("msg", done ? "Item deleted successfully" : "Delete operation failed");
		return redirectView;
	}
	
	@PostMapping(value = "/update", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public RedirectView updateCart(
			@ModelAttribute(name = "cartUpdateForm") CartUpdateForm form,
			RedirectAttributes redirectAttributes,
			HttpSession session) throws JsonMappingException, JsonProcessingException {
		RedirectView redirectView = new RedirectView("/customer/cart/index", true, true, false);
		
		// Get Map
		String updateInfo = form.getUpdateInfo();
		Map<Integer, Integer> productQuantityMap = new ObjectMapper().readValue(updateInfo, new TypeReference<Map<Integer, Integer>>() {});
		
		// Get Coupon Code
		String couponCode = form.getCouponCode();
		
		// Session current id
		Integer currentId = (Integer) session.getAttribute("currentId");
		AccountHolder accountHolder = accountService.getOneById(currentId);
						
		// Operate
		final int id = accountHolder.getCentral().getId();
		final int numUpdatedItems = cartService.updateCart(id, productQuantityMap, couponCode);
		
		// Redirect
		redirectAttributes.addFlashAttribute("msg", "Successfully updated " + numUpdatedItems + " items");
		return redirectView;
	}
}
