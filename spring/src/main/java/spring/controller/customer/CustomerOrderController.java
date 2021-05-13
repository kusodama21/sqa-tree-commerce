package spring.controller.customer;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import spring.holder.AccountHolder;
import spring.repository.CategoryRepository;
import spring.repository.OrderRepository;
import spring.repository.ProductRepository;
import spring.service.AccountService;
import spring.service.OrderService;

@Controller
@RequestMapping("/customer/orders")
public class CustomerOrderController {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping(value = "/index")
	public ModelAndView getOrderIndex(HttpSession session) {
		ModelAndView mav = new ModelAndView("customer/orders/index");
		
		// Session current Id
		Integer currentId = (Integer) session.getAttribute("currentId");
		AccountHolder accountHolder = accountService.getOneById(currentId);
		mav.addObject("accountHolder", accountHolder);
		
		// Category list
		mav.addObject("categories", categoryRepository.index());
								
		// Selling products (Random)
		mav.addObject("sellingProducts", productRepository.getManyAsRandom(HomeController.SIDEBAR_PRODUCT_COUNT));
		
		// Add orders
		final int id = accountHolder.getCentral().getId();
		mav.addObject("orders", orderRepository.getManyByAccountId(id));
		
		return mav;
	}
	
	@GetMapping(value = "/show")
	public ModelAndView getOrder(@RequestParam(name = "id", required = true) int orderId, HttpSession session) {
		ModelAndView mav = new ModelAndView("customer/orders/show");
		
		// Session current Id
		Integer currentId = (Integer) session.getAttribute("currentId");
		AccountHolder accountHolder = accountService.getOneById(currentId);
		mav.addObject("accountHolder", accountHolder);
		
		// Category list
		mav.addObject("categories", categoryRepository.index());
						
		// Selling products (Random)
		mav.addObject("sellingProducts", productRepository.getManyAsRandom(HomeController.SIDEBAR_PRODUCT_COUNT));
		
		// Add order holder
		mav.addObject("holder", orderService.getOneById(orderId));
		
		return mav;
	}
	
	@GetMapping(value = "/convert")
	public RedirectView convertCartToOrder(RedirectAttributes redirectAttributes, HttpSession session) {
		
		// Session current Id
		Integer currentId = (Integer) session.getAttribute("currentId");
		AccountHolder accountHolder = accountService.getOneById(currentId);
		
		// Check number of items in cart
		final int numItemsInCart = accountHolder.getNumItemsInCart();
		if (numItemsInCart == 0) {
			RedirectView redirectView = new RedirectView("/customer/cart/index", true, true, false);
			redirectAttributes.addFlashAttribute("msg", "Empty cart, can not create order");
			return redirectView;
		}
		
		// Keep going if not empty
		final int id = accountHolder.getCentral().getId();
		String link = "/customer/orders/show?id=" + orderService.insertOneByAccountId(id);
		
		// Redirect
		RedirectView redirectView = new RedirectView(link, true, true, false);
		redirectAttributes.addFlashAttribute("msg", "Operated");
		return redirectView;
	}
}
