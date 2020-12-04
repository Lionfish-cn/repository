package com.electric.business.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IVerifyCodeService {
    public void sendShortLetterCode(HttpServletRequest request, HttpServletResponse response);
    public void generateVerifyCode(HttpServletRequest request, HttpServletResponse response);
    public void validateVerificationCode(HttpServletRequest request, HttpServletResponse response);
}
