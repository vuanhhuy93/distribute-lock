package com.asim.curd_demo.repositories;

import com.asim.curd_demo.model.userbalance.CreateUserBalanceTransactionResponse;
import com.asim.curd_demo.model.userbalance.UserBalanceTransactionDTO;
import com.asim.curd_demo.model.userbalance.UserBalanceTransactionResponse;

public interface UserBalanceTransactionRepository {

    UserBalanceTransactionDTO getUserBalanceTransactionById(long transactionId);

    CreateUserBalanceTransactionResponse createUserBalanceTransaction(UserBalanceTransactionDTO entity);

    UserBalanceTransactionResponse updateUserBalanceTransactionStatus(UserBalanceTransactionDTO entity);
}
