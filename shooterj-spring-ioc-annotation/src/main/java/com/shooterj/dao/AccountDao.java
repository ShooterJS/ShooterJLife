package com.shooterj.dao;

import com.shooterj.pojo.Account;

/**
 * @author shooterj
 */
public interface AccountDao {

    Account queryAccountByCardNo(String cardNo) throws Exception;

    int updateAccountByCardNo(Account account) throws Exception;
}
