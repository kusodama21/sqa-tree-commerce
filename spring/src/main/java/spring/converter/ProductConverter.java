package spring.converter;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import spring.Utilities;
import spring.form.admin.ProductAddForm;
import spring.form.admin.ProductEditForm;
import spring.model.Product;
import spring.repository.ProductRepository;

@Component
public class ProductConverter {
	
	public static final int IMAGE_STANDARD_NAME_LENGTH = 20;
	
	@Autowired
	private Utilities utilities;
	
	@Autowired
	private ProductRepository productRepository;
	
	public Product convertAddForm(ProductAddForm form) throws IllegalStateException, IOException {
		Product product = new Product();
		
		// Name
		product.setName(form.getName());
		
		// Price
		product.setPrice(form.getPrice());
		
		// Description
		String description = form.getDescription();
		product.setDescription(description.isEmpty() ? null : description);
		
		// Category ID
		product.setCategoryId(form.getCategoryId());
		
		// Image
		String imageName = null;
		MultipartFile image = form.getImage();
		
		if (!image.isEmpty()) {
			String extension = utilities.getExtensionFromFileName(image.getOriginalFilename());
			do {
				imageName = utilities.generateRandomCharacterString(IMAGE_STANDARD_NAME_LENGTH) + extension;
			} while (productRepository.checkOneByImage(imageName));
			File file = new File(utilities.getServletContext().getRealPath("/") + "static\\images\\products\\" + imageName);
			image.transferTo(file);
		}
		
		product.setImage(imageName);
		
		return product;
	}
	
	public Product convertEditForm(ProductEditForm form) throws IllegalStateException, IOException {
		Product product = new Product();
		
		// ID
		final int id = form.getId();
		product.setId(id);
		
		// Name
		product.setName(form.getName());
		
		// Price
		product.setPrice(form.getPrice());
		
		// Description
		String description = form.getDescription();
		product.setDescription(description.isEmpty() ? null : description);
		
		// Category ID
		product.setCategoryId(form.getCategoryId());
		
		// Image
		String imageName = null, oldImage = form.getOldImage();
		switch (form.getImageAction()) {
			case ProductEditForm.IMAGE_ACTION_NONE_CODE:
				if (!oldImage.isEmpty())
					imageName = oldImage;
				break;
			case ProductEditForm.IMAGE_ACTION_CHANGE_CODE:
				MultipartFile newImage = form.getNewImage();
				String productImageDirectory = utilities.getServletContext().getRealPath("/") + "static\\images\\products\\";
				
				if (!newImage.isEmpty()) {
					String fileName = newImage.getOriginalFilename();
					String extension = utilities.getExtensionFromFileName(fileName);
					
					do {
						imageName = utilities.generateRandomCharacterString(IMAGE_STANDARD_NAME_LENGTH) + extension;
					} while (productRepository.checkOneByImageExcludeId(imageName, id));
					
	
					File file = new File(productImageDirectory + imageName);
					newImage.transferTo(file);
				}
				
				if (!oldImage.isEmpty()) {
					File oldFile = new File(productImageDirectory + oldImage);
					if (oldFile.delete())
						System.out.println("Deleted successfully!");
					else
						System.out.println("Not deleted successfully");
				}
				break;
		}
		product.setImage(imageName);
		
		return product;
	}
}