Ext.define("core.rbac.user.controller.DeptUserController", {
	extend : 'Ext.app.Controller',
	mixins : {
		suppleUtil : "core.util.SuppleUtil",
		messageUtil : "core.util.MessageUtil",
		formUtil : "core.util.FormUtil",
		treeUtil : "core.util.TreeUtil"
	},
	init : function() {
		var self = this
		// 事件注册
		this.control({
			"panel[xtype=rbac.depttree]" : {
				itemclick : function(tree, record, item, index, e,
						eOpts) {
					var mainLayout=tree.up("panel[xtype=rbac.mainlayout]");
					var deptForm=mainLayout.down("panel[xtype=rbac.deptform]");
					var deptTree=mainLayout.down("panel[xtype=rbac.depttree]");
					var formObj=deptForm.getForm();
					formObj.findField("deptName").setValue(record.get("text"));
					formObj.findField("deptCode").setValue(record.get("code"));
					formObj.findField("deptId").setValue(record.get("id"));
					formObj.findField("orderIndex").setValue(record.get("orderIndex"));
					formObj.findField("parentId").setValue(record.get("parent"));
					var treechildIns=deptTree.down("button[ref=treechildIns]");
					treechildIns.setDisabled(false);
					var treeDel=deptTree.down("button[ref=treeDel]");
					treeDel.setDisabled(false);
					//加载人员信息
					var userGrid=mainLayout.down("panel[xtype=rbac.usergrid]");
					var ids=new Array();
					var map=self.eachChildNode(record);
					map.eachKey(function(key){
						ids.push("'"+key+"'");
					});
					var store=userGrid.getStore();
					var proxy=store.getProxy();
					proxy.extraParams={
						whereSql:" and deptId in("+ids.join(",")+")"					
					};
					store.load();
				}
			},

			"panel[xtype=rbac.depttree] button[ref=treeIns]" : {
				click : function(btn) {
					var tree = btn.up("panel[xtype=rbac.depttree]");
					var root = tree.getRootNode();
					var params = {
						layer : root.getDepth() + 1,
						nodeInfo : "Department",
						parentId : root.get("id"),
						nodeType : "LEAF"
					}
					var resObj = self.ajax({
								url : "/rbac/deptAction!doSave.do",
								params : params
							});
					if (resObj.success) {
						var deptObj = resObj.obj;
						var nodeObj = {
							id : deptObj.deptId
						}
						params.parent = params.parentId;
						params.id = deptObj.deptId;
						params.leaf = true;
						var node = root.appendChild(params);
						tree.fireEvent("itemclick", tree.getView(),
								node);
					}
				}
			},

			"panel[xtype=rbac.depttree] button[ref=treechildIns]" : {
				click : function(btn) {
					var tree = btn.up("panel[xtype=rbac.depttree]");
					var records = tree.getSelectionModel()
							.getSelection();
					if (records.length <= 0) {
						alert("请选中节点!");
						return;
					}
					var parent = records[0];
					var params = {
						layer : parent.getDepth() + 1,
						nodeInfo : "Department",
						parentId : parent.get("id"),
						nodeType : "LEAF"
					}
					var resObj = self.ajax({
								url : "/rbac/deptAction!doSave.do",
								params : params
							});
					if (resObj.success) {
						var deptObj = resObj.obj;
						var nodeObj = {
							id : deptObj.deptId
						}
						params.parent = params.parentId;
						params.id = deptObj.deptId;
						params.leaf = true;
						parent.data.leaf = false;
						parent.commit();
						var node = parent.appendChild(params);
						tree.fireEvent("itemclick", tree.getView(),
								node);
					}
				}
			},

			"panel[xtype=rbac.depttree] button[ref=treechildIns]" : {
				click : function(btn) {
					var tree = btn.up("panel[xtype=rbac.depttree]");
					var records = tree.getSelectionModel()
							.getSelection();
					if (records.length <= 0) {
						alert("请选中节点!");
						return;
					}
					var parent = records[0];
					var params = {
						layer : parent.getDepth() + 1,
						nodeInfo : "Department",
						parentId : parent.get("id"),
						nodeType : "LEAF"
					}
					var resObj = self.ajax({
								url : "/rbac/deptAction!doSave.do",
								params : params
							});
					if (resObj.success) {
						var deptObj = resObj.obj;
						var nodeObj = {
							id : deptObj.deptId
						}
						params.parent = params.parentId;
						params.id = deptObj.deptId;
						params.leaf = true;
						parent.data.leaf = false;
						parent.data.expanded = true;
						parent.commit();
						var node = parent.appendChild(params);
						tree.selectPath(node.getPath())
						tree.fireEvent("itemclick", tree.getView(),
								node);
					}
				}
			},

			"panel[xtype=rbac.depttree] button[ref=treeDel]" : {
				click : function(btn) {
					var tree = btn.up("panel[xtype=rbac.depttree]");
					var records = tree.getSelectionModel()
							.getSelection();
					if (records.length <= 0) {
						alert("请选中节点!");
						return;
					}
					var node = records[0];
					var resObj = self.ajax({
								url : "/rbac/deptAction!doRemove.do",
								params : {
									ids : node.get("id")
								}
							});
					if (resObj.success) {
						tree.getStore().load();
						self.msgbox(resObj.obj);
					} else {
						alert(resObj.obj);
					}
				}
			},

			"panel[xtype=rbac.deptform] button[ref=submit]" : {
				click : function(btn) {
					var deptForm = btn.up("panel[xtype=rbac.deptform]");
					var formObj = deptForm.getForm();

					var params = self.getFormValue(formObj);
					if (params.deptId != null && params.deptId != "") {
						var resObj = self.ajax({
									url : "/rbac/deptAction!doUpdate.do",
									params : params
								});

						if (resObj.success) {
							var mainLayout = deptForm
									.up("panel[xtype=rbac.mainlayout]");
							var deptTree = mainLayout
									.down("panel[xtype=rbac.depttree]");
							var node = deptTree.getSelectionModel()
									.getSelection()[0];
							var obj = resObj.obj;
							node.set("text", obj.deptName);
							node.set("code", obj.deptCode);
							node.set("id", obj.deptId);
							node.set("orderIndex", obj.orderIndex);
							node.commit();
							self.msgbox("保存成功!");
						} else {
							alert(resObj.obj);
						}
					} else {
						alert("请选中节点");
					}

				}
			}

		});
	},

	views : ["core.rbac.user.view.MainLayout", "core.rbac.user.view.DeptTree",
			"core.rbac.user.view.CenterLayout", "core.rbac.user.view.DeptForm",
			"core.rbac.user.view.UserLayout", "core.rbac.user.view.UserGrid"],
	stores : ["core.rbac.user.store.DeptStore",
			"core.rbac.user.store.UserStore"]
});