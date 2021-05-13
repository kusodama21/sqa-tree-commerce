package spring.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import spring.model.Order;

@Component
public class OrderMapper implements RowMapper<Order> {

	@Override
	public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
		Order order = new Order();
		
		int id = rs.getInt("id");
		order.setId(id);
		
		Optional<Integer> accountId = Optional.ofNullable(rs.getObject("accountId", Integer.class));
		order.setAccountId(accountId);
		
		String couponCode = rs.getString("couponCode");
		order.setCouponCode(couponCode);
		
		String totalPrice = rs.getString("totalPrice");
		order.setTotalPrice(totalPrice);
		
		String finalPrice = rs.getString("finalPrice");
		order.setFinalPrice(finalPrice);
		
		Optional<Boolean> state = Optional.ofNullable(rs.getObject("state", Boolean.class));
		order.setState(state);
		
		return order;
	}
}
