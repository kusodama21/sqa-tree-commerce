package spring.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import spring.model.OrderLine;

@Component
public class OrderLineMapper implements RowMapper<OrderLine> {

	@Override
	public OrderLine mapRow(ResultSet rs, int rowNum) throws SQLException {
		OrderLine orderLine = new OrderLine();
		
		int orderId = rs.getInt("orderId");
		orderLine.setOrderId(orderId);
		
		Optional<Integer> productId = Optional.ofNullable(rs.getObject("productId", Integer.class));
		orderLine.setProductId(productId);
		
		int quantity = rs.getInt("quantity");
		orderLine.setQuantity(quantity);
		
		String totalPrice = rs.getString("totalPrice");
		orderLine.setTotalPrice(totalPrice);
		
		return orderLine;
	}
}
