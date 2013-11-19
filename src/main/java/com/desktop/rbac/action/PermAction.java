package com.desktop.rbac.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.desktop.action.BaseAction;
import com.desktop.rbac.model.Permission;

@Controller("permAction")
@Scope("prototype")
public class PermAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private Permission permission=new Permission();
	
	@Override
	public Object getModel() {
		return permission;
	}
}
