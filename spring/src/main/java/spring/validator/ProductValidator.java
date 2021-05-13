package spring.validator;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import spring.Utilities;
import spring.form.admin.ProductEditForm;
import spring.form.admin.ProductAddForm;
import spring.model.Product;
import spring.repository.ProductRepository;

@Component
public class ProductValidator {
	
	// SUCCESS SECTION
	private static final int NONE_VIOLATE_CODE = -1;
	public static final String PRODUCT_ADD_SUCCESS_MSG = "The product has been added successfully!",
			PRODUCT_EDIT_SUCCESS_MSG = "The product has been edited successfully!";
	
	
	// NAME ERRORS SECTION
	public static String[] nameErrors = {
			"The length of name must be between " + Product.NAME_MIN_LENGTH + " and " + Product.NAME_MAX_LENGTH + " characters",
			"The name must be unique"
	};
	
	private static final int NAME_LENGTH_VIOLATE_CODE = 0, 
			NAME_UNIQUE_VIOLATE_CODE = 1;
	
	
	// PRICE ERRORS SECTION
	public static String[] priceErrors = {
			"The value of price must be between " + Product.PRICE_MIN_VALUE + " and " + Product.PRICE_MAX_VALUE,
			"The step of price must only stay at " + Product.PRICE_STEP_VALUE,
			"The format of the price must be correct as in numbers"
	};
	
//	private static final int PRICE_RANGE_VIOLATE_CODE = 0,
//			PRICE_STEP_VIOLATE_CODE = 1,
//			PRICE_FORMAT_VIOLATE_CODE = 2;
	
	
	// IMAGE ERRORS SECTION
	public static String imageError = "The uploaded image is invalid, please check again";
	
	
	// DESCRIPTION ERRORS SECTION
	public static String[] descriptionErrors = {
			"The description should be left empty or must contain at least " + Product.DESCRIPTION_MIN_LENGTH + " characters"
	};
	
	private static final int DESCRIPTION_LENGTH_VIOLATE_CODE = 0;
	
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private Utilities utilities;
	
	
	// B-Minor: Check name length
	private boolean checkNameLength(String name) {
		final int len = name.length();
		return Product.NAME_MIN_LENGTH <= len && len <= Product.NAME_MAX_LENGTH;
	}
	
	
	// B-Minor: Check description length (Integer max value will be considered to be the upper infinity threshold)
	private boolean checkDescriptionLength(String description) {
		if (description.isEmpty())
			return true;
		final int len = description.length();
		return len >= Product.DESCRIPTION_MIN_LENGTH;
	}
	
	
	// A-Minor: Validate Name (Add)
	private int validateName(String name) {
		
		// Name: Check length constraint
		if (!checkNameLength(name))
			return NAME_LENGTH_VIOLATE_CODE;
		
		// Name: Check unique constraint
		if (productRepository.checkOneByName(name))
			return NAME_UNIQUE_VIOLATE_CODE;
		
		// If no problem then return null
		return NONE_VIOLATE_CODE;
	}
	
	
	// A-Minor: Validate Name (Edit)
	private int validateName(String name, int id) {
		
		// Name: Check length constraint
		if (!checkNameLength(name))
			return NAME_LENGTH_VIOLATE_CODE;
		
		// Name: Check unique constraint
		if (productRepository.checkOneByNameExcludeId(name, id))
			return NAME_UNIQUE_VIOLATE_CODE;
		
		return NONE_VIOLATE_CODE;
	}

	
	// A-Minor: Validate Description
	private int validateDescription(String description) {
		
		// Description: Check length constraint
		if (!checkDescriptionLength(description))
			return DESCRIPTION_LENGTH_VIOLATE_CODE;
		
		return NONE_VIOLATE_CODE;
	}

	
	// Major: Validate Add Form
	public String validateAddForm(ProductAddForm form) throws IOException {
		
		// Get result from name, if violate, return msg
		String name = form.getName().trim();
		final int nameResult = validateName(name);
		if (nameResult != NONE_VIOLATE_CODE)
			return nameErrors[nameResult];
		
		// DO NOT VALIDATE PRICE, IT HAS BEEN HANDLED BY PURE HTML (Will test on autoNumeric.js)
		
		// Get result from image, if violate, return msg
		MultipartFile image = form.getImage();
		if (!image.isEmpty() && !utilities.checkImageFileValidity(image))
			return imageError;
		
		// Get result from description, if violate, return msg
		String description = form.getDescription().trim();
		final int descriptionResult = validateDescription(description);
		if (descriptionResult != NONE_VIOLATE_CODE)
			return descriptionErrors[descriptionResult];
		
		// DO NOT VALIDATE CATEGORY ID, IT HAS BEEN HANDLED BY OPTIONAL
		
		// If no problem return null
		return null;
	}
	
	// Major: Validate Edit Form
	public String validateEditForm(ProductEditForm form) throws IOException {
		
		// Get the id
		final int id = form.getId();
		
		// Get result from name, if violate. return msg
		String name = form.getName().trim();
		final int nameResult = validateName(name, id);
		if (nameResult != NONE_VIOLATE_CODE)
			return nameErrors[nameResult];
		
		// DO NOT VALIDATE PRICE, IT HAS BEEN HANDLED BY PURE HTML (Will test on autoNumeric.js)
		
		// Get image action to decide, if none then do nothing, else proceed
		final int imageAction = form.getImageAction();
		if (imageAction == ProductEditForm.IMAGE_ACTION_CHANGE_CODE) {
			MultipartFile newImage = form.getNewImage();
			if (!newImage.isEmpty() && !utilities.checkImageFileValidity(newImage))
				return imageError;
		}
		
		// Get result from description, if violate, return msg
		String description = form.getDescription().trim();
		final int descriptionResult = validateDescription(description);
		if (descriptionResult != NONE_VIOLATE_CODE)
			return descriptionErrors[descriptionResult];
				
		// DO NOT VALIDATE CATEGORY ID, IT HAS BEEN HANDLED BY OPTIONAL
				
		// If no problem return null
		return null;
	}
}
