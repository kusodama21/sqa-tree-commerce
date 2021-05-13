package spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import spring.holder.AccountHolder;
import spring.service.AccountService;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
	
	@Autowired
	private AccountService accountService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// Get root (servlet path)
		String root = request.getContextPath();
		
		// Get session and check for currentId
		Integer currentId = (Integer) request.getSession().getAttribute("currentId");
		
		// If currentId is null, then ask to log in
		if (currentId == null) {
			String msg = "You need to log in to continue";
			response.sendRedirect(root + "/account/login?msg=" + msg);
			return false;
		}
		
		// Get the holder
		AccountHolder accountHolder = accountService.getOneById(currentId);
		String role = accountHolder.getRole().getName(), fullPath = request.getRequestURI(), path404 = root + "/404";
			
		// If the user is admin, all routes are allowed
		if (role.equals("ROLE_ADMIN")) {
			return true;
		}
			
		// If reach here, must be customer, then the link must contain customer path, else to 404, back to shop
		String customerPath = root + "/customer";
		if (fullPath.contains(customerPath))
			return true;
		
		// If not customer the be admin, this interceptor only applies for 2
		response.sendRedirect(path404);
		return false;
	}
}
