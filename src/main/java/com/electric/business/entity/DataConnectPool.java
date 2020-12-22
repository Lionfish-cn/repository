package com.electric.business.entity;

import com.electric.business.entity.base.BaseEntity;
import lombok.*;

import java.util.Date;

/**
 * Create file by suwenpo at 2020/12/21
 * 数据连接池表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class DataConnectPool extends BaseEntity {
    private String name;
    private String type;
    private String driverClassname;
    private Integer port;
    private String host;
    private String database;
    private String username;
    private String password;
    private String description;
    private boolean isEnable;
    private Date createTime;
    private Date updateTime;
}
