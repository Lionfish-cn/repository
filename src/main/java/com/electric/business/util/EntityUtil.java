package com.electric.business.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.electric.business.entity.base.BaseEntity;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实体工具类
 */
public class EntityUtil {

    /**
     * 将长类名com.xx.entity.Entity，转换成entity
     * @param bean
     * @return
     */
    public static String getSimpleName(String bean) {
        String clzname = bean.substring(bean.lastIndexOf(".")+1);
        clzname = clzname.toLowerCase().charAt(0) + clzname.substring(1);
        return clzname;
    }

    /**
     * 将实体类解析转换成json
     * @param baseEntity 需要转换的对象
     * @return
     */
    public static JSONObject convertModelToJson(BaseEntity baseEntity){
        JSONObject obj = new JSONObject();
        try{
            Class clz = baseEntity.getClass();
            Field[] fields = clz.getDeclaredFields();
            for (Field field : fields) {
                String name = field.getName();
                Class clzs = field.getType();
                Object o = null;
                try {
                    //有些字段类型无法实例化，若异常， 则认为是Integer|Double|Long等类型
                    o = clzs.newInstance();
                }catch (Exception e){
                    obj.put(name,invokeMethod(clz,name,baseEntity));
                    continue;
                }
                if(o instanceof BaseEntity){//如果字段类型是实体类
                    BaseEntity entity = (BaseEntity)o;
                    if(StringUtil.isNotNull(entity.getInitID())){
                        obj.put(name,convertModelToJson(entity).toJSONString());
                    }else{
                        obj.put(name,"");
                    }
                }else if(o instanceof List){//若字段类型是列表
                    List l = (List)o;
                    for (Object o1 : l) {
                        JSONArray array1 = new JSONArray();
                        if(o1 instanceof BaseEntity){//但是里面的泛型是实体类
                            array1.add(convertModelToJson((BaseEntity) baseEntity).toJSONString());
                        }else{//里面的类型是普通类型
                            array1.add(invokeMethod(clz,name,baseEntity));
                        }
                        obj.put(name,array1.toJSONString());
                    }
                }else{
                    //若类型是普通类型，String 等
                    obj.put(name,invokeMethod(clz,name,baseEntity));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return obj;
    }

    /**
     *
     * @param clz 需要反射的类
     * @param name 需要反射的字段名
     * @param o 需要invoke的类（需要从哪个实体类获取值）
     * @return
     */
    public static Object invokeMethod(Class clz,String name,Object o){
        Object value = "";
        try{
            name = name.toUpperCase().charAt(0)+name.substring(1);
            value = clz.getMethod("get"+name).invoke(o);
            if(value instanceof Date){//如果值的类型是日期，则将日期进行转换
                value = DateUtil.convertDateToString((Date)value,"yyyy-MM-dd HH:mm");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 根据查询参数去反射所有数据，并返回map
     * @param str
     * @param baseEntity
     * @return
     * @throws Exception
     */
    public static Map<String,Object> parseMapByQueryString(String[] str,BaseEntity baseEntity) throws Exception{
        if(baseEntity!=null){
            Class clz = baseEntity.getClass();
            Map<String,Object> m = new HashMap<>();
            for(String s : str){
                m.put(s,invokeMethod(clz,s,baseEntity));
            }
            return m;
        }
        return null;
    }

}
