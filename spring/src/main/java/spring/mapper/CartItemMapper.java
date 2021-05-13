package spring.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import spring.model.CartItem;

@Component
public class CartItemMapper implements RowMapper<CartItem> {

	public CartItem mapRow(ResultSet rs, int rowNum) throws SQLException {
		CartItem cartItem = new CartItem();
		
		int accountId = rs.getInt("accountId");
		cartItem.setAccountId(accountId);
		
		int productId = rs.getInt("productId");
		cartItem.setProductId(productId);
		
		int quantity = rs.getInt("quantity");
		cartItem.setQuantity(quantity);
		
		return cartItem;
	}
}
