package com.code.repository.util;

import com.headyonder.dmp.entity.base.BaseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 实体工具类
 */
public class EntityUtil {

    /**
     * 将长类名com.xx.entity.Entity，转换成entity
     *
     * @param bean 长类名com.xx.entity.Entity
     * @return 返回短类名
     */
    public static String getSimpleName(String bean) {
        String clzname = bean.substring(bean.lastIndexOf(".") + 1);
        clzname = clzname.toLowerCase().charAt(0) + clzname.substring(1);
        return clzname;
    }

    /**
     * @param clz  需要反射的类
     * @param name 需要反射的字段名
     * @param o    需要invoke的类（需要从哪个实体类获取值）
     * @return
     */
    private static Object invokeMethod(Class clz, String name, Object o) {
        Object value = "";
        try {
            name = name.toUpperCase().charAt(0) + name.substring(1);
            value = clz.getMethod("get" + name).invoke(o);
            if (value instanceof Date) {//如果值的类型是日期，则将日期进行转换
                value = DateUtil.convertDateToString((Date) value, "yyyy-MM-dd HH:mm");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 根据查询参数去反射所有数据，并返回map
     *
     * @param str
     * @param baseEntity
     * @return
     * @throws Exception
     */
    public static Map<String, Object> parseMapByQueryString(String[] str, BaseEntity baseEntity) throws Exception {
        if (baseEntity != null) {
            Class clz = baseEntity.getClass();
            Map<String, Object> m = new HashMap<>();
            for (String s : str) {
                m.put(s, invokeMethod(clz, s, baseEntity));
            }
            return m;
        }
        return null;
    }


}
