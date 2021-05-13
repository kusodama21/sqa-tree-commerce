package spring.controller.admin;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import spring.converter.ProductConverter;
import spring.form.admin.ProductAddForm;
import spring.form.admin.ProductEditForm;
import spring.model.Category;
import spring.model.Product;
import spring.repository.CategoryRepository;
import spring.repository.ProductRepository;
import spring.service.ProductService;
import spring.validator.ProductValidator;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductValidator productValidator;
	
	@Autowired
	private ProductConverter productConverter;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping(value = "/index")
	public ModelAndView getProductIndex() {
		ModelAndView mav = new ModelAndView("admin/products/index");
		mav.addObject("productHolders", productService.index());
		return mav;
	}
	
	@GetMapping(value = "/add")
	public ModelAndView getProductAddForm(
			@RequestParam(name = "reload", required = false, defaultValue = "false") boolean reload) {
		
		// Get the view
		ModelAndView mav = new ModelAndView("admin/products/add");
		
		// If the reload param is triggered, the form object will be passed from another method, else initialize new
		if (!reload)
			mav.addObject("form", new ProductAddForm());
		
		// Load Constraints of Product
		mav.addObject("productPriceMinValue", Product.PRICE_MIN_VALUE);
		mav.addObject("productPriceMaxValue", Product.PRICE_MAX_VALUE);
		mav.addObject("productPriceStepValue", Product.PRICE_STEP_VALUE);
		
		// Load Options from Categories
		List<Category> categories = categoryRepository.index();
		mav.addObject("categories", categories);
		
		return mav;
	}
	
	@PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public RedirectView processProductAddForm(
			@ModelAttribute(name = "form") ProductAddForm form, 
			RedirectAttributes redirectAttributes) throws IOException {
		
		// Validate the product, if there is message then return it
		String msg = productValidator.validateAddForm(form);
		if (msg != null) {
			
			// Get directed to add page with reload triggered
			RedirectView redirectView = new RedirectView("add?reload=true", true, true, false);
			
			// Add form to it
			redirectAttributes.addFlashAttribute("form", form);
			
			// Add message
			redirectAttributes.addFlashAttribute("msg", msg);
			
			return redirectView;
		}
		
		// Call the converter to get product from form, then save
		Product product = productConverter.convertAddForm(form);
		boolean done = productRepository.insertOne(product);
		
		// Get directed to add page with reload triggered
		RedirectView redirectView = new RedirectView("/admin/products/index", true, true, false);
		
		// Add success message
		redirectAttributes.addFlashAttribute("msg", done ? ProductValidator.PRODUCT_ADD_SUCCESS_MSG : "Something wrong");
		
		return redirectView;
	}
	
	@GetMapping(value = "/edit")
	public ModelAndView getProductEditForm(
			@RequestParam(name = "id", required = true) int productId, 
			@RequestParam(name = "reload", required = false, defaultValue = "false") boolean reload) {
		ModelAndView mav = new ModelAndView("admin/products/edit");
		
		
		if (!reload) {
			// Get product
			Product product = productRepository.getOneById(productId);
			
			// Initialize new form
			ProductEditForm form = new ProductEditForm();
			
			// Set ID
			form.setId(product.getId());
			
			// Set name
			form.setName(product.getName());
			
			// Set price
			form.setPrice(product.getPrice());
			
			// Set old image
			form.setOldImage(product.getImage());
			
			// Set description
			form.setDescription(product.getDescription());
			
			// Set cateogoryId
			form.setCategoryId(product.getCategoryId());
			
			// Add form to view
			mav.addObject("form", form);
		}
		
		// Load Constraints of Product
		mav.addObject("productNameMinLength", Product.NAME_MIN_LENGTH);
		mav.addObject("productNameMaxLength", Product.NAME_MAX_LENGTH);
				
		mav.addObject("productPriceMinValue", Product.PRICE_MIN_VALUE);
		mav.addObject("productPriceMaxValue", Product.PRICE_MAX_VALUE);
		mav.addObject("productPriceStepValue", Product.PRICE_STEP_VALUE);
				
		mav.addObject("productDescriptionMinLength", Product.DESCRIPTION_MIN_LENGTH);
		
		// Load image actions
		mav.addObject("imageActions", ProductEditForm.imageActions);
		
		// Load category options
		List<Category> categories = categoryRepository.index();
		mav.addObject("categories", categories);
		
		return mav;
	}
	
	@PostMapping(value = "/edit/gate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public RedirectView processProductEditForm(
			@ModelAttribute(name = "form") ProductEditForm form, 
			RedirectAttributes redirectAttributes) throws IOException {
		
		//Validate form to see if it returns wrong
		String msg = productValidator.validateEditForm(form);
		if (msg != null) {
			// Get the right link
			String link = "/admin/products/edit?id=" + form.getId() + "&reload=true";
			
			// Get directed to add page with reload triggered
			RedirectView redirectView = new RedirectView(link, true, true, false);
						
			// Add form to it
			redirectAttributes.addFlashAttribute("form", form);
						
			// Add message
			redirectAttributes.addFlashAttribute("msg", msg);
						
			return redirectView;
		}
		
		// Call the converter to get product from form, then save
		Product product = productConverter.convertEditForm(form);
		boolean done = productRepository.updateOne(product);
				
		// Get directed to add page with reload triggered
		RedirectView redirectView = new RedirectView("/admin/products/index", true, true, false);
				
		// Add success message
		redirectAttributes.addFlashAttribute("msg", done ? ProductValidator.PRODUCT_EDIT_SUCCESS_MSG : "Something wrong");
				
		return redirectView;
	}
	
	@GetMapping(value = "/delete")
	public RedirectView deleteProduct(
			@RequestParam(name = "id", required = true) int productId,
			RedirectAttributes redirectAttributes) {
		RedirectView redirectView = new RedirectView("/admin/products/index", true, true, false);
		boolean done = productRepository.deleteOneById(productId);
		
		redirectAttributes.addFlashAttribute("msg", done ? "A product has been successfully removed" : "Delete operation failed");
		return redirectView;
	}
}
