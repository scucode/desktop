package com.desktop.rbac.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.desktop.action.BaseAction;
import com.desktop.rbac.model.Role;

@Controller
@Scope("prototype")
public class RoleAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private Role role = new Role();
	
	@Override
	public Object getModel() {
		return role;
	}

}
