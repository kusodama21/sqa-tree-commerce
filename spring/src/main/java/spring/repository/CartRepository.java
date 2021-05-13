package spring.repository;

import org.springframework.stereotype.Component;

import spring.model.Cart;

@Component
public class CartRepository extends Repository<Cart> {

    // GET ONE BY ACCOUNT
    public Cart getOneByAccountId(Integer accountId) {
        String query = "SELECT * FROM cart WHERE accountId = ?";
        return querySingle(query, new Object[] {accountId});
    }

    // INSERT ONE
    public boolean insertOne(Cart t) {
        String query = "INSERT INTO cart VALUES (?,?)";
        Object[] args = {t.getAccountId(), t.getCouponCode()};

        return updateSingle(query, args);
    }

    // UPDATE ONE
    public boolean updateOne(Cart t) {
        String query = "UPDATE cart SET couponCode = ? WHERE accountId = ?";
        Object[] args = {t.getCouponCode(), t.getAccountId()};

        return updateSingle(query, args);
    }
}
