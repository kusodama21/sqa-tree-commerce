package spring.controller.admin;

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

import spring.converter.CategoryConverter;
import spring.form.admin.CategoryAddForm;
import spring.form.admin.CategoryEditForm;
import spring.model.Category;
import spring.repository.CategoryRepository;
import spring.validator.CategoryValidator;
import spring.validator.ProductValidator;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {
	
	@Autowired
	private CategoryValidator categoryValidator;
	
	@Autowired
	private CategoryConverter categoryConverter;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping(value = "/index")
	public ModelAndView getCategoryIndex() {
		ModelAndView mav = new ModelAndView("admin/categories/index");
		mav.addObject("categories", categoryRepository.index());
		return mav;
	}

	@GetMapping(value = "/add")
	public ModelAndView getCategoryAddForm(@RequestParam(name = "reload", required = false, defaultValue = "false") boolean reload) {
		ModelAndView mav = new ModelAndView("admin/categories/add");
		
		if (!reload) {
			mav.addObject("form", new CategoryAddForm());
		}
		
		// Return view
		return mav;
	}
	
	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public RedirectView processCategoryAddForm(
			@ModelAttribute(name = "form") CategoryAddForm form, 
			RedirectAttributes redirectAttributes) {
		
		// Get msg, if not null, redirect back
		String msg = categoryValidator.validateAddForm(form);
		if (msg != null) {
			RedirectView redirectView = new RedirectView("/admin/categories/add?reload=true", true, true, false);
			
			redirectAttributes.addFlashAttribute("form", form);
			redirectAttributes.addFlashAttribute("msg", msg);
			
			return redirectView;
		}
		
		Category category = categoryConverter.convertAddForm(form);
		boolean done = categoryRepository.insertOne(category);
		
		RedirectView redirectView = new RedirectView("/admin/categories/index", true, true, false);
		
		redirectAttributes.addFlashAttribute("msg", done ? CategoryValidator.CATEGORY_ADD_SUCCESS_MSG : "Save operation failed");
		
		return redirectView;
	}
	
	@GetMapping(value = "/edit")
	public ModelAndView getCategoryEditForm(
			@RequestParam(name = "id", required = true) int categoryId,
			@RequestParam(name = "reload", required = false, defaultValue = "false") boolean reload) {
		ModelAndView mav = new ModelAndView("admin/categories/edit");
		
		if (!reload) {
			Category category = categoryRepository.getOneById(categoryId);
			CategoryEditForm form = new CategoryEditForm();
			
			form.setId(category.getId());
			form.setName(category.getName());
			
			mav.addObject("form", form);
		}
				
		// Return view
		return mav;
	}
	
	@PostMapping(value = "/edit/gate")
	public RedirectView processCategoryEditForm(
			@ModelAttribute(name = "form") CategoryEditForm form,
			RedirectAttributes redirectAttributes) {
		
		// Get msg, if null do nothing, else redirect back
		String msg = categoryValidator.validateEditForm(form);
		if (msg != null) {
			String link = "/admin/categories/edit?id=" + form.getId() + "&reload=true";
			RedirectView redirectView = new RedirectView(link, true, true, false);
			
			redirectAttributes.addFlashAttribute("form", form);
			redirectAttributes.addFlashAttribute("msg", msg);
			
			return redirectView;
		}
		
		Category category = categoryConverter.convertEditForm(form);
		boolean done = categoryRepository.updateOne(category);
		
		RedirectView redirectView = new RedirectView("/admin/categories/index", true, true, false);
		
		redirectAttributes.addFlashAttribute("msg", done ? ProductValidator.PRODUCT_EDIT_SUCCESS_MSG : "Update operation failed");
		
		return redirectView;
	}
	
	@PostMapping(value = "/delete")
	public RedirectView deleteCategory(
			@RequestParam(name = "id", required = true) int categoryId,
			RedirectAttributes redirectAttributes) {
		RedirectView redirectView = new RedirectView("/admin/categories/index", true, true, false);
		boolean done = categoryRepository.deleteOneById(categoryId);
		
		redirectAttributes.addFlashAttribute("msg", done ? "Category deleted successfully" : "Delete operation failed");
		return redirectView;
	}
}
