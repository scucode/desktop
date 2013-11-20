/**
 * 表格控制器
 */
Ext.define("core.app.controller.GridController",{
	extend:"Ext.app.Controller",
	initGrid:function(){
		var self=this;
		var gridCtr={
			"basegrid":{
				/**
				 * 表格的render事件
				 */
				render:function(grid){
					var basePanel=grid.up("basepanel");
					var funCode=basePanel.funCode;
					grid.funCode=funCode;
					grid.itemId=funCode+"_basegrid";
				},
				/**
				 * 表格的双击事件
				 */
				itemdblclick:function(grid,record,item,index,e,eOpts){
					var basePanel=grid.up("basepanel");
					var funCode=basePanel.funCode;
					var baseGrid=basePanel.down("basegrid[itemId="+funCode+"_basegrid]");
					var editBtn=baseGrid.down("button[ref=gridEdit]");
					if(editBtn){
						var baseForm=basePanel.down("baseform[itemId="+funCode+"_baseform]");
						//表单赋值
						self.setFormValue(baseForm.getForm(),record.data);
						baseGrid.hide();
						baseForm.show();
					}
				},
				/**
				 * 表格单击事件
				 */
				itemclick:function(grid,record,item,index,e,eOpts){
					var basePanel=grid.up("basepanel");
					var funCode=basePanel.funCode;
					var baseGrid=basePanel.down("basegrid[itemId="+funCode+"_basegrid]");
					var records=baseGrid.getSelectionModel().getSelection();
					var btn=baseGrid.down("button[ref=gridEdit]");
					if(!btn){
						return;
					}
					if(records.length==1){
						btn.setDisabled(false);
					}else{
						btn.setDisabled(true);
					}
				}
			}
		}
		Ext.apply(self.ctr,gridCtr);
	}
});