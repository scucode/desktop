Ext.define("core.rbac.role.controller.RolePermController", {
	extend : 'Ext.app.Controller',
	mixins : {
		suppleUtil:"core.util.SuppleUtil",
		messageUtil:"core.util.MessageUtil",
		formUtil:"core.util.FormUtil",
		treeUtil:"core.util.TreeUtil",
		gridActionUtil:"core.util.GridActionUtil"
    },

	init : function() {
		var self = this;
		this.control({
			/**
			 * 角色树点击事件
			 */
			"panel[xtype=role.roletree]" : {
				itemclick : function(tree,record,item,index,e,eOpts) {
					var mainLayout=tree.up("panel[xtype=role.mainlayout]");
					var roleForm=mainLayout.down("panel[xtype=role.roleform]");
					var roleTree=mainLayout.down("panel[xtype=role.roletree]");
					var formObj=roleForm.getForm();
					formObj.findField("roleName").setValue(record.get("text"));
					formObj.findField("roleCode").setValue(record.get("code"));
					formObj.findField("roleId").setValue(record.get("id"));
					formObj.findField("orderIndex").setValue(record.get("orderIndex"));
					var treeDel=roleTree.down("button[ref=treeDel]");
					treeDel.setDisabled(false);
					//加载人员信息
					var userGrid=mainLayout.down("panel[xtype=role.usergrid]");
					var store=userGrid.getStore();
					var proxy=store.getProxy();
					proxy.extraParams={
						roleId:record.get("id")					
					};
					store.load();
				}
			},
			
			"panel[xtype=role.roletree] button[ref=treeIns]":{
				click:function(btn){
					var tree=btn.up("panel[xtype=role.roletree]");
					var root=tree.getRootNode();
					var params={
						
					};
					var resObj=self.ajax({url:"/rbac/roleAction!doSave.do",params:params});
					if(resObj.success){
						var roleObj=resObj.obj;				
						params.parent="ROOT";
						params.id=roleObj.roleId;
						params.leaf=true;
						params.text=roleObj.roleName;
						params.code=roleObj.roleCode;
						params.icon=roleObj.icon;
						var node=root.appendChild(params);
						tree.fireEvent("itemclick",tree.getView(),node);	
					}
				}
			},
			
			"panel[xtype=role.roleform] button[ref=submit]":{
				click:function(btn){
					var deptForm=btn.up("panel[xtype=role.roleform]");
					var formObj=deptForm.getForm();
					
					var params=self.getFormValue(formObj);
					if(params.roleId!=null && params.roleId!=""){
						var resObj=self.ajax({url:"/rbac/roleAction!doUpdate.do",params:params});
						
						if(resObj.success){
							var mainLayout=deptForm.up("panel[xtype=role.mainlayout]");
							var roleTree=mainLayout.down("panel[xtype=role.roletree]");
							var node=roleTree.getSelectionModel().getSelection()[0];
							var obj=resObj.obj;
							node.set("text",obj.roleName);
							node.set("code",obj.roleCode);
							node.set("id",obj.roleId);
							node.set("orderIndex",obj.orderIndex);
							node.set("icon",obj.icon);
							node.commit();
							self.msgbox("保存成功!");
						}else{
							alert(resObj.obj);
						}
					}else{
						alert("请选中节点");
					}
					
				}
			},
			"panel[xtype=role.roletree] button[ref=treeDel]":{
				click:function(btn){
					var tree=btn.up("panel[xtype=role.roletree]");
					var records=tree.getSelectionModel().getSelection();
					if(records.length<=0){
						alert("请选中节点!");
						return;
					}
					var node=records[0];
					var resObj=self.ajax({url:"/rbac/roleAction!doRemove.do",params:{ids:node.get("id")}});
					if(resObj.success){
						tree.getStore().load();
						self.msgbox(resObj.obj);
					}else{
						alert(resObj.obj);
					}
				}				
			},
			
			"panel[xtype=role.usergrid] button[ref=addUser]":{
				click:function(btn){
					var mainLayout=btn.up("panel[xtype=role.mainlayout]");
					var roleTree=mainLayout.down("panel[xtype=role.roletree]");
					var selRoles=roleTree.getSelectionModel().getSelection();
					var userGrid=btn.up("panel[xtype=role.usergrid]");
					if(selRoles.length<=0){
						alert("请选择角色");
						return;
					}
					var role=selRoles[0];
					self.selTreeWin({
						title:"组织结构",
						multiSelect:true,
						config:{
							url:"/rbac/deptUserAction!getTree.do",
							params:{
								whereSql:" and 1=1"
							}
						},
						callback:function(win,records){
							//点击确定之后会得到选中的数据做处理
							var ids=new Array();
							if(records.length>0){
							Ext.each(records,function(rec){
								ids.push(rec.get("id"));
							});
							var resObj=self.ajax({url:"/rbac/roleAction!addUsers.do",params:{roleId:role.get("id"),ids:ids.join(",")}});
								if(resObj.success){
									var proxy=userGrid.getStore().getProxy();
									proxy.extraParams={
										roleId:role.get("id")
									}
									userGrid.getStore().load();
									self.msgbox(resObj.obj);
								}else{
									alert(resObj.obj);
								}
							}
						}
					});	
				}
			},
			"panel[xtype=role.usergrid] button[ref=removeUser]":{
				click:function(btn){
					var mainLayout=btn.up("panel[xtype=role.mainlayout]");
					var userGrid=btn.up("panel[xtype=role.usergrid]");
					var roleTree=mainLayout.down("panel[xtype=role.roletree]");
					var userGrid=btn.up("panel[xtype=role.usergrid]");
					var records=userGrid.getSelectionModel().getSelection();
					var selRoles=roleTree.getSelectionModel().getSelection();
					
					if(selRoles.length<=0){
						alert("请选择角色");
						return;
					}
					var role=selRoles[0];
					if(records.length<=0){
						alert("请选择记录");
					}
					var ids=new Array();
					Ext.each(records,function(rec){
						ids.push(rec.get("userId"));
					});
					var resObj=self.ajax({url:"/rbac/roleAction!removeUsers.do",params:{roleId:role.get("id"),ids:ids.join(",")}});
					if(resObj.success){
						var proxy=userGrid.getStore().getProxy();
						proxy.extraParams={
								roleId:role.get("id")
						}
						userGrid.getStore().load();
						self.msgbox(resObj.obj);			
					}else{
						alert(resObj.obj);
					}
				}
			}
			
			
		});
	},
	
	views:[
		"core.rbac.role.view.MainLayout",
		"core.rbac.role.view.RoleTree",
		"core.rbac.role.view.CenterLayout",
		"core.rbac.role.view.RoleForm",
		"core.rbac.role.view.UserGrid"
	],
	stores:[
		"core.rbac.role.store.RoleStore",
		"core.rbac.role.store.UserStore"
	]
});