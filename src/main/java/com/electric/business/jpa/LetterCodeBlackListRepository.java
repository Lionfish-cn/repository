package com.electric.business.jpa;


import com.electric.business.entity.VerifyCodeBlackList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterCodeBlackListRepository extends JpaRepository<VerifyCodeBlackList, String> {
}
