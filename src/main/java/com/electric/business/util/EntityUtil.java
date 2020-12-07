package com.electric.business.util;

import com.electric.business.entity.base.BaseEntity;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class EntityUtil {

    public static String getSimpleName(String bean) {
        String clzname = bean.substring(bean.lastIndexOf(".")+1);
        clzname = clzname.toLowerCase().charAt(0) + clzname.substring(1);
        return clzname;
    }

    /**
     * 根据查询参数去根据Model反射去查询所有数据，并返回map
     * @param str
     * @param model
     * @return
     * @throws Exception
     */
    public static Map<String,Object> parseMapByQueryString(String[] str,String model) throws Exception{
        if(StringUtil.isNotNull(model)){
            Class clz = Class.forName(model);
            Map<String,Object> m = new HashMap<>();
            for(String s : str){
                Object o = clz.getDeclaredField(s).getType().newInstance();
                String p = s.toUpperCase().charAt(0) + s.substring(1);
                Object obj = clz.getMethod("get"+p).invoke(o);
                m.put(s,obj);
            }
            return m;
        }
        return null;
    }

}
