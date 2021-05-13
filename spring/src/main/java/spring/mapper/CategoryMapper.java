package spring.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import spring.model.Category;

@Component
public class CategoryMapper implements RowMapper<Category>{

	public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
		Category category = new Category();
		
		int id = rs.getInt("id");
		category.setId(id);
		
		String name = rs.getString("name");
		category.setName(name);
		
		return category;
	}
}
