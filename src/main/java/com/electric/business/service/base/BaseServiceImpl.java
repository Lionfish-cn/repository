package com.electric.business.service.base;

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
    public String save(BaseEntity baseEntity) {
        String entityName = EntityUtil.getEntityName(baseEntity.getClass().getName());
        BaseMapper baseMapper = mapper.get(entityName);
        int i = baseMapper.insertSelective(baseEntity);
        if (i > 0)
            return baseEntity.getId();
        return null;
    }

    @Override
    public String update(BaseEntity baseEntity) {
        String entityName = EntityUtil.getEntityName(baseEntity.getClass().getName());
        BaseMapper baseMapper = mapper.get(entityName);
        int i = baseMapper.updateBySelective(baseEntity);
        if (i > 0)
            return baseEntity.getId();
        return null;
    }

    @Override
    public String delete(BaseEntity baseEntity) {
        String entityName = EntityUtil.getEntityName(baseEntity.getClass().getName());
        BaseMapper baseMapper = mapper.get(entityName);
        int i = baseMapper.delete(baseEntity.getId());
        if (i > 0)
            return baseEntity.getId();
        return null;
    }

    @Override
    public List<BaseEntity> find(BaseEntity baseEntity, Map params) {
        String entityName = EntityUtil.getEntityName(baseEntity.getClass().getName());
        BaseMapper baseMapper = mapper.get(entityName);
        return baseMapper.findList(params);
    }

    @Override
    public BaseEntity findByPrimaryKey(String id) {
        return null;
    }
}
