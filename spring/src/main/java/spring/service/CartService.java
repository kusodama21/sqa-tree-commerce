package spring.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.holder.CartHolder;
import spring.holder.CartItemHolder;
import spring.model.Cart;
import spring.model.CartItem;
import spring.model.Coupon;
import spring.repository.CartItemRepository;
import spring.repository.CartRepository;
import spring.repository.CouponRepository;

@Component
public class CartService extends Service<Cart, CartRepository, CartHolder> {

    @Autowired
    private CouponRepository couponRepository;
    
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartItemService cartItemService;

    // DEFAULT - GET ONE
    private CartHolder getOne(Cart cart) {
        CartHolder holder = new CartHolder();
        holder.setCentral(cart);

        List<CartItemHolder> cartItemHolders = cartItemService.getManyByAccountId(cart.getAccountId());
        holder.setCartItemHolders(cartItemHolders);

        String totalPrice = cartItemHolders
        		.stream()
        		.map(item -> new BigDecimal(item.getTotalPrice()))
        		.reduce(BigDecimal.ZERO, BigDecimal::add)
        		.toString();
        holder.setTotalPrice(totalPrice);
        
        String couponCode = cart.getCouponCode();
        Coupon coupon = couponCode == null ? null : couponRepository.getOneByCode(cart.getCouponCode());
        holder.setCoupon(coupon);
        
        if (coupon == null) {
        	holder.setFinalPrice(totalPrice);
        } else {
        	String finalPrice = new BigDecimal(totalPrice)
        			.multiply(new BigDecimal("1").subtract(new BigDecimal(coupon.getEffect()).divide(new BigDecimal("100"))))
            		.setScale(2, RoundingMode.HALF_EVEN)
            		.stripTrailingZeros()
            		.toPlainString();
        	holder.setFinalPrice(finalPrice);
        }
        
        return holder;
    }

    // GET ONE BY ACCOUNT ID
    public CartHolder getOneByAccountId(int accountId) {
        return getOne(centralRepository.getOneByAccountId(accountId));
    }
    
    // ADD PRODUCT TO CART
    public boolean addProductToCart(int accountId, int productId, int quantity) {
    	CartItem cartItem = new CartItem(accountId, productId, quantity);
    	return cartItemRepository.insertOne(cartItem);
    }
    
    // DELETE PRODUCT FROM CART
    public boolean deleteProductFromCart(int accountId, int productId) {
    	return cartItemRepository.deleteOneByAccountIdAndProductId(accountId, productId);
    }
    
    // UPDATE CART
    public int updateCart(Integer accountId, Map<Integer, Integer> productQuantityMap, String couponCode) {
    	Cart cart = centralRepository.getOneByAccountId(accountId);
    	
    	cart.setCouponCode(couponCode.isEmpty() ? null : couponCode);
    	centralRepository.updateOne(cart);
    	
    	int count = 0;
    	for (Map.Entry<Integer, Integer> entry : productQuantityMap.entrySet()) {
    		CartItem cartItem = cartItemRepository.getOneByAccountIdAndProductId(accountId, entry.getKey());
    		cartItem.setQuantity(entry.getValue());
    		
    		if (cartItemRepository.updateOne(cartItem))
    			++count;
    	}
    	return count;
    }
    
    // DELETE CART ITEMS AND RESET THE CART
    public int resetCartByAccountId(int accountId) {
    	Cart cart = centralRepository.getOneByAccountId(accountId);
    	cart.setCouponCode(null);
    	centralRepository.updateOne(cart);
    	return cartItemRepository.deleteManyByAccountId(accountId);
    }
}
