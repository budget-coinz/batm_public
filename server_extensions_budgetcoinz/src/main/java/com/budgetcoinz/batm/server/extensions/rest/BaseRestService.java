package com.budgetcoinz.batm.server.extensions.rest;

import com.budgetcoinz.batm.server.extensions.shared.ExtensionRestResponse;
import com.generalbytes.batm.server.extensions.ApiAccessType;
import com.generalbytes.batm.server.extensions.IExtensionContext;

public class BaseRestService {
    public final IExtensionContext ctx = BudgetCoinzRestExtension.getExtensionContext();
    public ExtensionRestResponse invalidApiKey = new ExtensionRestResponse(500, "Invalid API KEY");

    public ExtensionRestResponse invalidRequest(String data){
        return new ExtensionRestResponse(500, "Invalid Request", data);
    }

    public boolean canAccess(String apiKey){
        return ctx.getAPIAccessByKey(apiKey, ApiAccessType.OSW) != null;
    }
}
