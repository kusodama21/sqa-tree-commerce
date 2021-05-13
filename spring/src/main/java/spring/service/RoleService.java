package spring.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.holder.RoleHolder;
import spring.model.Account;
import spring.model.Role;
import spring.repository.AccountRepository;
import spring.repository.RoleRepository;

@Component
public class RoleService extends Service<Role, RoleRepository, RoleHolder> {

    @Autowired
    private AccountRepository accountRepository;
    
    public RoleHolder getOne(Role role) {
    	RoleHolder holder = new RoleHolder();
        holder.setCentral(role);
        
        List<Account> accounts = accountRepository.getManyByRoleId(role.getId());
        holder.setAccounts(accounts);
        
        return holder;
    }


    public RoleHolder getOneById(int id) {
        Role role = centralRepository.getOneById(id);
        return getOne(role);
    }
    
    public List<RoleHolder> index() {
    	List<RoleHolder> holders = new ArrayList<>();
    	for (Role role : centralRepository.index())
    		holders.add(getOne(role));
    	return holders;
    }
}
