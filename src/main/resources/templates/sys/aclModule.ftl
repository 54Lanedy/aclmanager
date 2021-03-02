
<script type="text/javascript" src="./js/sys/aclModule.js"></script>

<div id="aclModule_layout" class="easyui-layout" data-options="fit:true">
    <div id="aclModule_layout_west" class="wu-sidebar" data-options="region:'west',split:true,border:true,title:'权限系统管理',tools: [{
         iconCls:'icon-add',
         handler:function(){_aclModule.openAddModuleDia(0)}
     },{
         iconCls:'icon-edit',
         handler:function(){_aclModule.openAddModuleDia(1)}
     }]" style="width:300px">

        <table id="aclModule_layout_west_table"></table>

    </div>
    <div id="aclModule_layout_center" class="wu-sidebar" data-options="region:'center',split:true,border:true,title:'权限菜单列表',tools: [{
         iconCls:'icon-add',
         handler:function(){_aclModule.openAddAclDia(0)}
     },{
         iconCls:'icon-edit',
         handler:function(){_aclModule.openAddAclDia(1)}
     }]">
        <table id="aclModule_layout_center_table"></table>
    </div>
</div>
<!-- Begin of easyui-dialog -->
<div id="aclModule_dia" class="easyui-dialog" data-options="title:'添加权限模块',closed:true,iconCls:'icon-save',modal:true,
        buttons:[{
            text:'保存',
            handler:function(){_aclModule.save();}
            },{
                text:'关闭',
                handler:function(){$('#aclModule_dia').dialog('close');}
        }]" style="width:400px; padding:10px;">
    <form id="aclModule_dia_edit_form" method="post">
        <input name="id" hidden>
        <table>
            <tr>
                <td align="right">名 称：</td>
                <td><input name="name" class="easyui-textbox"  data-options="width:260,required:true"/></td>
            </tr>
            <tr>
                <td align="right">系统编码：</td>
                <td><input name="systemCode" class="easyui-textbox"  data-options="width:260,required:true"/></td>
            </tr>
            <tr>
                <td align="right">顺 序：</td>
                <td><input name="seq" class="easyui-numberspinner" style="width:150px;"
                           data-options="editable:true,required:true,value:0"></td>
            </tr>
            <tr>
                <td align="right">状 态：</td>
                <td>
                    <select name="status" class="easyui-combobox" style="width:150px" data-options="required:true,editable:false">
                        <option value="1">有效</option>
                        <option value="-1">无效</option>
                        <option value="0">删除</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td valign="top" align="right">备 注：</td>
                <td><input name="remark" class="easyui-textbox" data-options="multiline:true,width:260,height:80" /></td>
            </tr>
        </table>
    </form>
</div>
<!-- End of easyui-dialog -->
<!-- Begin of easyui-dialog -->
<div id="acl_dia" class="easyui-dialog" data-options="title:'添加权限点',closed:true,iconCls:'icon-save',modal:true,
        buttons:[{
            text:'保存',
            handler:function(){_aclModule.save_acl();}
            },{
                text:'关闭',
                handler:function(){$('#acl_dia').dialog('close');}
        }]" style="width:400px; padding:10px;">
    <form id="acl_dia_edit_form" method="post">
        <input name="id" hidden>
        <input id="acl_module_id" name="aclModuleId" hidden>
        <input id="acl_parent_id" name="parentId" hidden>
        <table>
            <tr>
                <td align="right">所属权限系统：</td>
                <td><input id="acl_moduleName" name="aclModuleName" class="easyui-textbox"  data-options="width:260,readonly:true"/></td>
            </tr>
            <tr>
                <td align="right">父级关系：</td>
                <td><input id="acl_parentName" name="parentName" class="easyui-textbox"  data-options="width:260,readonly:true"/></td>
            </tr>
            <tr>
                <td align="right">名 称：</td>
                <td><input name="name" class="easyui-textbox"  data-options="width:260,required:true"/></td>
            </tr>
            <tr>
                <td align="right">类 型：</td>
                <td>
                    <select name="type" class="easyui-combobox" style="width:150px" data-options="required:true,editable:false">
                        <option value="0">显示菜单</option>
                        <option value="1">链接菜单</option>
                        <option value="2">按钮</option>
                        <option value="99">其他</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td align="right">URL：</td>
                <td><input name="url" class="easyui-textbox"  data-options="width:260,required:true"/></td>
            </tr>
            <tr>
                <td align="right">图标：</td>
                <td>
                    <input id="sys_res_iconCls" name="iconCls" >
                    <div id="sys_res_sp">
                        <div style="color:#99BBE8;background:#fafafa;padding:5px; ">请选择您的资源图标</div>
                        <div   style="padding:5px" id="sys_res_sp_icons">
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td align="right">排 序：</td>
                <td><input name="seq" class="easyui-numberspinner" style="width:150px;"
                           data-options="editable:true,required:true,value:0"></td>
            </tr>
            <tr>
                <td align="right">状 态：</td>
                <td>
                    <select name="status" class="easyui-combobox" style="width:150px" data-options="required:true,editable:false">
                        <option value="1">有效</option>
                        <option value="-1">无效</option>
<#--                        <option value="0">删除</option>-->
                    </select>
                </td>
            </tr>
            <tr>
                <td valign="top" align="right">备 注：</td>
                <td><input name="remark" class="easyui-textbox" data-options="multiline:true,width:260,height:80" /></td>
            </tr>
        </table>
    </form>
</div>
<!-- End of easyui-dialog -->