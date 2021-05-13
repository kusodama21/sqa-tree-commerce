package spring.holder;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.model.Account;
import spring.model.Role;

@NoArgsConstructor
@Getter
@Setter
public class RoleHolder extends Holder<Role> {
	
	// Account M - 1 role
    private List<Account> accounts;
}
