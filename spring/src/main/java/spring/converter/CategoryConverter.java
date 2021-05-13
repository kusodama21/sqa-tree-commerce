package spring.converter;

import org.springframework.stereotype.Component;

import spring.form.admin.CategoryAddForm;
import spring.form.admin.CategoryEditForm;
import spring.model.Category;

@Component
public class CategoryConverter {
	
	public Category convertAddForm(CategoryAddForm form) {
		Category category = new Category();
		category.setName(form.getName());
		return category;
	}
	
	public Category convertEditForm(CategoryEditForm form) {
		Category category = new Category();
		category.setId(form.getId());
		category.setName(form.getName());
		return category;
	}
}
