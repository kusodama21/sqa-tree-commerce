package spring.holder;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.model.Cart;
import spring.model.Coupon;

@NoArgsConstructor
@Getter
@Setter
public class CartHolder extends Holder<Cart> {
	
	// CartItem M - 1 cart
	private List<CartItemHolder> cartItemHolders;
	
	// Coupon 1 - M cart
	private Coupon coupon;
	
	// Adapt: Total price and final price
	private String totalPrice, finalPrice;
}
