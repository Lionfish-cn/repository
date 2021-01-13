package com.code.repository.service;

import com.code.repository.service.base.IBaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IVerifyCodeService extends IBaseService {
    public void sendShortLetterCode(HttpServletRequest request, HttpServletResponse response);
    public void generateVerifyCode(HttpServletRequest request, HttpServletResponse response);
    public void validateVerificationCode(HttpServletRequest request, HttpServletResponse response);
}
