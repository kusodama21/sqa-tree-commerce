package spring.form.admin;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProductAddForm {
	private String name, price, description;
	private MultipartFile image;
	private Optional<Integer> categoryId;
}
