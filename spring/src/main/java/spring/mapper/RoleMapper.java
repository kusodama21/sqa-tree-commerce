package spring.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import spring.model.Role;

@Component
public class RoleMapper implements RowMapper<Role>{

	public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
		Role role = new Role();
		
		int id = rs.getInt("id");
		role.setId(id);
		
		String name = rs.getString("name");
		role.setName(name);
		
		return role;
	}
}
