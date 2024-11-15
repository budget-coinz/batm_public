package com.budgetcoinz.batm.server.extensions.features;

import com.budgetcoinz.batm.server.extensions.features.transaction.BcTransactionListener;
import com.generalbytes.batm.server.extensions.AbstractExtension;
import com.generalbytes.batm.server.extensions.IExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BudgetCoinzListenersExtension extends AbstractExtension {

    protected final Logger log = LoggerFactory.getLogger("batm.master.BudgetCoinzListenerExtension");
    public static IExtensionContext ctx;

    @Override
    public String getName() {
        return "BudgetCoinz Listener Extension";
    }

    @Override
    public void init(IExtensionContext ctx) {
        super.init(ctx);
        BudgetCoinzListenersExtension.ctx = ctx;

        log.debug("BudgetCoinz extension initialized. Adding BcTransactionListener");
        ctx.addTransactionListener(new BcTransactionListener(ctx));
    }

    public static IExtensionContext getExtensionContext() {
        return BudgetCoinzListenersExtension.ctx;
    }
}
