package com.hjc.aclmanager.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class RoleDTO {

    private String id;

    @NotBlank(message = "权限系统ID不可以为空")
    private String aclModuleId;

    @NotBlank(message = "角色名称不可以为空")
    @Length(min = 2, max = 20, message = "角色名称长度需要在2-20个字之间")
    private String name;

    @NotBlank(message = "角色编码不可以为空")
    @Length(min = 2, max = 20, message = "角色编码长度需要在2-20个字之间")
    private String code;

    @Min(value = 1, message = "角色类型不合法")
    @Max(value = 2, message = "角色类型不合法")
    private Integer type = 1;

    @NotNull(message = "角色状态不可以为空")
    @Min(value = -1, message = "角色状态不合法")
    @Max(value = 1, message = "角色状态不合法")
    private Integer status;

    @Length(min = 0, max = 200, message = "角色备注长度需要在200个字符以内")
    private String remark;
}
