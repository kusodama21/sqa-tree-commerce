package spring.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import spring.model.Role;

@Component
public class RoleRepository extends Repository<Role> {
	
	// INDEX
	public List<Role> index() {
		String query = "SELECT * FROM role";
		return queryMany(query);
	}

    // GET ONE BY ID - WRAPPER CLASS
    public Role getOneById(Integer id) {
        String query = "SELECT * FROM role WHERE id = ?";
        return querySingle(query, new Object[] {id});
    }
}
