package com.desktop.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.desktop.action.BaseAction;

/**
 * 系统初始化监听器
 * 
 * @author wenyou <br />
 *         2013年11月18日 下午5:01:32
 */
public class ContextLintener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent sc) {
	}

	@Override
	public void contextInitialized(ServletContextEvent sc) {
		BaseAction.webrootAbsPath = sc.getServletContext().getRealPath("/");
		BaseAction.absClassPath = ContextLintener.class.getResource("/")
				.getPath().substring(1);
	}
	
}
