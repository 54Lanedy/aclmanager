<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>业务平台权限管理系统</title>
    <#include "freemarker-inc/web-inc.ftl">
    <#assign rootPath="/aclmanager">
    <script type="text/javascript" src="./js/main.js"></script>
</head>
<body class="easyui-layout">
<!-- begin of header -->
<div class="wu-header" data-options="region:'north',border:false,split:true">
    <div class="wu-header-left">
        <h1>业务平台权限管理系统</h1>
    </div>
    <div class="wu-header-right">
        <p><strong class="easyui-tooltip" title="2条未读消息">${realName}</strong>，欢迎您！</p>
        <p><a href="${rootPath}/logout.page">安全退出</a></p>
    </div>
</div>
<!-- end of header -->
<!-- begin of sidebar -->
<div class="wu-sidebar" data-options="region:'west',split:true,border:true,title:'导航菜单'">
    <div class="easyui-accordion" data-options="border:false,fit:true">
            <#list acls as menu>
                <div title="${menu.name}" data-options="iconCls:'icon-application-cascade'" style="padding:5px;">
                    <ul class="easyui-tree wu-side-tree">
                        <#list menu.children as link>
                            <li iconCls="${link.iconCls}">
                                <a href="javascript:void(0)"
                                   onclick="_main.openTab('${link.name}','${link.url}','${link.iconCls}')" iframe="0">${link.name}
                                </a>
                            </li>
                        </#list>
                    </ul>
                </div>
            </#list>
    </div>
</div>
<!-- end of sidebar -->
<!-- begin of main -->
<div class="wu-main" data-options="region:'center'">
    <div id="wu-tabs" class="easyui-tabs" data-options="border:false,fit:true">
        <div title="首页" data-options="closable:false,iconCls:'icon-tip',cls:'pd3'">这是首页，待开发......</div>
    </div>
</div>
<!-- end of main -->
<!-- begin of footer -->
<div class="wu-footer" data-options="region:'south',border:true,split:true">
    &copy; 2021 hjc Rights Reserved
</div>
</body>
</html>
