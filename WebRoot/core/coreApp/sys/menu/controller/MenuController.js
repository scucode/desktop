Ext.define("core.sys.menu.controller.MenuController",{
	extend: 'Ext.app.Controller',
	mixins: {
		suppleUtil:"core.util.SuppleUtil",
		messageUtil:"core.util.MessageUtil",
		formUtil:"core.util.FormUtil",
		treeUtil:"core.util.TreeUtil",
		gridActionUtil:"core.util.GridActionUtil"
	},
	
	init : function() {
		var self = this;
		
		this.control({
			"panel[xtype=menu.menutree]":{
				itemclick:function(tree,record,item,index,e,eOpts){
					var mainLayout=tree.up("panel[xtype=menu.mainlayout]");
					var deptForm=mainLayout.down("panel[xtype=menu.menuform]");
					var deptTree=mainLayout.down("panel[xtype=menu.menutree]");
					var formObj=deptForm.getForm();
					formObj.findField("menuName").setValue(record.get("text"));
					formObj.findField("moduleCode").setValue(record.get("code"));
					formObj.findField("menuId").setValue(record.get("id"));
					formObj.findField("nodeInfo").setValue(record.get("nodeInfo"));
					formObj.findField("nodeInfoType").setValue(record.get("nodeInfoType"));
					formObj.findField("icon").setValue(record.get("icon"));
					formObj.findField("bigIcon").setValue(record.get("bigIcon"));
					formObj.findField("orderIndex").setValue(record.get("orderIndex"));
					formObj.findField("parentId").setValue(record.get("parent"));
					var treechildIns=deptTree.down("button[ref=treechildIns]");
					treechildIns.setDisabled(false);
					var treeDel=deptTree.down("button[ref=treeDel]");
					treeDel.setDisabled(false);
				}
			},
			
			"panel[xtype=menu.menutree] button[ref=treeIns]":{
				click:function(btn){
					var tree=btn.up("panel[xtype=menu.menutree]");
					var root=tree.getRootNode();
					var params={
						layer:root.getDepth()+1,
						nodeInfoType:"MENU",
						parentId:root.get("id"),
						nodeType:"LEAF"
					}
					var resObj=self.ajax({url:"/pc/menuAction!doSave.do",params:params});
					if(resObj.success){
						var menuObj=resObj.obj;
						params.parent=params.parentId;
						params.id=menuObj.menuId;
						params.nodeInfoType=menuObj.nodeInfoType;
						params.leaf=true;
						var node=root.appendChild(params);
						tree.fireEvent("itemclick",tree.getView(),node);	
					}
				}
			},
			"panel[xtype=menu.menutree] button[ref=treechildIns]":{
				click:function(btn){
					var tree=btn.up("panel[xtype=menu.menutree]");
					var records=tree.getSelectionModel().getSelection();
					if(records.length<=0){
						alert("请选中节点!");
						return;
					}
					var parent=records[0];
					var params={
						layer:parent.getDepth()+1,
						nodeInfoType:"MENU",
						parentId:parent.get("id"),
						nodeType:"LEAF"
					}
					var resObj=self.ajax({url:"/pc/menuAction!doSave.do",params:params});
					if(resObj.success){
						var menuObj=resObj.obj;
						params.parent=params.parentId;
						params.id=menuObj.menuId;
						params.nodeInfoType=menuObj.nodeInfoType;
						params.leaf=true;
						parent.data.leaf=false;
						parent.data.expanded=true;
						parent.commit();
						var node=parent.appendChild(params);
						tree.selectPath(node.getPath())
						tree.fireEvent("itemclick",tree.getView(),node);	
					}
				}
			},
			"panel[xtype=menu.menutree] button[ref=treeDel]":{
				click:function(btn){
					var tree=btn.up("panel[xtype=menu.menutree]");
					var records=tree.getSelectionModel().getSelection();
					if(records.length<=0){
						alert("请选中节点!");
						return;
					}
					var node=records[0];
					var resObj=self.ajax({url:"/pc/menuAction!doRemove.do",params:{ids:node.get("id")}});
					if(resObj.success){
						tree.getStore().load();
						self.msgbox(resObj.obj);
					}else{
						alert(resObj.obj);
					}
				}
			},
			"panel[xtype=menu.menuform] button[ref=submit]":{
				click:function(btn){
					var menuForm=btn.up("panel[xtype=menu.menuform]");
					var formObj=menuForm.getForm();
					var idValue=formObj.findField("menuId").getValue();
					if(idValue!=null && idValue!=""){
						formObj.submit({
							url:"/pc/menuAction!doUpdate.do",
							params:{uploadFields:"icon,bigIcon"},
							//可以提交空的字段值
//							submitEmptyText:true,
							success:function(form,action){
								var obj=action.result.obj;
								if(action.result.success){
									//刷新当前表单
									self.setFormValue(formObj,obj);
									var mainLayout=menuForm.up("panel[xtype=menu.mainlayout]");
									var menuTree=mainLayout.down("panel[xtype=menu.menutree]");
									var node=menuTree.getSelectionModel().getSelection()[0];
									node.set("text",obj.menuName);
									node.set("code",obj.moduleCode);
									node.set("id",obj.menuId);
									node.set("icon",obj.icon);
									node.set("bigIcon",obj.bigIcon);
									node.set("nodeInfo",obj.nodeInfo);
									node.set("nodeInfoType",obj.nodeInfoType);
									node.set("orderIndex",obj.orderIndex);
									node.commit();
									self.msgbox("保存成功!");
								}else{
									alert(obj);
								}
							},
							failure:function(form, action){
								//前台表单校验错误
								if(action.fieldureType=="client"){
									var errors=["前台验证失败，错误信息："];
									formObj.getFields().each(function(f){
										if(!f.isValid()){
											errors.push("<font color=red>"+f.fieldLabel+"</font>:"+f.getErrors().join(","));
										}
									});
									alert(errors.join("<br/>"));								
								}else{
									alert("后台数据保存错误");
								}
							}		
						});
					}else{
						alert("请选中节点");
					}
					
				}
			}
		});
	},
	
	views: [
		"core.sys.menu.view.MainLayout",
		"core.sys.menu.view.MenuForm",
		"core.sys.menu.view.MenuTree"
	],
	
	stores:[
		"core.sys.menu.store.MenuStore"
	]
});