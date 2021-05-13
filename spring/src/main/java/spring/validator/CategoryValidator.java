package spring.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.form.admin.CategoryAddForm;
import spring.form.admin.CategoryEditForm;
import spring.model.Category;
import spring.repository.CategoryRepository;

@Component
public class CategoryValidator {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	// SUCCESS SECTION
	public static final int NONE_VIOLATE_CODE = -1;
	public static final String CATEGORY_ADD_SUCCESS_MSG = "The category has been created successfully!",
			CATEGORY_EDIT_SUCCESS_MSG = "The category has been edited successfully!";
	
	// NAME ERRORS SECTION
	public static String[] nameErrors = {
			"The category name must lie between " + Category.NAME_MIN_LENGTH + " and " + Category.NAME_MAX_LENGTH + " characters",
			"The category name must be unique"
	};
	
	private static final int NAME_LENGTH_VIOLATE_CODE = 0,
			NAME_UNIQUE_VIOLATE_CODE = 1;
	
	// B-Minor: Check name length
	private boolean checkNameLength(String name) {
		final int len = name.length();
		return Category.NAME_MIN_LENGTH <= len && len <= Category.NAME_MAX_LENGTH;
	}
	
	// A-Minor: Validate name (Add)
	private int validateName(String name) {
		
		// Name: Check length constraint
		if (!checkNameLength(name))
			return NAME_LENGTH_VIOLATE_CODE;
		
		// Name: Check unique constraint
		if (categoryRepository.checkOneByName(name))
			return NAME_UNIQUE_VIOLATE_CODE;
			
		return NONE_VIOLATE_CODE;
	}
	
	// A-Minor: Validate name (Edit)
	private int validateName(String name, int id) {
		
		// Name: Check length constraint
		if (!checkNameLength(name))
			return NAME_LENGTH_VIOLATE_CODE;
		
		// Name: Check unique constraint
		if (categoryRepository.checkOneByNameExcludeId(name, id))
			return NAME_UNIQUE_VIOLATE_CODE;
		
		return NONE_VIOLATE_CODE;
	}
	
	// Major: Validate add form
	public String validateAddForm(CategoryAddForm form) {
		
		// Validate name
		String name = form.getName().trim();
		final int nameResult = validateName(name);
		if (nameResult != NONE_VIOLATE_CODE)
			return nameErrors[nameResult];
		
		// If no problem return null
		return null;
	}
	
	public String validateEditForm(CategoryEditForm form) {
		
		// Get id
		final int id = form.getId();
		
		// Validate name
		String name = form.getName().trim();
		final int nameResult = validateName(name, id);
		if (nameResult != NONE_VIOLATE_CODE)
			return nameErrors[nameResult];
		
		// If no problem return null
		return null;
	}
}
