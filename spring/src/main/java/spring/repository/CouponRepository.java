package spring.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import spring.model.Coupon;

@Component
public class CouponRepository extends Repository<Coupon> {

	// INDEX
    public List<Coupon> index() {
        String query = "SELECT * FROM coupon";
        return queryMany(query);
    }

    // GET ONE
    public Coupon getOneByCode(String code) {
        String query = "SELECT * FROM coupon WHERE code = ?";
        return querySingle(query, new Object[] {code});
    }

    // DELETE ONE
    public boolean deleteOneByCode(String code) {
        String query = "DELETE * FROM coupon WHERE code = ?";
        return updateSingle(query, new Object[] {code});
    }

    // INSERT ONE
    public boolean insertOne(Coupon t) {
        String query = "INSERT INTO coupon VALUES (?, ?)";
        return updateSingle(query, new Object[] {t.getCode(), t.getEffect()});
    }
    
    // CHECK ONE BY CODE
    public boolean checkOneByCode(String code) {
    	String query= "SELECT EXISTS (SELECT 1 FROM coupon WHERE code = ?)";
    	return template.queryForObject(query, Boolean.class, new Object[] {code});
    }
}
