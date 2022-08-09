package com.shopme.setting;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;   

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shopme.common.entity.Setting;

@Component
public class SettingFilter implements Filter {
	
	@Autowired 
	private SettingService service;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// được gọi khi một yêu cầu phù hợp được gửi đến máy chủ
	    // được sử dụng để chặn Req và chuyển đổi Res
		
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		String url = servletRequest.getRequestURI().toString();
		
		//Gọi Filter tiếp theo trong chuỗi Filter (chain)
		
		if(url.endsWith(".css") || url.endsWith(".js") || url.endsWith(".png") 
				|| url.endsWith(".jpg")) {
			
			// xu ly Req tren ham doFilter
			chain.doFilter(request, response);
			// xy ly Res duoi ham doFilter
			
//			System.out.println("_"+url);
//			System.out.println("do filter duoc goi");
			return;
		}
		
		List<Setting> generalSettings = service.getGeneralSetting();
		
		generalSettings.forEach(setting -> {
			System.out.println(setting);
			request.setAttribute(setting.getKey(), setting.getValue());
		});
		
		System.out.println("main:" + url);
		
		chain.doFilter(request, response);
		
	//	System.out.println("do filter ngoai duoc goi");
		
	}

}
