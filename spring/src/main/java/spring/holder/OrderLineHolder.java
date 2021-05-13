package spring.holder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.model.OrderLine;

@NoArgsConstructor
@Getter
@Setter
public class OrderLineHolder extends Holder<OrderLine> {
	
	// Product 1 - M cart item
	private ProductHolder productHolder;
}
