package com.shooterj.service.impl;

import com.shooterj.annotation.MyAutowired;
import com.shooterj.annotation.MyService;
import com.shooterj.annotation.MyTransactional;
import com.shooterj.dao.AccountDao;
import com.shooterj.pojo.Account;
import com.shooterj.service.TransferService;

/**
 * @author shooterj
 */
@MyService("transferService")
@MyTransactional
public class TransferServiceImpl implements TransferService {

    // 最佳状态
    @MyAutowired
    private AccountDao accountDao;

    // 构造函数传值/set方法传值

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }



    @Override
    public void transfer(String fromCardNo, String toCardNo, int money) throws Exception {
            Account from = accountDao.queryAccountByCardNo(fromCardNo);
            Account to = accountDao.queryAccountByCardNo(toCardNo);

            from.setMoney(from.getMoney()-money);
            to.setMoney(to.getMoney()+money);

            accountDao.updateAccountByCardNo(to);
            int c = 1/0;
            accountDao.updateAccountByCardNo(from);

    }
}
