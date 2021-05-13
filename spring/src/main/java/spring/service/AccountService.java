package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.holder.AccountHolder;
import spring.model.Account;
import spring.model.Role;
import spring.repository.AccountRepository;
import spring.repository.CartItemRepository;
import spring.repository.RoleRepository;

@Component
public class AccountService extends Service<Account, AccountRepository, AccountHolder> {

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private CartItemRepository cartItemRepository;
    
    public AccountHolder getOne(Account account) {
    	if (account == null)
    		return null;
    	
    	AccountHolder holder = new AccountHolder();
		holder.setCentral(account);
		
		Role role = roleRepository.getOneById(account.getRoleId());
		holder.setRole(role);
	
		int numItemsInCart = cartItemRepository.countItemsByAccountId(account.getId());
		holder.setNumItemsInCart(numItemsInCart);
		
		return holder;
    }
    

	public AccountHolder getOneById(int id) {
		return getOne(centralRepository.getOneById(id));
	}
	
	public AccountHolder getOneByUsername(String username) {
		return getOne(centralRepository.getOneByUsername(username));
	}
}
