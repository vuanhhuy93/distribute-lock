package com.asim.curd_demo.domains;

import com.asim.curd_demo.model.userbalance.*;
import com.asim.curd_demo.repositories.UserBalanceRepository;
import com.asim.curd_demo.repositories.UserBalanceTransactionRepository;
import com.asim.curd_demo.utils.ApplicationException;
import com.asim.curd_demo.utils.CONSTANT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;


@Component
public class UserBalanceDomain {

    @Autowired
    UserBalanceRepository userBalanceRepository;

    @Autowired
    UserBalanceTransactionRepository userBalanceTransactionRepository;


    public void handConfirmTransaction(long transactionId) {

        UserBalanceTransactionDTO dto = userBalanceTransactionRepository.getUserBalanceTransactionById(transactionId);

        if (dto == null) {
            throw new ApplicationException(-13, "not found by id");
        }

        UserBalanceDTO userBalanceDTO = userBalanceRepository.findUserBalanceByUserId(dto.getUserId());

        if (userBalanceDTO.getReverseBalance() < dto.getAmount()) {
            dto.setStatus(CONSTANT.USER_BALANCE_TRANSACTION_STATUS.FAIL);

            userBalanceTransactionRepository.updateUserBalanceTransactionStatus(dto);

            throw new ApplicationException(-11, "insufficient amount");
        }

        dto.setStatus(CONSTANT.USER_BALANCE_TRANSACTION_STATUS.SUCCESS);
        userBalanceTransactionRepository.updateUserBalanceTransactionStatus(dto);

        userBalanceRepository.update(dto.getUserId(),0D ,dto.getAmount() * -1, userBalanceDTO.getVersion());


    }

    public UserBalanceTransactionDTO createTransaction(long userId, double amount) throws ApplicationException {

        // tao mot transaction
        UserBalanceTransactionDTO userBalanceTransactionDTO = new UserBalanceTransactionDTO();

        userBalanceTransactionDTO.setUserId(userId);
        userBalanceTransactionDTO.setAmount(amount);
        userBalanceTransactionDTO.setActionType(1); // tru tien
        userBalanceTransactionDTO.setStatus(CONSTANT.USER_BALANCE_TRANSACTION_STATUS.CREATE);
        CreateUserBalanceTransactionResponse transactionResponse = userBalanceTransactionRepository.createUserBalanceTransaction(userBalanceTransactionDTO);

        if (transactionResponse == null || transactionResponse.getData() == null || transactionResponse.getData().getItem() == null
                || CollectionUtils.isEmpty(transactionResponse.getData().getItem().getItems())) {

            throw new ApplicationException(-12, "not create transaction");
        }

        userBalanceTransactionDTO = transactionResponse.getData().getItem().getItems().get(0);

        UserBalanceDTO userBalanceDTO = userBalanceRepository.findUserBalanceByUserId(userId);

        if (userBalanceDTO.getUserBalance() < amount) {
            userBalanceTransactionDTO.setStatus(CONSTANT.USER_BALANCE_TRANSACTION_STATUS.FAIL);

            userBalanceTransactionRepository.updateUserBalanceTransactionStatus(userBalanceTransactionDTO);

            throw new ApplicationException(-11, "insufficient amount");
        }

        UpdateBalanceAndTransactionResponse response = userBalanceRepository.updateTransactionAndBalance(userBalanceTransactionDTO.getId(),
                CONSTANT.USER_BALANCE_TRANSACTION_STATUS.PROCESS,
                userBalanceTransactionDTO.getUserId(), amount, amount * -1, userBalanceDTO.getVersion());

        if (response == null || response.getData() == null || response.getData().getUserBalanceData() == null
                || response.getData().getTransactionData() == null) {

            userBalanceTransactionDTO.setStatus(CONSTANT.USER_BALANCE_TRANSACTION_STATUS.FAIL);

            userBalanceTransactionRepository.updateUserBalanceTransactionStatus(userBalanceTransactionDTO);

            throw new ApplicationException(-15, "update user balance fail");
        }

        userBalanceTransactionDTO.setStatus(CONSTANT.USER_BALANCE_TRANSACTION_STATUS.PROCESS);

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

        UserBalanceDTO userBalanceDTO = userBalanceRepository.findUserBalanceByUserId(entity.getUserId());

        if (userBalanceDTO.getReverseBalance() < entity.getAmount()) {
            entity.setStatus(CONSTANT.USER_BALANCE_TRANSACTION_STATUS.FAIL);

            userBalanceTransactionRepository.updateUserBalanceTransactionStatus(entity);

            throw new ApplicationException(-11, "insufficient amount");
        }



        UpdateBalanceAndTransactionResponse response = userBalanceRepository.updateTransactionAndBalance(entity.getId(),
                CONSTANT.USER_BALANCE_TRANSACTION_STATUS.ROLLBACK,
                entity.getUserId(), entity.getAmount() * -1, entity.getAmount(), userBalanceDTO.getVersion());

        if (response == null || response.getData() == null || response.getData().getUserBalanceData() == null
                || response.getData().getTransactionData() == null) {
            throw new ApplicationException(-16, "rollback fail");
        }


    }
}
