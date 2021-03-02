<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8" />
    <title>用户登录</title>
    <#include "freemarker-inc/web-inc.ftl">
    <script type="text/javascript" src="./js/login.js"></script>
    <link rel="shortcut icon" href="favicon.ico" />
    <style>
        html, body
        {
            width: 100%;
            height: 100%;
            margin: 0;
            padding: 0;
        }

        h1
        {
            color: rgb(39, 78, 255);
            text-align: center;
        }

        a:link, a:hover, a:visited, a:active
        {
            color: rgb(39, 78, 255);
            text-decoration: none;
        }

        form
        {
            width: 400px;
            min-width: 400px;
            position: absolute;
            left: 40%;
            top: 15%;
            margin: 0;
            padding: 30px;
            background-color: white;
            border: 2px solid rgba(128, 128, 128, 0.2);
            border-radius: 10px;
        }

        form div
        {
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<form id="login_form" action="/aclmanager/signin.page" method="post">
    <div>
        <h1>用户登录</h1>
    </div>
    <div>
        <input class="easyui-textbox" name="username" data-options="iconCls:'icon-man',iconWidth:30,iconAlign:'left',prompt:'用户名',required:true" style="width:100%;height:35px;" />
    </div>
    <div>
        <input class="easyui-passwordbox" name="password" data-options="iconWidth:30,iconAlign:'left',prompt:'密码',required:true" style="width:100%;height:35px;" />
    </div>
    <div style="color: red;">${error}</div>
    <div>
        <input class="easyui-linkbutton" type="submit"  value="登陆" style="width:100%;height:35px;" />
    </div>
</form>
</body>
</html>