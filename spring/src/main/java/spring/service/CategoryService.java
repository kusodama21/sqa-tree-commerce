package spring.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.holder.CategoryHolder;
import spring.model.Category;
import spring.model.Product;
import spring.repository.CategoryRepository;
import spring.repository.ProductRepository;

@Component
public class CategoryService extends Service<Category, CategoryRepository, CategoryHolder> {

    @Autowired
    private ProductRepository productRepository;


    public CategoryHolder getOne(Category category) {
        CategoryHolder holder = new CategoryHolder();
        holder.setCentral(category);
        
        List<Product> products = productRepository.getManyByCategoryId(category.getId());
        holder.setProducts(products);
        
        return holder;
    }
    
    public CategoryHolder getOneRestrictProductQuantity(Category category, int productQuantity) {
    	CategoryHolder holder = new CategoryHolder();
        holder.setCentral(category);
        
        List<Product> products = productRepository.getManyAsRandomByCategoryId(productQuantity, category.getId());
        holder.setProducts(products);
        
        return holder;
    }
    
    public CategoryHolder getOneById(int id) {
    	Category category = centralRepository.getOneById(id);
        return getOne(category);
    }


    public List<CategoryHolder> index() {
        List<CategoryHolder> holders = new ArrayList<>();
        for (Category category : centralRepository.index())
        	holders.add(getOne(category));
        return holders;
    }
    
    public List<CategoryHolder> getManyAsRandomRestrictProductQuantity(int categoryQuantity, int productQuantity) {
    	List<CategoryHolder> holders = new ArrayList<>();
    	for (Category category : centralRepository.getManyAsRandomNonEmpty(categoryQuantity))
    		holders.add(getOneRestrictProductQuantity(category, productQuantity));
    	return holders;
    }
}
