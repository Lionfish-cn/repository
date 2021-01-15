package com.code.repository.entity;

import com.code.repository.annotation.FieldUnique;
import com.code.repository.entity.base.BaseEntity;
import lombok.*;

import java.util.Date;

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
    @FieldUnique
    private String name;
    private String description;
    private Date createTime;
    private Date updateTime;
}
