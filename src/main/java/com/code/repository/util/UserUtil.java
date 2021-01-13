package com.code.repository.util;

import com.headyonder.dmp.entity.SystemAuth;
import com.headyonder.dmp.entity.User;
import org.apache.shiro.SecurityUtils;

import java.util.ArrayList;
import java.util.List;

public class UserUtil {
    public static User getUser() {
        return (User) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 获取当前用户的权限
     * @return
     */
    public static List<SystemAuth> getUserAuths(User u){
        if (u == null)
            u = getUser();
        List<SystemAuth> sauths= new ArrayList<>();
        List<String> roles = u.getRoles();
        roles.forEach(s->{

        });

        return sauths;
    }

}
