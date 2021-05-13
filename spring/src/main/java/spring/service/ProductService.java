package spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.holder.ProductHolder;
import spring.model.Product;
import spring.repository.CategoryRepository;
import spring.repository.ProductRepository;

@Component
public class ProductService extends Service<Product, ProductRepository, ProductHolder> {

    @Autowired
    private CategoryRepository categoryRepository;


    public ProductHolder getOne(Product product) {
        ProductHolder holder = new ProductHolder();
        holder.setCentral(product);
        
        Optional<Integer> categoryId = product.getCategoryId();
        if (categoryId.isPresent())
        	holder.setCategory(categoryRepository.getOneById(categoryId.get()));
        
        return holder;
    }
    
    public List<ProductHolder> getMany(List<Product> products) {
    	List<ProductHolder> holders = new ArrayList<>();
        for (Product product : products)
        	holders.add(getOne(product));
        return holders;
    }


    public List<ProductHolder> index() {
        return getMany(centralRepository.index());
    }


    public ProductHolder getOneById(int id) {
    	Product product = centralRepository.getOneById(id);
        return getOne(product);
    }

    
    public List<ProductHolder> getManyByName(String name) {
        return getMany(centralRepository.getManyByName(name));
    }
}
