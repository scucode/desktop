<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0"> 
	<title>桌面化系统</title>
	
	<link rel="stylesheet" type="text/css" href="/extjs/resources/css/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="/desktop/css/desktop.css" />
	<link rel="stylesheet" type="text/css" href="/core/css/comm.css" />
	<link rel="stylesheet" type="text/css" href="/extjs/shared/example.css">
	<script type="text/javascript" src="/extjs/ext-all-debug.js"></script>
	<script type="text/javascript" src="/extjs/shared/examples.js"></script>
	<script type="text/javascript" src="/extjs/ext-lang-zh_CN.js"></script>
	<script type="text/javascript" src="/core/coreApp/util/comm.js"></script>
	<script type="text/javascript" src="/core/coreApp/util/OverrideUtil.js"></script>
	<script type="text/javascript" src="/core/loader.js"></script> 
	<script type="text/javascript" src="/core/app.js"></script> 
</head>
<body>

<script type="text/javascript">
	<!--加载分辨率大小-->
	var clientWidth = document.body.clientWidth;
	var clientHeight = document.body.clientHeight;
	var screenWidth = document.body.scrollWidth;
	var screenHeight = document.body.scrollHeight;
	var resolutionHeight = window.screen.height;
	var resolutionWidth = window.screen.width;
	comm.add("clientWidth",clientWidth);
	comm.add("clientHeight",clientHeight);
	comm.add("screenWidth",screenWidth);
	comm.add("screenHeight",screenHeight);
	comm.add("resolutionWidth",resolutionWidth);
	comm.add("resolutionHeight",resolutionHeight);   

	<!--改造窗体的层次-->
	Ext.override(Ext.ZIndexManager, {
	    tempHidden: [],
	    show: function () {
	        var comp, x, y;
	        while (comp = this.tempHidden.shift()) {
	            x = comp.x;
	            y = comp.y;
	            comp.show();
	            comp.setPosition(x, y);
	        }
	    }
	});
	
	<!--加载程序-->
	Ext.Loader.setConfig({
	  enabled: true,
	  paths: {
	    'Ext.ux.desktop': 'desktop/js',
	    'MyDesktop': 'desktop'
	  }
	});

	<!--引用类-->
    Ext.require('MyDesktop.App');

    <!--实例化-->
    var myDesktopApp;
    Ext.onReady(function () {
        myDesktopApp = new MyDesktop.App();
    });
</script>
</body>
</html>