package spring.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Category {
	
	public static final int NAME_MIN_LENGTH = 3, NAME_MAX_LENGTH = 30;
	
	private int id;
	private String name;
}
