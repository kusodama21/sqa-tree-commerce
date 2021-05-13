package spring.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import spring.model.Product;

@Component
public class ProductMapper implements RowMapper<Product>{

	public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
		Product product = new Product();
		
		int id = rs.getInt("id");
		product.setId(id);
		
		String name = rs.getString("name");
		product.setName(name);
		
		String price = rs.getString("price");
		product.setPrice(price);
		
		String image = rs.getString("image");
		product.setImage(image);
		
		String description = rs.getString("description");
		product.setDescription(description);
		
		Optional<Integer> categoryId = Optional.ofNullable(rs.getObject("categoryId", Integer.class));
		product.setCategoryId(categoryId);
		
		return product;
	}
}
