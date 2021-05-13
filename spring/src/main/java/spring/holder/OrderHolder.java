package spring.holder;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.model.Account;
import spring.model.Coupon;
import spring.model.Order;

@NoArgsConstructor
@Getter
@Setter
public class OrderHolder extends Holder<Order> {
	
	// Order M - 1 Account
	private Account account;
	
	// Order 1 - M orderLine
	private List<OrderLineHolder> orderLineHolders;
	
	// Coupon
	private Coupon coupon;
}
