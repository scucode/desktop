Ext.define("core.util.MenuUtil",{
	mixins:{
		suppleUtil:"core.util.SuppleUtil"
	},
	/**
	 * 加载当前登录的人权限按钮
	 */
	initMenu:function(){
		var self=this;
		var data=self.ajax({url:"/pc/menuAction!getTree.do",params:{excludes:"checked"}});
		var menuTreeStore=Ext.create("Ext.data.TreeStore",{
			model:factory.ModelFactory.getModelByName("com.desktop.model.extjs.JSONTreeNode","checked").modelName,
			defaultRootId:"ROOT",
			root:{
				text:"ROOT",
				code:"ROOT",
				children:data
			}
		});
		comm.add("menuTreeStore",menuTreeStore);
	},
	/**
	 * 构建菜单数据
	 * @param {} node
	 * @return {}
	 */
	buildMenuData:function(node){
		var data=new Array();
		node.eachChild(function(n){
			data.push(n.raw);
		});
		return data;
	},
	/**
	 * 构建开始菜单中的项
	 * @param {} node
	 */
	buildStartMenu:function(root,me){
		var rootMenu={};
		var eachMenus = function(node,menu){
              node.eachChild(function(n){
	           var menuObj={
	           		text:n.get("text"),
	           		icon:n.get("icon"),
	           		handler:function(){
		            	 me.desktop.onShortcutItemClick(null,n);
		            }
	           };
	           if(menu.menu){
	           		menu.menu.items.push(menuObj);
	           }else{
	           		menu.menu={
	           			items:[menuObj]
	           		}
	           }
               eachMenus(n,menuObj);
            });
		}
		eachMenus(root,rootMenu);
		if(rootMenu.menu){
			return rootMenu.menu.items;
		}else{
			return [];
		}
	}
});