package spring.holder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.model.Account;
import spring.model.Role;

@NoArgsConstructor
@Getter
@Setter
public class AccountHolder extends Holder<Account> {
	
	// Role 1 - M account
    private Role role;
    
    // Cart stuff really
    private int numItemsInCart;
}
