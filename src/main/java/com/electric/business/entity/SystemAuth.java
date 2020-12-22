package com.electric.business.entity;

import com.electric.business.entity.base.BaseEntity;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * Create file by suwenpo at 2020/12/21 13:45
 * 系统权限表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class SystemAuth extends BaseEntity {
    private String name;
    private String description;
    private String authUrl;
    private String authPermission;
    private String authOperate;//edit|delete|add
    private List<Customer> customers;
    private SystemRole roles;
    private Date createTime;
    private Date updateTime;
}
