package spring.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import spring.model.Account;

@Component
public class AccountRepository extends Repository<Account> {
	
	// GET LAST INSERTED ID
	private int getLastInsertedId() {
		String query = "SELECT LAST_INSERT_ID()";
		return template.queryForObject(query, Integer.class);
	}

	
	// GET ONE BY ID - WRAPPER CLASS
	public Account getOneById(Integer id) {
		String query = "SELECT * FROM account WHERE id = ?";
		return querySingle(query, new Object[] {id});
	}

	
	// INSERT ONE
	private boolean insertOne(Account t) {
		String query = "INSERT INTO account ("
				+ "username, "
				+ "password, "
				+ "fullname, "
				+ "address, "
				+ "roleId"
				+ ") VALUES (?, ?, ?, ? ,?)";
		Object[] args = {t.getUsername(), t.getPassword(), t.getFullname(), t.getAddress(), t.getRoleId()};
		
		return updateSingle(query, args);
	}

	
	// UPDATE ONE
	public boolean updateOne(Account t) {
		String query = "UPDATE account SET "
				+ "username = ?, "
				+ "password = ?, "
				+ "fullname = ?, "
				+ "address = ?, "
				+ "roleId = ? "
				+ "WHERE id = ?";
		Object[] args = {t.getUsername(), t.getPassword(), t.getFullname(), t.getAddress(), t.getRoleId(), t.getId()};
		
		return updateSingle(query, args);
	}

	
	// DELETE ONE BY ID - WRAPPER CLASS
	public boolean deleteOneById(Integer id) {
		String query = "DELETE FROM account WHERE id = ?";
		return updateSingle(query, new Object[] {id});
	}

	
	// GET MANY BY ROLE ID - WRAPPER CLASS
	public List<Account> getManyByRoleId(Integer roleId) {
		String query = "SELECT * FROM account WHERE roleId = ?";
		return queryMany(query, new Object[] {roleId});
	}
	
	
	// INSERT ONE COMPACT
	public int insertOneRetrieveId(Account account) {
		insertOne(account);
		return getLastInsertedId();
	}
	
	// GET ONE BY USERNAME
	public Account getOneByUsername(String username) {
		String query = "SELECT * FROM account WHERE username = ?";
		return querySingle(query, new Object[] {username});
	}
}