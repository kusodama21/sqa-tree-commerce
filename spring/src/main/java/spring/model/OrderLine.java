package spring.model;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OrderLine {
	private int orderId;
	
	private Optional<Integer> productId;
	
	private int quantity;
	
	private String totalPrice;
}
