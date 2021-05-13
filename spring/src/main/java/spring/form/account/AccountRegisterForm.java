package spring.form.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AccountRegisterForm {
	private String username, password, fullname, address;
}
