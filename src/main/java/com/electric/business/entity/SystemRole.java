package com.electric.business.entity;

import com.electric.business.entity.base.BaseEntity;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * Create file by suwenpo at 2020/12/21 13:50
 * 系统角色表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class SystemRole  extends BaseEntity {
    private String name;
    private String description;
    private Date createTime;
    private Date updateTime;
}
