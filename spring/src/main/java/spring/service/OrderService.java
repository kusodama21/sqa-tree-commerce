package spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.holder.CartHolder;
import spring.holder.CartItemHolder;
import spring.holder.OrderHolder;
import spring.holder.OrderLineHolder;
import spring.holder.ProductHolder;
import spring.model.Account;
import spring.model.Coupon;
import spring.model.Order;
import spring.model.OrderLine;
import spring.repository.AccountRepository;
import spring.repository.CouponRepository;
import spring.repository.OrderLineRepository;
import spring.repository.OrderRepository;

@Component
public class OrderService extends Service<Order, OrderRepository, OrderHolder> {

	@Autowired
	private OrderLineRepository orderLineRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderLineService orderLineService;
	
	@Autowired
	private CouponRepository couponRepository;

	public List<OrderHolder> index() {
		List<OrderHolder> holders = new ArrayList<>();
		for (Order order : orderRepository.index())
			holders.add(getOne(order));
		return holders;
	}
	
	public OrderHolder getOne(Order t) {
		OrderHolder holder = new OrderHolder();
		holder.setCentral(t);
		
		Optional<Integer> accountId = t.getAccountId();
		if (accountId.isPresent()) {
			Account account = accountRepository.getOneById(accountId.get());
			holder.setAccount(account);
		}
		
		List<OrderLineHolder> orderLineHolders = orderLineService.getManyByOrderId(t.getId());
		holder.setOrderLineHolders(orderLineHolders);
		
		String couponCode = t.getCouponCode();
		Coupon coupon = couponCode == null ? null : couponRepository.getOneByCode(t.getCouponCode());
		holder.setCoupon(coupon);
		
		return holder;
	}

	public List<OrderHolder> getManyByAccountId(int accountId) {
		List<OrderHolder> holders = new ArrayList<>();
		for (Order order : orderRepository.getManyByAccountId(accountId))
			holders.add(getOne(order));
		return holders;
	}
	
	public OrderHolder getOneById(int id) {
		return getOne(orderRepository.getOneById(id));
	}

	public int insertOneByAccountId(int accountId) {
		CartHolder cartHolder = cartService.getOneByAccountId(accountId);
		
		Order order = new Order();
		order.setAccountId(Optional.of(accountId));
		order.setCouponCode(cartHolder.getCentral().getCouponCode());
		order.setTotalPrice(cartHolder.getTotalPrice());
		order.setFinalPrice(cartHolder.getFinalPrice());
		
		final int orderId = centralRepository.insertOneRetrieveId(order);
		
		List<OrderLine> orderLines = new ArrayList<>();
		for (CartItemHolder itemHolder : cartHolder.getCartItemHolders()) {
			OrderLine orderLine = new OrderLine();
			orderLine.setOrderId(orderId);
			
			ProductHolder productHolder = itemHolder.getProductHolder();
			orderLine.setProductId(Optional.of(productHolder.getCentral().getId()));
			
			orderLine.setQuantity(itemHolder.getCentral().getQuantity());
			orderLine.setTotalPrice(itemHolder.getTotalPrice());
			
			orderLines.add(orderLine);
		}
		
		orderLineRepository.insertMany(orderLines);
		cartService.resetCartByAccountId(accountId);
		
		return orderId;
	}
}
