package spring.controller.admin;

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

import spring.converter.CouponConverter;
import spring.form.admin.CouponAddForm;
import spring.model.Coupon;
import spring.repository.CouponRepository;
import spring.validator.CouponValidator;

@Controller
@RequestMapping("/admin/coupons")
public class AdminCouponController {
	
	@Autowired
	private CouponRepository couponRepository;
	
	@Autowired
	private CouponValidator couponValidator;
	
	@Autowired
	private CouponConverter couponConverter;
	
	@GetMapping(value = "/index")
	public ModelAndView getCouponIndex() {
		ModelAndView mav = new ModelAndView("/admin/coupons/index");
		mav.addObject("coupons", couponRepository.index());
		return mav;
	}
	
	@GetMapping(value = "/add")
	public ModelAndView getCouponAddForm(@RequestParam(name = "reload", required = false, defaultValue = "false") boolean reload) {
		ModelAndView mav = new ModelAndView("/admin/coupons/add");
		
		if (!reload)
			mav.addObject("form", new CouponAddForm());
		
		// Load constraints for effect
		mav.addObject("couponEffectMinValue", Coupon.EFFECT_MIN_VALUE);
		mav.addObject("couponEffectMaxValue", Coupon.EFFECT_MAX_VALUE);
		mav.addObject("couponEffectStepValue", Coupon.EFFECT_STEP_VALUE);
		
		return mav;
	}
	
	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public RedirectView processCouponAddForm(
			@ModelAttribute(name = "form") CouponAddForm form,
			RedirectAttributes redirectAttributes) {
		String msg = couponValidator.validateAddForm(form);
		if (msg != null) {
			RedirectView redirectView = new RedirectView("/admin/coupons/add?reload=true", true, true, false);
			redirectAttributes.addFlashAttribute("form", form);
			redirectAttributes.addFlashAttribute("msg", msg);
			return redirectView;
		}
		
		RedirectView redirectView = new RedirectView("/admin/coupons/index", true, true, false);
		boolean done = couponRepository.insertOne(couponConverter.convertAddForm(form));
		redirectAttributes.addFlashAttribute("msg", done ? CouponValidator.COUPON_ADD_SUCCESS_MSG : "Insert operation failed");
		return redirectView;
	}
	
	@GetMapping(value = "/delete")
	public RedirectView deleteCoupon(
			@RequestParam(name = "code", required = true) String couponCode,
			RedirectAttributes redirectAttributes) {
		RedirectView redirectView = new RedirectView("/admin/coupons/index", true, true, false);
		boolean done = couponRepository.deleteOneByCode(couponCode);
		
		redirectAttributes.addFlashAttribute("msg", done ? "Coupon deleted successfully" : "Delete operation failed");
		return redirectView;
	}
}
