<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" isELIgnored="false"%>
<jsp:include page="system/doctype.jsp" flush="true" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style type="text/css">
<!--
html,body {
	width: 100%;
	height: 98%;
	margin: 0;
}
-->
</style>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/dijit/themes/claro/claro.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/dojo/dojo.js" data-dojo-config="async: true"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ui.js"></script>
<script type="text/javascript">
ctx = '${pageContext.request.contextPath}';

function createConnection() {
	require(['dialog/createConnection'], function(createConnectionDialog) {
		createConnectionDialog.show();
	});
}

function createFolder() {
	
}

var menu = {
		dbLeftPanel: {
			targetNodeIds: ['dbLeftPanel'], 
			list: [
			       {title: '新建连接', action: createConnection},
			       {title: '新建文件夹', action: createFolder},
			       {title: '共享', action: createFolder},
			       {title: '刷新', action: createFolder}
			]
		}
};

require(['page/layout', 'dojo/json', 'dojo/dom', 'log', 'dojo/domReady!'], function(Layout, json, dom, log) {
	var l = new Layout();
	var a = l.startup();
	try {
		a.placeAt(dom.byId('main'));
		a.startup();
		l.setupMenu();
	} catch (e) {
		log.error(e.message);
	}
});

/*require([
         'dijit/layout/TabContainer', 'dojo/dom', "dijit/layout/BorderContainer", "dijit/layout/ContentPane",
         "dijit/Menu",
         "dijit/MenuItem",
         "dijit/CheckedMenuItem",
         "dijit/MenuSeparator",
         "dijit/PopupMenuItem",
         "dojo/domReady!"
     ], function(TabContainer, dom, BorderContainer, ContentPane, Menu, MenuItem, CheckedMenuItem, MenuSeparator, PopupMenuItem){
	
		function setupMenu(m) {
			for (var k in m) {
				var pm = new Menu({targetNodeIds: m[k]['targetNodeIds']});
				for (var i = 0; i < m[k]['list'].length; i++) {
					var record = m[k]['list'][i];
					var menuItem = new MenuItem({label: record['title'], onClick: record['action']});
					pm.addChild(menuItem);
				}
				pm.startup();
			}
		}
		function setupComponent(c) {
			var res = null;
			if (c['title'] != null) {
				c['args']['title'] = c['title'];
			}
			c['args']['id'] = c['id'];
			if (c['type'] == 'BorderContainer') {
				res = new BorderContainer(c['args']);
			} else if (c['type'] == 'TabContainer') {
				res = new TabContainer(c['args']);
			} else if (c['type'] == 'ContentPane') {
				res = new ContentPane(c['args']);
			}
			if (c['childs'] != null) {
				for (var i = 0; i < c['childs'].length; i++) {
					res.addChild(setupComponent(c['childs'][i]));
				}
			}
			return res;
		}
		try {
			var p = setupComponent(page);
			p.placeAt(dom.byId('main'));
			p.startup();
			setupMenu(menu);
			refreshConnectionList();
		} catch (e) {alert(e.message);}
	}
);
*/

function refreshConnectionList() {
	getConnectionList(renderConnectionList);
}


function getConnectionList(callback) {
	$.post(ctx + '/db/getConnectionList.htm', {}, function(data) {
		eval('data=' + data);
		if (typeof(callback) == 'function') {
			callback(data['list']);
		}
	});
}



function renderConnectionList(list) {
	
	require([
	         'dojo/dom', 'dijit/registry', 
	         "dojo/domReady!"
	     ], function(dom, r){
		var dbLeftPanel = r.byId('dbLeftPanel');
		var content = '';
		debugger;
		for (var i = 0; i < list.length; i++) {
			if (i > 0) {
				content += '<br />';
			}
			content += list[i]['name'];
		}
		dbLeftPanel.set('content', content);
	});
}

</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Dev Util - 工作区</title>
</head>
<body class="claro" style="margin: 0 auto; text-align: center;">
	<div style="height: 100%; width: 100%; position: relative; min-width: 800px; min-height: 500px;">
		<div id="main" class="roundCorderN" style="background-color: #aaa;overflow: hidden;position: absolute; left: 20px; top: 20px; right: 20px; bottom: 20px;">

		</div>
	</div>
</body>
</html>