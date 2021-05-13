package spring.model;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Coupon {
	public static final int CODE_FIXED_LENGTH = 6;
	public static final String EFFECT_MIN_VALUE = "0.01", EFFECT_MAX_VALUE = "99.99", EFFECT_STEP_VALUE = "0.01";
    private String code, effect;
}
