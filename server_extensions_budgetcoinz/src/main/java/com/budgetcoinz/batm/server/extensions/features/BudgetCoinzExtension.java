package com.budgetcoinz.batm.server.extensions.features;

import com.budgetcoinz.batm.server.extensions.RESTBudgetCoinz;
import com.budgetcoinz.batm.server.extensions.TransactionListenerImpl;
import com.generalbytes.batm.server.extensions.AbstractExtension;
import com.generalbytes.batm.server.extensions.IExtensionContext;
import com.generalbytes.batm.server.extensions.IRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class BudgetCoinzExtension extends AbstractExtension {

    protected final Logger log = LoggerFactory.getLogger("batm.master.BudgetCoinzExtension");
    public static IExtensionContext ctx;

    @Override
    public String getName() {
        return "BudgetCoinz extensions";
    }


    @Override
    public void init(IExtensionContext ctx) {
        super.init(ctx);
        BudgetCoinzExtension.ctx = ctx;

        log.debug("BudgetCoinz extension initialized. Adding listener");
        ctx.addTransactionListener(new TransactionListenerImpl(ctx));
    }

    @Override
    public Set<IRestService> getRestServices() {
        HashSet<IRestService> services = new HashSet<>();
        //This is simplest implementation of rest service
        services.add(new IRestService() {
            @Override
            public String getPrefixPath() {
                return "example";
            }

            @Override
            public Class getImplementation() {
                return RESTBudgetCoinz.class;
            }

        });

        return services;
    }

    public static IExtensionContext getExtensionContext() {
        return BudgetCoinzExtension.ctx;
    }
}
