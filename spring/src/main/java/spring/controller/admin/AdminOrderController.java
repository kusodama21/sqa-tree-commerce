package spring.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import spring.repository.OrderRepository;
import spring.service.OrderService;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@GetMapping(value = "/index")
	public ModelAndView getOrderIndex() {
		ModelAndView mav = new ModelAndView("admin/orders/index");
		mav.addObject("orderHolders", orderService.index());
		return mav;
	}
	
	@GetMapping(value = "/show")
	public ModelAndView getOrderShow(@RequestParam(name = "id", required = true) int orderId) {
		ModelAndView mav = new ModelAndView("admin/orders/show");
		mav.addObject("holder", orderService.getOneById(orderId));
		return mav;
	}
	
	@GetMapping(value = "/delete")
	public RedirectView deleteOrder(
			@RequestParam(name = "id", required = true) int orderId,
			RedirectAttributes redirectAttributes) {
		RedirectView redirectView = new RedirectView("/admin/orders/index", true, true, false);
		boolean done = orderRepository.deleteOneById(orderId);
		redirectAttributes.addFlashAttribute("msg", done ? "The order has been deleted successfully" : "Delete operation failed");
		return redirectView;
	}
	
	@GetMapping(value = "/change")
	public RedirectView changeStateOrder(
			@RequestParam(name = "id", required = true) int orderId,
			@RequestParam(name = "state", required = true) boolean state,
			RedirectAttributes redirectAttributes) {
		RedirectView redirectView = new RedirectView("/admin/orders/index", true, true, false);
		boolean done = orderRepository.changeStateById(state, orderId);
		redirectAttributes.addFlashAttribute("msg", done ? ("The order's state has been change successfully to " + state) : "Update operation failed");
		return redirectView;
	}
}
