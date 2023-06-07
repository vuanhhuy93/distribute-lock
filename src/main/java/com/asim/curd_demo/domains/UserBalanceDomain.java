package com.asim.curd_demo.domains;

import com.asim.curd_demo.model.userbalance.*;
import com.asim.curd_demo.repositories.UserBalanceRepository;
import com.asim.curd_demo.repositories.UserBalanceTransactionRepository;
import com.asim.curd_demo.utils.ApplicationException;
import com.asim.curd_demo.utils.CONSTANT;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.TimeUnit;

@Component
public class UserBalanceDomain {

    @Autowired
    UserBalanceRepository userBalanceRepository;

    @Autowired
    UserBalanceTransactionRepository userBalanceTransactionRepository;

    @Autowired
    RedissonClient redissonClient;

    public void handConfirmTransaction(long transactionId){

        UserBalanceTransactionDTO dto = userBalanceTransactionRepository.getUserBalanceTransactionById(transactionId);

        if (dto == null) {
            throw new ApplicationException(-13, "not found by id");
        }

        RLock rLock = redissonClient.getFairLock("user-balance-" + dto.getUserId());

        rLock.lock(5, TimeUnit.SECONDS);

        try {

            dto.setStatus(CONSTANT.USER_BALANCE_TRANSACTION_STATUS.SUCCESS);
            userBalanceTransactionRepository.updateUserBalanceTransactionStatus(dto);

            userBalanceRepository.updateReverseBalance(dto.getUserId(), dto.getAmount());

        } finally {
            rLock.unlock();
        }
    }

    public UserBalanceTransactionDTO createTransaction(long userId, double amount) throws ApplicationException {
        RLock rLock = redissonClient.getFairLock("user-balance-" + userId);

        rLock.lock(5, TimeUnit.SECONDS);
        // tao mot transaction
        UserBalanceTransactionDTO userBalanceTransactionDTO = new UserBalanceTransactionDTO();

        userBalanceTransactionDTO.setUserId(userId);
        userBalanceTransactionDTO.setAmount(amount);
        userBalanceTransactionDTO.setActionType(1); // tru tien
        userBalanceTransactionDTO.setStatus(CONSTANT.USER_BALANCE_TRANSACTION_STATUS.CREATE);
        CreateUserBalanceTransactionResponse transactionResponse = userBalanceTransactionRepository.createUserBalanceTransaction(userBalanceTransactionDTO);

        if (transactionResponse == null || transactionResponse.getData() == null || transactionResponse.getData().getItem() == null
                || CollectionUtils.isEmpty(transactionResponse.getData().getItem().getItems())) {
            rLock.unlock();
            throw new ApplicationException(-12, "not create transaction");
        }

        userBalanceTransactionDTO = transactionResponse.getData().getItem().getItems().get(0);

        UserBalanceDTO userBalanceDTO = userBalanceRepository.findUserBalanceByUserId(userId);

        if (userBalanceDTO.getUserBalance() < amount) {
            userBalanceTransactionDTO.setStatus(CONSTANT.USER_BALANCE_TRANSACTION_STATUS.FAIL);

            userBalanceTransactionRepository.updateUserBalanceTransactionStatus(userBalanceTransactionDTO);
            rLock.unlock();
            throw new ApplicationException(-11, "insufficient amount");
        }

        UpdateBalanceAndTransactionResponse response = userBalanceRepository.updateTransactionAndBalance(userBalanceTransactionDTO.getId(),
                CONSTANT.USER_BALANCE_TRANSACTION_STATUS.PROCESS,
                userBalanceTransactionDTO.getUserId(), amount , amount * -1);

        if (response == null || response.getData() == null || response.getData().getUserBalanceData() == null
                || response.getData().getTransactionData() == null){

            userBalanceTransactionDTO.setStatus(CONSTANT.USER_BALANCE_TRANSACTION_STATUS.FAIL);

            userBalanceTransactionRepository.updateUserBalanceTransactionStatus(userBalanceTransactionDTO);
            rLock.unlock();
            throw new ApplicationException(-15, "update user balance fail");
        }

        userBalanceTransactionDTO.setStatus(CONSTANT.USER_BALANCE_TRANSACTION_STATUS.PROCESS);
        rLock.unlock();
        return userBalanceTransactionDTO;
    }

    public void rollbackTransaction(long transactionId) {
        UserBalanceTransactionDTO dto = userBalanceTransactionRepository.getUserBalanceTransactionById(transactionId);

        if (dto == null) {
            throw new ApplicationException(-13, "not found by id");
        }

        this.rollbackTransaction(dto);

    }

    public void rollbackTransaction(UserBalanceTransactionDTO entity) {
        RLock rLock = redissonClient.getFairLock("user-balance-" + entity.getUserId());

        rLock.lock(5, TimeUnit.SECONDS);
        UpdateBalanceAndTransactionResponse response = userBalanceRepository.updateTransactionAndBalance(entity.getId(),
                CONSTANT.USER_BALANCE_TRANSACTION_STATUS.ROLLBACK,
                entity.getUserId(), entity.getAmount() * -1, entity.getAmount());
        rLock.unlock();
        if (response == null || response.getData() == null || response.getData().getUserBalanceData() == null
                || response.getData().getTransactionData() == null){
            throw new ApplicationException(-16, "rollback fail");
        }


    }
}
