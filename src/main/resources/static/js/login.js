var _login = $.extend({}, _login);
aclm.init(function () {
    _login.signin = function(){
        $('#login_form').form('submit', {
            url: '/signin.page',
            onSubmit: function(){
                var isValid = $(this).form('validate');
                //return false;
                if (isValid) {
                    aclm.submitLoad(true);
                } else {
                    aclm.TTipWaring("请完善您的表单信息！");
                }
                return isValid;	// 返回false终止表单提交
            },
            success:function(res){
                aclm.submitLoad(false);
                if (res.success) {

                }
            }
        });
    }
});