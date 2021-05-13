package spring.repository;

import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Component;

import spring.model.OrderLine;

@Component
public class OrderLineRepository extends Repository<OrderLine> {

	// GET MANY BY ORDER
	public List<OrderLine> getManyByOrderId(Integer orderId) {
		String query = "SELECT * FROM order_line WHERE orderId = ?";
		return queryMany(query, new Object[] {orderId});
	}
	
	// INSERT MANY - THIS IS NEW
	public int insertMany(List<OrderLine> list) {
		String query = "INSERT INTO order_line VALUES (?, ?, ?, ?)";
		int[] results = template.batchUpdate(query, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				OrderLine orderLine = list.get(i);
				
				int orderId = orderLine.getOrderId();
				ps.setInt(1, orderId);
				
				Integer productId = orderLine.getProductId().isPresent() ? orderLine.getProductId().get() : null;
				ps.setObject(2, productId, JDBCType.INTEGER);
				
				int quantity = orderLine.getQuantity();
				ps.setInt(3, quantity);
				
				String totalPrice = orderLine.getTotalPrice();
				ps.setString(4, totalPrice);
			}

			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
		
		return (int) Arrays.stream(results)
				.filter(e -> e > 0)
				.count();
	}
}
