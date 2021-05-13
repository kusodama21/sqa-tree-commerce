package spring.model;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Order {
	
	private int id;
	
	private Optional<Integer> accountId;
	
	private String couponCode;
	
	private String totalPrice, finalPrice;
	
	private Optional<Boolean> state;
}
