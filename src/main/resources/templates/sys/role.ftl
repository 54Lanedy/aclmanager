
<style>
    .demo-transfer{margin: 10px 30px;}
</style>
<script type="text/javascript" src="./js/sys/role.js"></script>

<div id="role_layout" class="easyui-layout" data-options="fit:true">
    <div id="role_layout_west" class="wu-sidebar" data-options="region:'west',split:true,border:true,title:'权限系统列表'" style="width:10%">

        <table id="role_layout_west_table"></table>
    </div>
    <div id="role_layout_center" class="wu-sidebar" data-options="region:'center',split:true,border:true,title:'角色列表',tools: [{
         iconCls:'icon-add',
         handler:function(){_role.openAddDia(0)}
     },{
         iconCls:'icon-edit',
         handler:function(){_role.openAddDia(1)}
     }]" style="width:15%">

        <table id="role_layout_center_table"></table>
    </div>
    <div id="role_layout_east" class="wu-sidebar" data-options="region:'east',split:true,border:true" style="width: 75%">
        <div id="role_center_tabs" class="easyui-tabs" style="width:100%;height:100%;">
            <div title="角色与权限" class="easyui-tab" style="padding:20px;display:none;">
                <ul id="role_and_acl_tree"></ul>
                <br>
                <hr>
                <a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="_role.saveRoleAndAcl();" plain="true">保存</a>
            </div>
            <div title="角色与用户" class="easyui-tab"  style="overflow:auto;padding:20px;display:none;">

                <div id="role_transfer" class="demo-transfer"></div>
                <br>
                <hr>
                <a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="_role.saveRoleAndUser();" plain="true">保存</a>
            </div>
        </div>
    </div>
</div>

<!-- Begin of easyui-dialog -->
<div id="role_dia" class="easyui-dialog" data-options="title:'编辑角色',closed:true,iconCls:'icon-save',modal:true,
        buttons:[{
            text:'保存',
            handler:function(){_role.save();}
            },{
                text:'关闭',
                handler:function(){$('#role_dia').dialog('close');}
        }]" style="width:400px; padding:10px;">
    <form id="role_dia_edit_form" method="post">
        <input name="id" hidden>
        <input id="role_aclModuleId" name="aclModuleId" hidden>
        <table>
            <tr>
                <td align="right">归属的权限系统：</td>
                <td><input id="role_aclModuleName" name="aclModuleName" class="easyui-textbox"  data-options="width:260,readonly:true"/></td>
            </tr>
            <tr>
                <td align="right">名 称：</td>
                <td><input name="name" class="easyui-textbox"  data-options="width:260,required:true"/></td>
            </tr>
            <tr>
                <td align="right">编 码：</td>
                <td><input name="code" class="easyui-textbox"  data-options="width:260,required:true"/></td>
            </tr>
            <tr>
                <td align="right">状 态：</td>
                <td>
                    <select name="status" class="easyui-combobox" style="width:150px" data-options="required:true,editable:false">
                        <option value="1">可用</option>
                        <option value="-1">冻结</option>
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