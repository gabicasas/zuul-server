package com.gcc.app.zuul.server.filters;

import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class ProTiempoTranscurrido extends ZuulFilter {
	
	private static Logger log= Logger.getLogger(ProTiempoTranscurrido.class);

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		 RequestContext rc=RequestContext.getCurrentContext();
		 HttpServletRequest sr=rc.getRequest();
		 
		 long tiempoInicio=(long) sr.getAttribute("tiempoInicio");
		 log.error("TIEMPO: "+(System.currentTimeMillis()-tiempoInicio));
		 
		 
		return null;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "post";
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

}
