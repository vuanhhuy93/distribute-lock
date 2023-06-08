package com.asim.curd_demo.repositories;

import com.asim.curd_demo.model.userbalance.UpdateBalanceAndTransactionResponse;
import com.asim.curd_demo.model.userbalance.UserBalanceDTO;


public interface UserBalanceRepository {

    UserBalanceDTO findUserBalanceByUserId(long userId);

    UserBalanceDTO update(long userId, double userBalance, double reverseBalance, double version);

    UpdateBalanceAndTransactionResponse updateTransactionAndBalance(long transactionId, int status, long userId, double reverseAmount, double activeAmount, double version );

    UpdateBalanceAndTransactionResponse rollbackTransactionAndBalance(long transactionId, int status, long userId, double reverseAmount, double activeAmount, double version );
}
