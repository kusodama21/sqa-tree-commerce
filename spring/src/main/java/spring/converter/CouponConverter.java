package spring.converter;

import org.springframework.stereotype.Component;

import spring.form.admin.CouponAddForm;
import spring.model.Coupon;

@Component
public class CouponConverter {
	
	public Coupon convertAddForm(CouponAddForm form) {
		return new Coupon(form.getCode(), form.getEffect());
	}
}
