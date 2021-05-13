package spring;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Component
public class Utilities implements ServletContextAware {
	private ServletContext servletContext;
	
	public boolean checkStringLengthWithinRange(String s, final int minlength, final int maxlength) {
		final int len = s.length();
		return minlength <= len && len <= maxlength;
	}
	
	public boolean checkImageFileValidity(MultipartFile file) throws IOException {
		
		// Check extension by mime types (defined in web.xml of Tomcat)
		String name = file.getOriginalFilename();
		String mimeType = servletContext.getMimeType(name);
		if (mimeType == null || !mimeType.startsWith("image/"))
			return false;
		
		// Check if the content can be read as an image
		InputStream is = file.getInputStream();
		if (ImageIO.read(is) == null) {
			is.close();
			return false;
		}
		
		// Pass all, return true
		is.close();
		return true;
	}
	
	
	public String getExtensionFromFileName(String name) {
		final int seperator = name.lastIndexOf('.');
		return name.substring(seperator);
	}
	
	
	public String generateRandomCharacterString(final int length) {
		return new Random(System.currentTimeMillis())
				.ints('0', 'z')
				.filter(i -> Character.isLetterOrDigit(i))
				.limit(length)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();
	}
}