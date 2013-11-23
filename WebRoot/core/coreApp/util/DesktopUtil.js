Ext.define("core.util.DesktopUtil",{
	mixins:{
		menuUtil:"core.util.MenuUtil"
	},
	getMenuItems:function(record,me){
		var self=this;
		var items=null;
		//加载菜单
		if(record.get("nodeInfoType")=="MENU"){
			var id=record.get("id");
			var menuTreeStore=comm.get("menuTreeStore");
			var node=menuTreeStore.getNodeById(id);
			var data=self.buildMenuData(node);
			var dataView=Ext.create("Ext.ux.desktop.FolderView",{
				listeners:{
					itemclick:function(dv,record,item,index,e,eOpts){
						me.onShortcutItemClick(dv,record);
					}
				}
			});
			dataView.getStore().loadData(data);
			items=dataView;
		//加载功能
		}else{
			var nodeInfo=record.data.nodeInfo;
        	var config=nodeInfo.split(",");
			var controller=coreApp.getController(config[1]);
			if(!controller.inited){controller.init();controller.inited=true};
        	items={xtype:config[0]};
		}
		return items;
	},
	loadFolderData:function(record,win,me,isHander){
		var self=this;
		var dataView=win.down("folderdataview");
		var id=record.get("id");
		var menuTreeStore=comm.get("menuTreeStore");
		var node=menuTreeStore.getNodeById(id);
		var data=self.buildMenuData(node);
		var store=dataView.getStore();
		if(isHander){
			store.loadData(data);
			return;
		}
		//如果你点击的是文件夹中的数据
		if(store.findRecord("id",record.get("id"))){
			var toolbar=win.down("toolbar");
			toolbar.add(">");
			toolbar.add({
				xtype:"button",
				text:"<b>"+record.get("text")+"</b>",
				handler:function(btn){
					while(btn.nextSibling()){
								btn.ownerCt.remove(btn.nextSibling());
							}
					self.loadFolderData(record,win,me,true);
				}
			});
		}else{
			var taskButton=me.taskbar.getTaskButton(win.taskButton);
			if(taskButton){
				taskButton.setText(record.get("text"));
				taskButton.setIcon(record.get("icon"));
			}
			win.setTitle(record.get("text"));
			win.setIcon(record.get("icon"));
			var toolbar=win.down("toolbar");
			toolbar.removeAll();
			toolbar.add({
				xtype:"button",
				text:"<b>"+record.get("text")+"</b>",
				handler:function(btn){
					while(btn.nextSibling()){
								btn.ownerCt.remove(btn.nextSibling());
							}
					self.loadFolderData(record,win,me);
				}
			});
		}
		store.loadData(data);
	}
});