package com.budgetcoinz.batm.server.extensions.rest;

import com.budgetcoinz.batm.server.extensions.rest.edd.EddRestService;
import com.budgetcoinz.batm.server.extensions.rest.identity.IdentityRestService;
import com.budgetcoinz.batm.server.extensions.rest.location.LocationRestService;
import com.budgetcoinz.batm.server.extensions.rest.terminal.TerminalRestService;
import com.generalbytes.batm.server.extensions.AbstractExtension;
import com.generalbytes.batm.server.extensions.IExtensionContext;
import com.generalbytes.batm.server.extensions.IRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class BudgetCoinzRestExtension extends AbstractExtension {
    protected final Logger log = LoggerFactory.getLogger("batm.master.BudgetcoinzRestExtension");
    public static IExtensionContext ctx;

    @Override
    public String getName() {
        return "BudgetCoinz REST Extensions";
    }


    @Override
    public void init(IExtensionContext ctx) {
        super.init(ctx);
        BudgetCoinzRestExtension.ctx = ctx;
        log.debug("BudgetCoinz extension initialized. Adding listener");
    }

    @Override
    public Set<IRestService> getRestServices() {

        HashSet<IRestService> services = new HashSet<>();
        //This is simplest implementation of rest service

        services.add(new IRestService() {
            @Override
            public String getPrefixPath() {
                return "api/terminal";
            }

            @Override
            public Class getImplementation() {
                return TerminalRestService.class;
            }
        });

        services.add(new IRestService() {
            @Override
            public String getPrefixPath() {
                return "api/edd";
            }

            @Override
            public Class getImplementation() { return EddRestService.class; }
        });

        services.add(new IRestService() {
            @Override
            public String getPrefixPath() {
                return "api/location";
            }

            @Override
            public Class getImplementation() { return LocationRestService.class; }
        });

        services.add(new IRestService() {
            @Override
            public String getPrefixPath() {
                return "api/identity";
            }

            @Override
            public Class getImplementation() { return IdentityRestService.class; }
        });

        return services;
    }

    public static IExtensionContext getExtensionContext() {
        return ctx;
    }
}
