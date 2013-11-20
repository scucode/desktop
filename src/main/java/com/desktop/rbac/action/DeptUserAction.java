package com.desktop.rbac.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.desktop.action.BaseAction;
import com.desktop.rbac.model.view.VDeptUser;

@Controller
@Scope("prototype")
public class DeptUserAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private VDeptUser vdeptUser=new VDeptUser();
	
	@Override
	public Object getModel() {
		return vdeptUser;
	}

}
