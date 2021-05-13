package spring.form.admin;

import lombok.Setter;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Setter
public class ProductEditForm {
	public static final String[] imageActions = {"None", "Change"};
	public static final int IMAGE_ACTION_NONE_CODE = 0, IMAGE_ACTION_CHANGE_CODE = 1;
	
	private int id;
	private String name, price, oldImage, description;
	private int imageAction;
	private MultipartFile newImage;
	private Optional<Integer> categoryId;
}
