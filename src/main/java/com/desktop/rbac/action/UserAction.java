package com.desktop.rbac.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.desktop.action.BaseAction;
import com.desktop.rbac.model.EndUser;

@Controller("userAction")
@Scope("prototype")
public class UserAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private EndUser endUser=new EndUser();
	
	@Override
	public Object getModel() {
		return endUser;
	}

}
