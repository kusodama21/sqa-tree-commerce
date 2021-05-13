package spring.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import spring.model.Category;

@Component
public class CategoryRepository extends Repository<Category> {

	// INDEX
	public List<Category> index() {
		String query = "SELECT * FROM category";
		return queryMany(query);
	}

	// GET ONE BY ID - WRAPPER CLASS
	public Category getOneById(Integer id) {
		String query = "SELECT * FROM category WHERE id = ?";
		return querySingle(query, new Object[] {id});
	}

	
	// INSERT ONE
	public boolean insertOne(Category t) {
		String query = "INSERT INTO category (name) VALUES (?)";
		return updateSingle(query, new Object[] {t.getName()});
	}

	
	// UPDATE ONE
	public boolean updateOne(Category t) {
		String query = "UPDATE category SET name = ? WHERE id = ?";
		return updateSingle(query, new Object[] {t.getName(), t.getId()});
	}

	
	// DELETE ONE BY ID - WRAPPER CLASS
	public boolean deleteOneById(Integer id) {
		String query = "DELETE FROM category WHERE id = ?";
		return updateSingle(query, new Object[] {id});
	}
	
	// CHECK ONE BY NAME
	public boolean checkOneByName(String name) {
		String query = "SELECT EXISTS (SELECT 1 FROM category WHERE name = ?)";
		return template.queryForObject(query, Boolean.class, new Object[] {name});
	}
	
	// CHECK ONE BY NAME EXCLUDE ID
	public boolean checkOneByNameExcludeId(String name, int id) {
		String query = "SELECT EXISTS (SELECT 1 FROM category WHERE name = ? AND id != ?)";
		return template.queryForObject(query, Boolean.class, new Object[] {name, id});
	}
	
	// SELECT RANDOM AS IT MUST HAS AT LEAST ONE PRODUCT - INNER JOIN - NOT OPTIMIZED
	public List<Category> getManyAsRandomNonEmpty(int quantity) {
		String query = "SELECT c.* FROM category AS c INNER JOIN product AS p ON c.id = p.categoryId GROUP BY c.id ORDER BY RAND() LIMIT ?";
		return queryMany(query, new Object[] {quantity});
	}
}
