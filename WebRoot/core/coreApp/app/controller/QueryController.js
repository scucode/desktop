/**
 * 查询组件控制器
 */
Ext.define("core.app.controller.QueryController",{
	extend:"Ext.app.Controller",
	initQuery:function(){
		var self=this;
		var queryCtr={
			"mttreeview":{
				/**
				 * 注册树形的选中事件，选中指定父节点和子节点
				 */
				checkchange:function(node,checked,eOpts){
					node.expand(true);
					var eachParent = function(node,checked){
                            if(!node.isRoot() && checked == true){
                                if(!Ext.isEmpty(node.get('checked'))){
                                    node.set('checked',checked);
                                    node.commit();
                                }
                                eachParent(node.parentNode,checked);
                            }
                        }
                      eachParent(node.parentNode,checked);
                      var eachChild = function(node,checked){
                        node.eachChild(function(n){
                            if(!Ext.isEmpty(n.get('checked'))){
                                n.set('checked',checked);
                                n.commit();
                            }
                            eachChild(n,checked);
                        });
                    	};
                    	eachChild(node,checked);
				}	
			},
			"mtsswinview button[ref=ssOkBtn]":{
				click:function(btn){
					var win=btn.up("mtsswinview");
					//树形查询处理
					if(win.queryType=="mttreeview"){
						var tree=win.down("mttreeview");
						var selRecords=new Array();
						var records=tree.getChecked();
						Ext.each(records,function(rec){
							if(!rec.raw.disabled){
								selRecords.push(rec);
							}
						});
						if(selRecords.length>0){
							win.callback(win,selRecords);
							win.close();
						}else{
							alert("你选中的信息错误，请重新选择!");
						}
					}
				}
			},
			"mtsswinview button[ref=ssCancelBtn]":{
				click:function(btn){
					var win=btn.up("mtsswinview");
					win.close();
				}
			}
		}
		Ext.apply(self.ctr,queryCtr);
	},
	views:[
		"core.app.view.query.MtssWindow",
		"core.app.view.query.TreeView"
		],
	stores:[
		"core.app.store.query.TreeStore"
	]
});