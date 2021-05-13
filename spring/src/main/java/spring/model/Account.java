package spring.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Account {
	private int id, roleId;
	private String username, password, fullname, address;
}
