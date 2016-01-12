package update.center.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SecurityFilter implements Filter{
	
	
	private static final String LOGIN_PAGE = "pages/login.jsf";
	private static final String SERVER_INFO_PAGE = "pages/serverinfo.jsf";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("Entered in securitry filter...");
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		
		User u = getUserFromHttpServletRequest(httpServletRequest);
		if(u == null){
			//user is not logged and page is not login page - navigate to login page!!!!
			if(!httpServletRequest.getRequestURL().toString().contains(LOGIN_PAGE)){
				httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/" + LOGIN_PAGE);
				return;
			}
		}else{
			//there is user logged & we opened login -- goto main form
			if(httpServletRequest.getRequestURL().toString().contains(LOGIN_PAGE)){
				httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/" + SERVER_INFO_PAGE);
				return;
			}
		}
		chain.doFilter(request, response);
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	
	private static User getUserFromHttpServletRequest(HttpServletRequest httpServletRequest){
		if(httpServletRequest == null) return null;
		return  (User)httpServletRequest.getSession().getAttribute(LoginBean.USER_KEY);
	}

}
