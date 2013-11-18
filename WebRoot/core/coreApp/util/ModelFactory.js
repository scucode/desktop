/**
 * 模型工厂类
 */
 Ext.define("factory.ModelFactory",{
 	statics : {
 		models:new Ext.util.MixedCollection(),
		fields:new Ext.util.MixedCollection(),
		
		/**
		 * 根据类名获取Model
		 * @param {} modelName
		 * @param {} excludes
		 * @return {}
		 */
		getModelByName:function(modelName,excludes){
			var params={modelName:modelName};
			if(!Ext.isEmpty(excludes)) {
				params.excludes = excludes;
			}
			if(!Ext.isEmpty(modelName) && !this.models.containsKey(modelName)){
				var fields=[];
				if(this.fields.containsKey(modelName)){
					fields=this.fields.get(modelName);
				} else {
					Ext.Ajax.request({
						url:'/pc/modelAction!getModelFields.do',
						method:'POST',
						params:params,
						timeout:4000,
						async:false,//很关键 我不需要异步操作
						success:function(response,opts){
							fields = Ext.decode(response.responseText);
						}
					});
					this.fields.add(modelName,fields);
				}
				var newModel=Ext.define(modelName,{
					extend:"Ext.data.Model",
					fields:fields
				});
				this.models.add(modelName,newModel);
			}
			return {modelName:modelName,model:this.models.get(modelName)};
		},
		
		/**
		 * 获取字段类型
		 * @param {} config
		 */
		getFields:function(config){
			var params={
				modelName:Ext.value(config.modelName,""),
				excludes:Ext.value(config.excludes,"")
			};
			var modelName=params.modelName;
			var fields=[];
			if(this.fields.containsKey(modelName)){
				fields=this.fields.get(modelName);
			}else{
				Ext.Ajax.request({
						url:'/pc/modelAction!getModelFields.do',
						method:'POST',
						params:params,
						timeout:4000,
						async:false,//很关键 我不需要异步操作
						success:function(response,opts){
							fields = Ext.decode(response.responseText);
						}
				});
				this.fields.add(modelName,fields);	
			}
			return fields;
		},
		
		/**
		 * 得到模型
		 * @param {} config
		 * @return {}
		 */
		getModel:function(config){
			var modelName=config.modelName;
			if(!this.models.containsKey(modelName)){
				var config=Ext.value(config,{});
				var fields=config.fields;
				if(Ext.isEmpty(fields)){
					fields=getFields(config);
				}
				var newModel=Ext.define(modelName,{
					extend:"Ext.data.Model",
					fields:fields
				});
				this.models.add(modelName,newModel);
			}
			return modelName;
		}
		
 	}
 });