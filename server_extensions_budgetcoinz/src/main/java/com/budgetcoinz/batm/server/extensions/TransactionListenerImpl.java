package com.budgetcoinz.batm.server.extensions;

import com.generalbytes.batm.server.extensions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TransactionListenerImpl implements ITransactionListener {
    private IExtensionContext ctx;
    protected final Logger log = LoggerFactory.getLogger("batm.master.budgetcoinz");


    public TransactionListenerImpl(IExtensionContext ctx) {
        this.ctx = ctx;
    }

    //refactor to our own logic
    @Override
    public OutputQueueInsertConfig overrideOutputQueueInsertConfig(ITransactionQueueRequest transactionQueueRequest, OutputQueueInsertConfig outputQueueInsertConfig) {
        if(isItSuspect(transactionQueueRequest.getIdentityPublicId(), transactionQueueRequest.getCashAmount())){
            outputQueueInsertConfig.setManualApprovalRequired((true));
        }

        if(transactionQueueRequest.getCashAmount().compareTo(new BigDecimal(2)) > 0){
            outputQueueInsertConfig.setInsertIntoSecondaryQueue(true);
        }
        return outputQueueInsertConfig;
    }

    boolean isItSuspect(String identityPublicId, BigDecimal currentAmmount){
        List<ITransactionDetails> txs = ctx.findAllTransactionsByIdentityId(identityPublicId);

        BigDecimal sum = currentAmmount;
        Date now = new Date();
        for(ITransactionDetails tx: txs){
            if(now.getTime() < tx.getServerTime().getTime() + (100*60*60*24)){
                sum = sum.add(tx.getCashAmount());
            }
        }


        return sum.compareTo(new BigDecimal("3")) > 0;
    }

    //can remove
    @Override
    public Map<String, String> onTransactionCreated(ITransactionDetails transactionDetails) {
        log.info("Bravo! Transaction has been created! Here are the details: = " + transactionDetails);
        ctx.sendSMSAsync(transactionDetails.getTerminalSerialNumber(), "+15867382274", "It Worked");
        return null;
    }

    @Override
    public Map<String, String> onTransactionUpdated(ITransactionDetails transactionDetails) {
        log.info("Hey! Transaction has been updated! Here are the details: " + transactionDetails);
        return null;
    }
}
