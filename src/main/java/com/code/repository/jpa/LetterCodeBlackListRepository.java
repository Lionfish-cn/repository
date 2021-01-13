package com.code.repository.jpa;


import com.code.repository.entity.VerifyCodeBlackList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterCodeBlackListRepository extends JpaRepository<VerifyCodeBlackList, String> {
}
