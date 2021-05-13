package spring.model;

import lombok.NoArgsConstructor;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Product {
	public static final int NAME_MIN_LENGTH = 3,
			NAME_MAX_LENGTH = 50,
			PRICE_MIN_VALUE = 1000,
			PRICE_MAX_VALUE = 2_000_000_000,
			PRICE_STEP_VALUE = 1,
			DESCRIPTION_MIN_LENGTH = 5;
	
	private int id;
	private String name, price, image, description;
	private Optional<Integer> categoryId;
}
