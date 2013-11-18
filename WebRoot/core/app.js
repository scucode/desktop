Ext.onReady(function(){
	Ext.application({
		name: "core",
		scope: this,
		appFolder: 'core/coreApp',
		
		launch: function() {
		
		},
		controllers: [
			"core.app.controller.MainController"
		]
	});
})