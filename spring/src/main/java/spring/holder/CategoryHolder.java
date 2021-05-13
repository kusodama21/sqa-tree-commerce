package spring.holder;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.model.Category;
import spring.model.Product;

@NoArgsConstructor
@Getter
@Setter
public class CategoryHolder extends Holder<Category> {
	
	// Product M - 1 category
	private List<Product> products;
}
