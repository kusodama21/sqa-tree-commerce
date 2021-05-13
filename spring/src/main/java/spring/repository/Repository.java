package spring.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public abstract class Repository<T> {

    @Autowired
    protected JdbcTemplate template;

    @Autowired
    protected RowMapper<T> mapper;
    
    // List of short cut methods
    protected T querySingle(String query, Object[] args) {
        try {
            return args == null ? template.queryForObject(query, mapper) : template.queryForObject(query, mapper, args);
        } catch (IncorrectResultSizeDataAccessException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    protected T querySingle(String query) {
        return querySingle(query, null);
    }
    
    protected List<T> queryMany(String query, Object[] args) {
    	return template.query(query, mapper, args);
    }
    
    protected List<T> queryMany(String query) {
    	return template.query(query, mapper);
    }
    
    protected boolean updateSingle(String query, Object[] args) {
    	try {
    		return template.update(query, args) > 0;
    	} catch (DataAccessException e) {
    		return false;
    	}
    }
}
