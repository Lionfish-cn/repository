package com.electric.business.service.impl;

import com.electric.business.dao.CustomerMapper;
import com.electric.business.service.ICustomerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CustomerServiceImpl implements ICustomerService {
    @Resource
    CustomerMapper customerMapper;


}
