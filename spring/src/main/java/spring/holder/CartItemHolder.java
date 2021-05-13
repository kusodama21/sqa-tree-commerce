package spring.holder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.model.CartItem;

@NoArgsConstructor
@Getter
@Setter
public class CartItemHolder extends Holder<CartItem> {
	
	// Product 1 - M cart item
	private ProductHolder productHolder;
	
	// Adapt: Total price
	private String totalPrice;
}
