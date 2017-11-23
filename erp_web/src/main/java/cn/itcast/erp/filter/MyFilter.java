package cn.itcast.erp.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

public class MyFilter extends AuthorizationFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		 Subject subject = getSubject(request, response);
	     String[] perms = (String[]) mappedValue;   // ["供应商","客户"]
	     System.out.println("***************************执行的是自定的perms过滤器");
	     if (perms != null && perms.length > 0) {
	    	 for (String string : perms) {
	    		 if(subject.isPermitted(string)){
	    			 return true;
	    		 }
			}
	    	return false; 
	     }else{  // []
	    	 
	    	return true; 
	     }
	}

}
