package spring.service;

import org.springframework.beans.factory.annotation.Autowired;

import spring.holder.Holder;
import spring.repository.Repository;

public abstract class Service<T, R extends Repository<T>, H extends Holder<T>> {
	
	@Autowired
	protected R centralRepository;
}
