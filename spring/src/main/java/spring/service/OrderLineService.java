package spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.holder.OrderLineHolder;
import spring.holder.ProductHolder;
import spring.model.OrderLine;
import spring.repository.OrderLineRepository;

@Component
public class OrderLineService extends Service<OrderLine, OrderLineRepository, OrderLineHolder>{
	
	@Autowired
    private ProductService productService;


    private OrderLineHolder getOne(OrderLine orderLine) {
        OrderLineHolder holder = new OrderLineHolder();
        holder.setCentral(orderLine);
        
        Optional<Integer> productId = orderLine.getProductId();
        if (productId.isPresent()) {
        	ProductHolder productHolder = productService.getOneById(productId.get());
        	holder.setProductHolder(productHolder);
        }
        
        return holder;
    }

    public List<OrderLineHolder> getManyByOrderId(int orderId) {
    	List<OrderLineHolder> holders = new ArrayList<>();
    	for (OrderLine orderLine : centralRepository.getManyByOrderId(orderId))
    		holders.add(getOne(orderLine));
    	return holders;
    }
}
