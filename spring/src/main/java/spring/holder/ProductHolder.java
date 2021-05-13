package spring.holder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.model.Category;
import spring.model.Product;

@NoArgsConstructor
@Getter
@Setter
public class ProductHolder extends Holder<Product> {
	
	// Category 1 - M product
	private Category category;
}
