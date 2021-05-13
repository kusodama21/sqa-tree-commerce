package spring.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.form.admin.CouponAddForm;
import spring.model.Coupon;
import spring.repository.CouponRepository;

@Component
public class CouponValidator {
	
	@Autowired
	private CouponRepository couponRepository;

	// SUCCESS SECTION
	public static final int NONE_VIOLATE_CODE = -1;
	public static final String COUPON_ADD_SUCCESS_MSG = "A coupon has been successfully added";
	
	// CODE ERROR
	public static String[] codeErrors = {
		"The coupon code must be exactly 6 characters",
		"Only characters (both cases) and numbers are allowed",
		"The coupon code must be unique"
	};
	private static final int CODE_LENGTH_VIOLATE_CODE = 0,
			CODE_FORMAT_VIOLATE_CODE = 1,
			CODE_UNIQUE_VIOLATE_CODE = 2;
	
	// B-Minor: Check code length
	private boolean checkCodeLength(String code) {
		return code.length() == Coupon.CODE_FIXED_LENGTH;
	}
	
	// B-Minor: Check code format
	private boolean checkCodeFormat(String code) {
		return code.chars().allMatch(c -> Character.isLetterOrDigit(c));
	}
	
	// A-Minor: Validate code
	private int validateCode(String code) {
		if (!checkCodeLength(code))
			return CODE_LENGTH_VIOLATE_CODE;
		if (!checkCodeFormat(code))
			return CODE_FORMAT_VIOLATE_CODE;
		if (couponRepository.checkOneByCode(code))
			return CODE_UNIQUE_VIOLATE_CODE;
		return NONE_VIOLATE_CODE;
	}
	
	// Validate coupon add form
	public String validateAddForm(CouponAddForm form) {
		String code = form.getCode().trim();
		final int codeResult = validateCode(code);
		if (codeResult != NONE_VIOLATE_CODE)
			return codeErrors[codeResult];
		return null;
	}
}
