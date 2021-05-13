package spring.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import spring.model.Account;

@Component
public class AccountMapper implements RowMapper<Account>{

	public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
		Account account = new Account();
		
		int id = rs.getInt("id");
		account.setId(id);
		
		String username = rs.getString("username");
		account.setUsername(username);
		
		String password = rs.getString("password");
		account.setPassword(password);
		
		String fullname = rs.getString("fullname");
		account.setFullname(fullname);
		
		String address = rs.getString("address");
		account.setAddress(address);
		
		int roleId = rs.getInt("roleId");
		account.setRoleId(roleId);
		
		return account;
	}
}
