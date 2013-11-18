package com.desktop.listener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 静态化spring上下文
 * @author wenyou
 * <br />
 * 2013年11月18日  下午5:02:48
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext ac)
			throws BeansException {
		applicationContext = ac;
	}
	
	/**
	 * 得到spring上下文对象
	 * 
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 得到spring注入的bean实例
	 * 
	 * @param beanName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		return (T) applicationContext.getBean(beanName);
	}

}
