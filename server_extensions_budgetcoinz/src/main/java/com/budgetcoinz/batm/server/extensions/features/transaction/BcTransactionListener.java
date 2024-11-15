package com.budgetcoinz.batm.server.extensions.features.transaction;

import com.generalbytes.batm.server.extensions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class BcTransactionListener implements ITransactionListener {
    private IExtensionContext ctx;
    protected final Logger log = LoggerFactory.getLogger("batm.master.BcTransactionListener");
    public BcTransactionListener(IExtensionContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public OutputQueueInsertConfig overrideOutputQueueInsertConfig(ITransactionQueueRequest transactionQueueRequest, OutputQueueInsertConfig outputQueueInsertConfig) {
        log.debug("BcTransactionListener entered for transaction " + transactionQueueRequest.getRemoteTransactionId());

        if(isStateMinnesota(transactionQueueRequest)){
            log.debug("State is Minnessota, checking to see if is new customer");
            if(isNewCustomer(transactionQueueRequest.getIdentityPublicId())){

                log.debug("Customer is new " + transactionQueueRequest.getIdentityPublicId());
                Date currentDate = new Date();

                log.debug("Setting unlock time to " + new Date(currentDate.getTime() + 72*60*60*1000L));
                outputQueueInsertConfig.setUnlockTime(new Date(currentDate.getTime() + 72*60*60*1000L));

                log.debug("Setting secondary manual approval to true");
                outputQueueInsertConfig.setSecondaryManualApprovalRequired(true);

                log.debug("Inserting transaction into secondary queue");
                outputQueueInsertConfig.setInsertIntoSecondaryQueue(true);
            }
        }

        return outputQueueInsertConfig;
    }



    boolean isStateMinnesota(ITransactionQueueRequest transactionQueueRequest){
        ITerminal terminal = ctx.findTerminalBySerialNumber(transactionQueueRequest.getTerminalSerialNumber());

        if(terminal.getLocation().getProvince().equals("MN")){
            return true;
        }

        return false;
    }

    boolean isNewCustomer(String identityPublicId){
        List<ITransactionDetails> transactions = ctx.findAllTransactionsByIdentityId(identityPublicId);

        ITransactionDetails earliestTransaction = transactions.stream()
            .min(Comparator.comparing(ITransactionDetails::getServerTime))
            .orElse(null);

        if(earliestTransaction != null){
            Date currentTime = new Date();
            if(earliestTransaction.getServerTime().before(new Date(currentTime.getTime() - (72*60*60*1000L)))){
                return false;
            }
        }

        return true;
    }
}
