package spring.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import spring.model.Order;

@Component
public class OrderRepository extends Repository<Order> {
	
	// GET LAST INSERTED ID
	private int getLastInsertedId() {
    	String query = "SELECT LAST_INSERT_ID()";
    	return template.queryForObject(query, Integer.class);
    }
	
	// INDEX
	public List<Order> index() {
		String query = "SELECT * FROM orders";
		return queryMany(query);
	}
	
	// GET MANY BY ACCOUNT
	public List<Order> getManyByAccountId(Integer accountId) {
		String query = "SELECT * FROM orders WHERE accountId = ?";
		return queryMany(query, new Object[] {accountId});
	}

	// GET ONE
	public Order getOneById(Integer id) {
		String query = "SELECT * FROM orders WHERE id = ?";
		return querySingle(query, new Object[] {id});
	}

	// DELETE ONE
	public boolean deleteOneById(Integer id) {
		String query = "DELETE FROM orders WHERE id = ?";
		return updateSingle(query, new Object[] {id});
	}

	// INSERT ONE
	public boolean insertOne(Order t) {
		String query = "INSERT INTO orders ("
				+ "accountId, "
				+ "couponCode, "
				+ "totalPrice, "
				+ "finalPrice"
				+ ") VALUES (?, ?, ?, ?)";
		
		Integer accountId = t.getAccountId().isPresent() ? t.getAccountId().get() : null;
		
		Object[] args = {accountId, t.getCouponCode(), t.getTotalPrice(), t.getFinalPrice()};
		return updateSingle(query, args);
	}
    
	// INSERT ONE COMPACT
    public int insertOneRetrieveId(Order t) {
    	insertOne(t);
    	return getLastInsertedId();
    }
    
    // CHANGE STATE BY ID
    public boolean changeStateById(boolean state, int id) {
    	String query = "UPDATE orders SET state = ? WHERE id = ?";
    	return updateSingle(query, new Object[] {state, id});
    }
}
