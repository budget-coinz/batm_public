/*************************************************************************************
 * Copyright (C) 2014-2020 GENERAL BYTES s.r.o. All rights reserved.
 *
 * This software may be distributed and modified under the terms of the GNU
 * General Public License version 2 (GPL2) as published by the Free Software
 * Foundation and appearing in the file GPL2.TXT included in the packaging of
 * this file. Please note that GPL2 Section 2[b] requires that all works based
 * on this software must also be made publicly available under the terms of
 * the GPL2 ("Copyleft").
 *
 * Contact information
 * -------------------
 *
 * GENERAL BYTES s.r.o.
 * Web      :  http://www.generalbytes.com
 *
 ************************************************************************************/
package com.budgetcoinz.batm.server.extensions.rest.edd;

import com.budgetcoinz.batm.server.extensions.shared.ExtensionRestResponse;
import com.budgetcoinz.batm.server.extensions.rest.BudgetCoinzRestExtension;
import com.budgetcoinz.batm.server.extensions.rest.edd.models.EddObject;
import com.budgetcoinz.batm.server.extensions.rest.edd.models.EddPropertyObject;
import com.budgetcoinz.batm.server.extensions.shared.IdentityPieceBc;
import com.generalbytes.batm.server.extensions.*;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

import static java.util.Comparator.*;

/**
 * REST service implementation class that uses JSR-000311 JAX-RS
 */
@Path("/")
public class EddRestService {
    protected final Logger log = LoggerFactory.getLogger("batm.master.budgetcoinz.EddRestService");
    private final IExtensionContext ctx = BudgetCoinzRestExtension.getExtensionContext();
    ExtensionRestResponse invalidApiKey = new ExtensionRestResponse(401, "Unauthorized/Invalid API KEY");

    @POST
    @Path("/rejection")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object EddRejection(@HeaderParam("X-Api-Key") String apiKey, String data) {
        log.debug("POST /edd/rejection called");

        if(!canAccess(apiKey)){
            return invalidApiKey;
        }

        try {
            log.debug("Deserializing Wordpress EDD Form");
            EddObject eddDataObject = new Gson().fromJson(data, EddObject.class);

            List<EddPropertyObject> eddEntries = new ArrayList<>();
            for(Map.Entry<String, EddPropertyObject> properties : eddDataObject.data.entrySet()){
                eddEntries.add(properties.getValue());
            }

            log.debug("Getting Identity by Phone Number");
            List<IIdentity> potentialIdentities = ctx.findIdentityByPhoneNumber(eddEntries
                .stream()
                .filter(x -> x.getName().equals("Phone"))
                .findAny()
                .orElse(null)
                .getValue(), "US");

            if(potentialIdentities.stream().count() > 0){
                log.debug("Getting Latest Created Identity");
                IIdentity identity = potentialIdentities.stream().max(comparing(IIdentity::getCreated)).get();

                log.debug("Updating Identity State and Inserting Autoresponse into Identity Notes");
                ctx.updateIdentity(identity.getPublicId(),
                    identity.getExternalId(),
                    IIdentity.STATE_PROHIBITED,
                    identity.getType(),
                    identity.getCreated(),
                    identity.getRegistered(),
                    identity.getVipBuyDiscount(),
                    identity.getVipSellDiscount(),
                    buildAutoResonse(eddEntries),
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
                    identity.getConfigurationCashCurrency()
                );

                String ssn = eddEntries.stream().filter(x -> x.getName().equals("Social Security Number (SSN)")).findAny().orElse(null).getValue();
                String occupation = eddEntries.stream().filter(x -> x.getName().equals("Occupation")).findAny().orElse(null).getValue();

                ctx.updateIdentityPiecePersonalInfo(identity.getPublicId(), IdentityPieceBc.fromPersonalInfo(
                    identity.getIdentityPieces().stream().max(comparing(IIdentityPiece::getCreated)).get().getFirstname(),
                    identity.getIdentityPieces().stream().max(comparing(IIdentityPiece::getCreated)).get().getLastname(),
                    "",
                    IIdentityPiece.TYPE_PERSONAL_INFORMATION,
                    null,
                    null,
                    "",
                    "",
                    "",
                    "",
                    "",
                    null,
                    occupation,
                    ssn));

                JsonObject returnObject = new JsonObject();
                returnObject.addProperty("publicId", identity.getPublicId());
                returnObject.addProperty("autoResponse", buildAutoResonse(eddEntries));
                return new ExtensionRestResponse(200, returnObject.toString());
            }

            log.debug("Identity did not exist with phone number " + eddDataObject.getData().get("45").getValue());
            return new ExtensionRestResponse(204, "Identity did not exist with phone number " + eddDataObject.getData().get("45").getValue());
        }catch(Exception ex){
         log.error("POST /edd/eddrejection Error: " + ex);
         return new ExtensionRestResponse(500, ex.toString());
        }
    }

    @POST
    @Path("/approved/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Object EddApproved(@HeaderParam("X-Api-Key") String apiKey, @PathParam("id") String publicId) {
        log.debug("POST /edd/approved called");

        if(!canAccess(apiKey)){
            return invalidApiKey;
        }

        try {
            log.debug("Getting Identity by Public Id");
            IIdentity identity = ctx.findIdentityByIdentityId(publicId);

            log.debug("Updating Total Identity Cash Limit to 0 (remove limit)");
            ctx.updateIdentity(identity.getPublicId(),
                identity.getExternalId(),
                identity.getState(),
                identity.getType(),
                identity.getCreated(),
                identity.getRegistered(),
                identity.getVipBuyDiscount(),
                identity.getVipSellDiscount(),
                "",
                identity.getLimitCashPerTransaction(),
                identity.getLimitCashPerHour(),
                identity.getLimitCashPerDay(),
                identity.getLimitCashPerWeek(),
                identity.getLimitCashPerMonth(),
                identity.getLimitCashPer3Months(),
                identity.getLimitCashPer12Months(),
                identity.getLimitCashPerCalendarQuarter(),
                identity.getLimitCashPerCalendarYear(),
                new ArrayList<>(),
                identity.getConfigurationCashCurrency());

            log.debug("Update SSN");
            
            return new ExtensionRestResponse(200, "Identity Total Cash Limit Updated");
        }catch (Exception ex){
            log.error("POST /edd/approved Error: " + ex);
            return new ExtensionRestResponse(500, ex.toString());
        }
    }

    private boolean canAccess(String apiKey){
        return ctx.getAPIAccessByKey(apiKey, ApiAccessType.OSW) != null;
    }

    private String buildAutoResonse(List<EddPropertyObject> eddEntries){
        StringBuilder sb = new StringBuilder();

        sb.append("EDD/EDD-F1 auto populated by Zapier \n\n");
        sb.append("Customer Questionnaire (EDD) - Autoresponse / EDD-F1 \n\n");

        eddEntries.remove(EddPropertyObject.class.getName().equals("Social Security Number (SSN)"));

        for(EddPropertyObject entry : eddEntries){
            sb.append(entry.getName().replace("'", "\\'") + ": " + entry.getValue().replace("'", "\\'") + "\n");
        }

        return sb.toString();
    }
}
