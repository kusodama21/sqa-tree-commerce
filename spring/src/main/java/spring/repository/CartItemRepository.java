package spring.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import spring.model.CartItem;

@Component
public class CartItemRepository extends Repository<CartItem> {
	
	// GET MANY BY CART
	public List<CartItem> getManyByAccountId(Integer accountId) {
		String query = "SELECT * FROM cart_item WHERE accountId = ?";
		return queryMany(query, new Object[] {accountId});
	}
	
	// GET ONE
	public CartItem getOneByAccountIdAndProductId(Integer accountId, Integer productId) {
		String query = "SELECT * FROM cart_item WHERE accountId = ? AND productId = ?";
		return querySingle(query, new Object[] {accountId, productId});
	}

	// INSERT ONE
	public boolean insertOne(CartItem t) {
		String query = "INSERT INTO cart_item VALUES (?, ?, ?)";
		Object[] args = {t.getAccountId(), t.getProductId(), t.getQuantity()};

		return updateSingle(query, args);
	}
	
	// UPDATE ONE
	public boolean updateOne(CartItem t) {
		String query = "UPDATE cart_item SET "
				+ "quantity = ? " 
				+ "WHERE accountId = ? AND productId = ?";
		Object[] args = {t.getQuantity(), t.getAccountId(), t.getProductId()};

		return updateSingle(query, args);
	}

	// DELETE ONE
	public boolean deleteOneByAccountIdAndProductId(Integer accountId, Integer productId) {
		String query = "DELETE FROM cart_item WHERE accountId = ? AND productId = ?";
		return updateSingle(query, new Object[] {accountId, productId});
	}

	// DELETE MANY BY CART
	public int deleteManyByAccountId(Integer accountId) {
		String query = "DELETE FROM cart_item WHERE accountId = ?";
		return template.update(query, new Object[] {accountId});
	}
	
	// UPDATE MANY
	public int updateMany(List<CartItem> cartItems) {
		int count = 0;
		for (CartItem cartItem : cartItems)
			if (updateOne(cartItem))
				++count;
		return count;
	}
	
	// GET NUMBER OF ITEMS IN CART BY ACCOUNT
	public int countItemsByAccountId(Integer accountId) {
		String query = "SELECT COUNT(*) FROM cart_item WHERE accountId = ?";
		return template.queryForObject(query, Integer.class, new Object[] {accountId});
	}
}
