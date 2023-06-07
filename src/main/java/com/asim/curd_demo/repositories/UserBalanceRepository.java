package com.asim.curd_demo.repositories;

import com.asim.curd_demo.model.userbalance.UpdateBalanceAndTransactionResponse;
import com.asim.curd_demo.model.userbalance.UserBalanceDTO;


public interface UserBalanceRepository {

    UserBalanceDTO findUserBalanceByUserId(long userId);

    UserBalanceDTO updateUserBalanceAndReverseBalance(long userId, double amount);


    UserBalanceDTO updateReverseBalance(long userId, double amount);

    UpdateBalanceAndTransactionResponse updateTransactionAndBalance(long transactionId, int status, long userId, double reverseAmount, double activeAmount );

    UpdateBalanceAndTransactionResponse rollbackTransactionAndBalance(long transactionId, int status, long userId, double reverseAmount, double activeAmount );
}
