package com.code.repository.entity;

import com.code.repository.entity.base.BaseEntity;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_verifycodeblacklist")
public class VerifyCodeBlackList extends BaseEntity {
    @Column(name="phone")
    private String phone;
    @Column(name="reason")
    private String reason;
    @UpdateTimestamp
    @Column(name = "happen_time")
    private Date happenTime;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String id;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getHappenTime() {
        return happenTime;
    }

    public void setHappenTime(Date happenTime) {
        this.happenTime = happenTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Id
    public String getId() {
        return id;
    }
}
