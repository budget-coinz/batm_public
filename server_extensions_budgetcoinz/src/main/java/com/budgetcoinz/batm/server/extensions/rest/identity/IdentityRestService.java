package com.budgetcoinz.batm.server.extensions.rest.identity;

import com.budgetcoinz.batm.server.extensions.rest.BudgetCoinzRestExtension;
import com.budgetcoinz.batm.server.extensions.shared.ExtensionRestResponse;
import com.generalbytes.batm.server.extensions.ApiAccessType;
import com.generalbytes.batm.server.extensions.IExtensionContext;
import com.generalbytes.batm.server.extensions.IIdentity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

public class IdentityRestService {
    protected final Logger log = LoggerFactory.getLogger("batm.master.budgetcoinz.IdentityRestService");
    private final IExtensionContext ctx = BudgetCoinzRestExtension.getExtensionContext();
    ExtensionRestResponse invalidApiKey = new ExtensionRestResponse(500, "Invalid API KEY");

    @POST
    @Path("/ssn/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object DeployTerminalToLocation(@HeaderParam("X-Api-Key") String apiKey, @QueryParam("publicId") String publicId, @QueryParam("ssn") String ssn) {
        log.debug("POST /location/create called");

        if(!canAccess(apiKey)){
            return invalidApiKey;
        }

        IIdentity identity = ctx.findIdentityByIdentityId(publicId);

        ctx.updateIdentity(identity.getPublicId(),
            identity.getExternalId(),
            identity.getState(),
            identity.getType(),
            identity.getCreated(),
            identity.getRegistered(),
            identity.getVipBuyDiscount(),
            identity.getVipSellDiscount(),
            "Identity Updated via CAS REST Extension 'IdentityRestService'. SSN was set to ***-**-" + "1234",
            identity.getLimitCashPerTransaction(),
            identity.getLimitCashPerHour(),
            identity.getLimitCashPerDay(),
            identity.getLimitCashPerWeek(),
            identity.getLimitCashPerMonth(),
            identity.getLimitCashPer3Months(),
            identity.getLimitCashPer12Months(),
            identity.getLimitCashPerCalendarQuarter(),
            identity.getLimitCashPerCalendarYear(),
            identity.getLimitCashTotalIdentity(),
            identity.getConfigurationCashCurrency());

        return new ExtensionRestResponse(200, "Successfully updated Identity" + publicId, "");
    }

    private boolean canAccess(String apiKey){ return ctx.getAPIAccessByKey(apiKey, ApiAccessType.OSW) != null; }
}
