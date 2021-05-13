package spring.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import spring.model.Cart;

@Component
public class CartMapper implements RowMapper<Cart>{

    @Override
    public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
        Cart cart = new Cart();
        
        int accountId = rs.getInt("accountId");
        cart.setAccountId(accountId);
        
        String couponCode = rs.getString("couponCode");
        cart.setCouponCode(couponCode);
        
        return cart;
    }
}
