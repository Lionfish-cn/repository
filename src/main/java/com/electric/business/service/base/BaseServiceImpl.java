package com.electric.business.service.base;

import com.electric.business.constants.Constants;
import com.electric.business.dao.base.BaseMapper;
import com.electric.business.entity.base.BaseEntity;
import com.electric.business.loader.MapperInitiative;
import com.electric.business.util.EntityUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BaseServiceImpl implements IBaseService {
    public final static Map<String, BaseMapper> mapper = MapperInitiative.myBatisMapper;


    @Override
    public String getModelName() {
        String[] packages = this.getClass().getName().split("\\.");
        String modelName = packages[packages.length-1].replaceAll("Service","").replaceAll("Impl","");
        return Constants.ENTITY_BASE_PACKAGES + modelName;
    }
    
    

    @Override
    public String save(BaseEntity baseEntity) {
        String entityName = EntityUtil.getSimpleName(baseEntity.getClass().getName());
        BaseMapper baseMapper = mapper.get(entityName);
        int i = baseMapper.insertSelective(baseEntity);
        if (i > 0)
            return baseEntity.getId();
        return null;
    }

    @Override
    public String update(BaseEntity baseEntity) {
        String entityName = EntityUtil.getSimpleName(baseEntity.getClass().getName());
        BaseMapper baseMapper = mapper.get(entityName);
        int i = baseMapper.updateSelective(baseEntity);
        if (i > 0)
            return baseEntity.getId();
        return null;
    }

    @Override
    public String delete(String id) {
        String entityName = EntityUtil.getSimpleName(getModelName());
        BaseMapper baseMapper = mapper.get(entityName);
        int i = baseMapper.delete(id);
        if (i > 0)
            return id;
        return null;
    }

    @Override
    public void deleteAll(String[] ids) {
        for(String id : ids){
            delete(id);
        }
    }

    @Override
    public List<BaseEntity> findList( Map params) {
        BaseMapper baseMapper = mapper.get(EntityUtil.getSimpleName(getModelName()));
        return baseMapper.findList(params);
    }

    @Override
    public BaseEntity findByPrimaryKey(String id) {
        return  mapper.get(EntityUtil.getSimpleName(getModelName())).findByPrimaryKey(id);
    }
}
