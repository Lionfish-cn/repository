package com.code.repository.util;

import com.code.repository.annotation.FieldUnique;
import com.code.repository.annotation.IDField;
import com.code.repository.annotation.NotNull;
import com.code.repository.constants.Constants;
import com.code.repository.entity.base.BaseEntity;
import com.code.repository.util.database.DBUtil;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    /**
     * 将entity转成create语句并执行
     *
     * @param clz
     */
    public static void convertModelDDL(Class clz, Connection conn) {
        if (clz == null)
            return;
        try {
            Class superclz = clz.getSuperclass();

            String simpleName = clz.getSimpleName();
            String table = "t_"+underline(simpleName);
            if(DBUtil.getTables(conn).contains(table)){
               return;
            }
            StringBuilder sb = new StringBuilder();
            Field[] fs = clz.getDeclaredFields();
            Field[] superfields = superclz.getDeclaredFields();
            List<Field> fields = new ArrayList<>();
            Collections.addAll(fields, superfields);
            Collections.addAll(fields, fs);

            sb.append("CREATE TABLE ").append(table).append(" (");
            List<String> uniques = new ArrayList<>();
            String primarykey = "";
            List<String> foreigns = new ArrayList<>();
            for (Field f : fields) {
                String name = f.getName();
                String type = f.getType().getTypeName();
                Class ct = f.getType();
                String className = ct.getName();

                if (className.contains(Constants.ENTITY_BASE_PACKAGES)) {
                    convertModelDDL(ct, conn);
                    className = "t_"+underline(getSimpleName(className));
                    String primary = DBUtil.getPrimaryKey(conn,className);
                    foreigns.add(className + ":" + underline(name)+":"+primary);
                    sb.append(underline(name)).append(" ").append(convertType(type)).append(",");
                } else {
                    if (name.contains("List")) {
                        List l = (List) ct.newInstance();
                        Class o = l.get(0).getClass();
                        className = o.getName();
                        if (className.contains(Constants.ENTITY_BASE_PACKAGES)) {

                        }
                    } else {
                        if (f.getAnnotation(IDField.class) != null) {//判断是否是主键
                            sb.append(underline(name)).append(" ").append(convertType(type)).append(" NOT NULL");
                            primarykey = name;
                        } else if (f.getAnnotation(NotNull.class) != null) {//判断是否不可为空
                            sb.append(underline(name)).append(" ").append(convertType(type)).append(" NOT NULL");
                        } else {
                            sb.append(underline(name)).append(" ").append(convertType(type));
                        }
                        sb.append(",");
                        if (f.getAnnotation(FieldUnique.class) != null) {
                            uniques.add(name);
                        }
                    }
                }
            }
            sb.append(" PRIMARY KEY(`").append(primarykey).append("`),");
            int i = 0;

            StringBuilder sb1 = new StringBuilder();
            for (String foreign : foreigns) {
                String[] foreignkeys = foreign.split(":");
                String foreignkey = "t_" + foreignkeys[0] + "_fk" + i;
                sb.append("KEY `").append(foreignkey).append("` (`").
                        append(foreignkeys[1]).append("`),");
                sb1.append("CONSTRAINT `").append(foreignkey).append("`")
                        .append(" ").append("FOREIGN KEY").append(" (`").append(foreignkeys[1]).append("`)")
                        .append(" REFERENCES `").append(foreignkeys[0]).append("` (`").append(foreignkeys[2]).append("`),");
                i++;
            }
            sb.append(sb1);

            for (String unique : uniques) {
                sb.append("UNIQUE KEY `").append(simpleName.toLowerCase()).
                        append(underline(unique)).append("_indexs").append("` (`").append(unique).append("`),");
            }
            String createSQL = sb.substring(0, sb.length() - 1)+") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
            PreparedStatement ps = conn.prepareStatement(createSQL);
            int j = ps.executeUpdate();
            if (j > 0) {
                conn.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String convertType(String t) {
        t = getSimpleName(t);
        String type = "";
        switch (t) {
            case "date":
                type = "TIMESTAMP";
                break;
            case "int":
                type = "Integer";
                break;
            case "double":
                type = "Double";
                break;
            case "float":
                type = "Float";
                break;
            default:
                type = "VARCHAR(36)";
                break;
        }
        return type;
    }


    /**
     * 驼峰转下划线
     *
     * @param name
     * @return
     */
    public static String underline(String name) {
        name = (name.charAt(0) + "").toLowerCase() + name.substring(1);
        Pattern p = Pattern.compile("[A-Z]");
        Matcher matcher = p.matcher(name);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


}
