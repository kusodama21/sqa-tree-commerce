package spring.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {DatabaseContext.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {ServletContext.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
    
    @Override
    protected Filter[] getServletFilters() {
    	CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter("UTF-8");
		encodingFilter.setForceEncoding(true);
		
		return new Filter[] {encodingFilter};
    }
    
    @Override
    protected void customizeRegistration(Dynamic registration) {
    	MultipartConfigElement multipartConfigElement = new MultipartConfigElement("/tmp", 10 * 1024 * 1024, 12 * 1024 * 1024, 0);
    	registration.setMultipartConfig(multipartConfigElement);
    	registration.setLoadOnStartup(1);
    	registration.addMapping("/");
//    	super.customizeRegistration(registration);
    }
}