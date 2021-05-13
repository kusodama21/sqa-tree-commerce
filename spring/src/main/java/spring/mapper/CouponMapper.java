package spring.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import spring.model.Coupon;

@Component
public class CouponMapper implements RowMapper<Coupon>{

    @Override
    public Coupon mapRow(ResultSet rs, int rowNum) throws SQLException {
        Coupon coupon = new Coupon();
        
        String code = rs.getString("code");
        coupon.setCode(code);
        
        String effect = rs.getString("effect");
        coupon.setEffect(effect);
        
        return coupon;
    }
}
