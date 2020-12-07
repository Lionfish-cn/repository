package com.electric.business.service;

import com.electric.business.service.base.IBaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IVerifyCodeService extends IBaseService {
    public void sendShortLetterCode(HttpServletRequest request, HttpServletResponse response);
    public void generateVerifyCode(HttpServletRequest request, HttpServletResponse response);
    public void validateVerificationCode(HttpServletRequest request, HttpServletResponse response);
}
