package com.desktop.rbac.action;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.desktop.action.BaseAction;
import com.desktop.constant.NodeType;
import com.desktop.model.BaseEntity;
import com.desktop.rbac.model.Department;
import com.desktop.utils.StringUtil;

@Controller("deptAction")
@Scope("prototype")
public class DeptAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger=Logger.getLogger(DeptAction.class);
	
	private Department department=new Department();
	
	@Override
	public Object getModel() {
		String parentId=request.getParameter("parentId");
		if(StringUtil.isEmpty(parentId)){
			parentId=NodeType.ROOT;
		}
		Department parent=new Department();
		parent.setDeptId(parentId);
		department.setParent(parent);
		return department;
	}
	
	public void doSave(){
		Object entity=getModel();
		try{
			if(entity instanceof BaseEntity){
				buildModelCreateInfo((BaseEntity)entity);
			}else{
				logger.error("实体信息获取错误");
				toWrite(jsonBuilder.returnFailureJson("'传入的实体信息错误'"));
				return;
			}
			//构建创建信息
			
			//保存实体
			entity=commonService.save(entity);
			Department dept=(Department) entity;
			Department parent=(Department) commonService.findById(Department.class, dept.getParent().getDeptId());
			if(!parent.getDeptId().equals(NodeType.ROOT)){
				parent.setNodeType(NodeType.GENERAL);
				commonService.update(parent);
			}
			toWrite(jsonBuilder.returnSuccessJson(jsonBuilder.toJson(entity)));
		}catch(Exception e){
			logger.error("保存方法出错，错误信息"+e.getMessage());
			toWrite(jsonBuilder.returnFailureJson("'保存方法出错，错误信息"+e.getMessage()+"'"));
		}
	}

}
