package spring.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.holder.CartItemHolder;
import spring.holder.ProductHolder;
import spring.model.CartItem;
import spring.repository.CartItemRepository;

@Component
public class CartItemService extends Service<CartItem, CartItemRepository, CartItemHolder> {

    @Autowired
    private ProductService productService;


    private CartItemHolder getOne(CartItem cartItem) {
        CartItemHolder holder = new CartItemHolder();
        holder.setCentral(cartItem);
        
        ProductHolder productHolder = productService.getOneById(cartItem.getProductId());
        holder.setProductHolder(productHolder);
        
        String totalPrice = new BigDecimal(productHolder.getCentral().getPrice())
        		.multiply(new BigDecimal(cartItem.getQuantity()))
        		.stripTrailingZeros()
        		.toPlainString();
        holder.setTotalPrice(totalPrice);
        
        return holder;
    }

    public List<CartItemHolder> getManyByAccountId(int accountId) {
    	List<CartItemHolder> holders = new ArrayList<>();
    	for (CartItem cartItem : centralRepository.getManyByAccountId(accountId))
    		holders.add(getOne(cartItem));
    	return holders;
    }
}
