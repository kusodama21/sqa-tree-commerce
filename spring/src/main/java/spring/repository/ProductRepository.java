package spring.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import spring.model.Product;

@Component
public class ProductRepository extends Repository<Product> {

	// INDEX
	public List<Product> index() {
		String query = "SELECT * FROM product";
		return queryMany(query);
	}

	// GET ONE
	public Product getOneById(Integer id) {
		String query = "SELECT * FROM product WHERE id = ?";
		return querySingle(query, new Object[] {id});
	}

	// INSERT ONE
	public boolean insertOne(Product t) {
		String query = "INSERT INTO product ("
				+ "name, "
				+ "price, "
				+ "image, "
				+ "description, "
				+ "categoryId"
				+ ") VALUES (?, ?, ?, ?, ?)";
		
		Integer categoryId = t.getCategoryId().isPresent() ? t.getCategoryId().get() : null;
		Object[] args = {t.getName(), t.getPrice(), t.getImage(), t.getDescription(), categoryId};

		return updateSingle(query, args);
	}

	// UPDATE ONE
	public boolean updateOne(Product t) {
		String query = "UPDATE product SET "
				+ "name = ?, "
				+ "price = ?, "
				+ "image = ?, "
				+ "description = ?, "
				+ "categoryId = ? " 
				+ "WHERE id = ?";
		
		Integer categoryId = t.getCategoryId().isPresent() ? t.getCategoryId().get() : null;
		Object[] args = {t.getName(), t.getPrice(), t.getImage(), t.getDescription(), categoryId, t.getId()};

		return updateSingle(query, args);
	}

	// DELETE ONE
	public boolean deleteOneById(Integer id) {
		String query = "DELETE FROM product WHERE id = ?";
		return updateSingle(query, new Object[] {id});
	}

	// GET MANY BY NAME
	public List<Product> getManyByName(String name) {
		String query = "SELECT * FROM product WHERE LOWER(name) LIKE ?";
		return queryMany(query, new Object[] {"%" + name.toLowerCase() + "%"});
	}

	// GET MANY BY CATEGORY
	public List<Product> getManyByCategoryId(Integer categoryId) {
		String query = "SELECT * FROM product WHERE categoryId = ?";
		return queryMany(query, new Object[] {categoryId});
	}
	
	// CHECK ONE BY NAME
	public boolean checkOneByName(String name) {
		String query = "SELECT EXISTS (SELECT 1 FROM product WHERE name = ?)";
		return template.queryForObject(query, Boolean.class, new Object[] {name});
	}
	
	// CHECK ONE BY IMAGE
	public boolean checkOneByImage(String image) {
		String query = "SELECT EXISTS (SELECT 1 FROM product WHERE image = ?)";
		return template.queryForObject(query, Boolean.class, new Object[] {image});
	}
	
	// CHECK ONE BY NAME EXCLUDE ID
	public boolean checkOneByNameExcludeId(String name, int id) {
		String query = "SELECT EXISTS (SELECT 1 FROM product WHERE name = ? AND id != ?)";
		return template.queryForObject(query, Boolean.class, new Object[] {name, id});
	}
	
	// CHECK ONE BY IMAGE EXCLUDE ID
	public boolean checkOneByImageExcludeId(String name, int id) {
		String query = "SELECT EXISTS (SELECT 1 FROM product WHERE image = ? AND id != ?)";
		return template.queryForObject(query, Boolean.class, new Object[] {name, id});
	}
	
	// GET MANY AS RANDOM - NOT OPTIMIZED
	public List<Product> getManyAsRandom(int quantity) {
		String query = "SELECT * FROM product ORDER BY RAND() LIMIT ?";
		return queryMany(query, new Object[] {quantity});
	}
	
	// GET MANY AS RANDOM BY CATEGORY ID - NOT OPTIMIZED
	public List<Product> getManyAsRandomByCategoryId(int quantity, int categoryId) {
		String query = "SELECT * FROM product WHERE categoryId = ? ORDER BY RAND() LIMIT ?";
		return queryMany(query, new Object[] {categoryId, quantity});
	}
}