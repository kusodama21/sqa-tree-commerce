package spring.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.form.account.AccountRegisterForm;
import spring.model.Account;
import spring.repository.AccountRepository;

@Component
public class AccountValidator {
	
	@Autowired
	private AccountRepository accountRepository;
	
	public String validateRegisterForm(AccountRegisterForm form) {
		// Validate name
		String username = form.getUsername().trim();
		
		// Name C1: Length
		final int usernameLen = username.length();
		if (4 > usernameLen || usernameLen > 20)
			return "The username length is not valid";
		
		// Name C2: Characters
		for (int i = 0; i < usernameLen; ++i)
			if (!Character.isLetterOrDigit(username.charAt(i)))
				return "The username can only contain English letters and digits";
		
		// Name C3: Beginning character
		if (!Character.isLetter(username.charAt(0)))
			return "The username first character has to be a letter";
		
		// Name C4: Unique username (Should turn to boolean)
		Account account = accountRepository.getOneByUsername(username);
		if (account != null)
			return "The username must be unique";
		
		// Validate password
		String password = form.getPassword();
		
		// Password C1: Length
		final int passwordLen = password.length();
		if (6 > passwordLen || passwordLen > 20)
			return "The password length is not valid";
		
		// Password C2 & C3: Characters
		boolean hasUpper = false, hasLower = false, hasDigit = false;
		for (int i = 0; i < passwordLen; ++i) {
			char c = password.charAt(i);
			if (!Character.isLetterOrDigit(c))
				return "The password can only contain English letters and digits";
			if ('a' <= c && c <= 'z')
				hasLower = true;
			if ('A' <= c && c <= 'Z')
				hasUpper = true;
			if ('0' <= c && c <= '9')
				hasDigit = true;
		}
		if (!(hasUpper && hasLower && hasDigit))
			return "The password contain at least one lowercase, one uppercase character, one number";
		
		// Validate fullname
		String fullname = form.getUsername().trim();
		
		// Fullname C1: Length
		final int fullnameLen = fullname.length();
		if (fullname.isEmpty() || fullnameLen > 50)
			return "The full name length is invalid";
		
		// Validate address
		String address = form.getAddress().trim();
		
		// Address C1: Length
		if (address.isEmpty())
			return "The address can not be empty";
		
		return null;
	}
}
