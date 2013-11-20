package com.desktop.rbac.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.desktop.action.BaseAction;
import com.desktop.model.extjs.JSONTreeNode;
import com.desktop.rbac.model.EndUser;
import com.desktop.rbac.model.Role;
import com.desktop.utils.StringUtil;

@Controller
@Scope("prototype")
public class RoleAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private Role role = new Role();
	
	@Override
	public Object getModel() {
		return role;
	}
	
	/**
	 * 加载角色树
	 */
	@SuppressWarnings("unchecked")
	public void getTree(){
		List<Role> roles= (List<Role>) commonService.queryByHql(" from Role where 1=1 "+whereSql+" "+orderSql);
		List<JSONTreeNode> lists=new ArrayList<JSONTreeNode>();
		for(Role r:roles){
			JSONTreeNode node=new JSONTreeNode();
			node.setId(r.getRoleId());
			node.setText(r.getRoleName());
			node.setCode(r.getRoleCode());
			node.setLeaf(true);
			node.setNodeInfo("ROLE");
			node.setIcon(r.getIcon());
			node.setOrderIndex(r.getOrderIndex());
			lists.add(node);
		}
		strData=jsonBuilder.buildList(lists, excludes);	
		toWrite(strData);
	}
	
	/**
	 * 根据角色加载用户
	 */
	public void loadUsers(){
		Set<EndUser> users=new HashSet<EndUser>();
		if(StringUtil.isNotEmpty(role.getRoleId())){
			Role r = (Role) commonService.findById(Role.class, role.getRoleId());
			if(r != null) {
				users = r.getUsers();
			}
		}
		toWrite(jsonBuilder.buildObjListToJson((long)users.size(), users, true));
	}
	
	/**
	 * 为角色添加用户
	 */
	@SuppressWarnings("unchecked")
	public void addUsers(){
		String[] idArray=ids.split(",");
		String roleId=role.getRoleId();
		if(StringUtil.isNotEmpty(roleId)){
			for(String id:idArray){
				List<Object> objs=(List<Object>) commonService.queryBySql(" select count(*) from ROLE_USER where ROLEID='"+roleId+"' and USERID='"+id+"'");
				if(objs!=null && objs.size()>0 && objs.get(0)!=null && Integer.parseInt(objs.get(0).toString())>0){					
				}else{
					commonService.executeSql(" insert into ROLE_USER(ROLEID,USERID) values('"+roleId+"','"+id+"')");
				}
			}
			toWrite(jsonBuilder.returnSuccessJson("'添加成功'"));
		}else{
			toWrite(jsonBuilder.returnFailureJson("'传入角色主键错误！'"));
		}
	}
	
	/**
	 * 移除角色中的用户
	 */
	public void removeUsers(){
		String[] idArray=ids.split(",");
		String roleId=role.getRoleId();
		if(StringUtil.isNotEmpty(roleId)){
			String idsWhere=StringUtil.fromArrayToStr(idArray);
			commonService.executeSql("delete ROLE_USER where ROLEID='"+roleId+"' and USERID in ("+idsWhere+")");
			toWrite(jsonBuilder.returnSuccessJson("'移除成功'"));
		}else{
			toWrite(jsonBuilder.returnFailureJson("'传入角色主键错误！'"));
		}
	}

}
