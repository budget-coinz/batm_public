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
package com.budgetcoinz.batm.server.extensions;

import com.budgetcoinz.batm.server.extensions.features.BudgetCoinzExtension;
import com.budgetcoinz.batm.server.extensions.shared.ExtensionRestResponse;
import com.budgetcoinz.batm.server.extensions.shared.IdentityPieceBc;
import com.generalbytes.batm.server.extensions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 * REST service implementation class that uses JSR-000311 JAX-RS
 */
@Path("/")
public class RESTBudgetCoinz {
    private static final Logger log = LoggerFactory.getLogger("batm.master.extensions.RESTServiceExample");



    @GET
    @Path("/helloworld")
    @Produces(MediaType.APPLICATION_JSON)
    /**
     * Returns JSON response on following URL https://localhost:7743/extensions/example/helloworld
     */
    public Object helloWorld(@Context HttpServletRequest request, @Context HttpServletResponse response, @QueryParam("serial_number") String serialNumber) {
        log.debug("RESTTTT");
        String serverVersion = BudgetCoinzExtension.getExtensionContext().getServerVersion();
        return new ExtensionRestResponse(0, "Server version is: " + serverVersion);
    }

    @GET
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    /**
     * Returns JSON response on following URL https://localhost:7743/extensions/example/register
     */
    public Object register(@Context HttpServletRequest request, @Context HttpServletResponse response, @QueryParam("apiKey") String apiKey, @QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName) {
        log.debug("RegistrationCalled");

        IExtensionContext ctx = BudgetCoinzExtension.ctx;

        IApiAccess api = ctx.getAPIAccessByKey(apiKey, ApiAccessType.OSW);

        if(api == null){
            return new ExtensionRestResponse(0,"Bad API KEY");
        }

        IIdentity identity = ctx.addIdentity(
            "USD",
            "BT106938",
            null,
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>(),
            "Created via Extension",
            IIdentity.STATE_REGISTERED,
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            new Date(),
            new Date(),
            "en");

        ctx.addIdentityPiece(
            identity.getPublicId(),
            IdentityPieceBc.fromPersonalInfo(firstName,
                lastName,
                "",
                IIdentityPiece.DOCUMENT_TYPE_ID_CARD,
            null,
            null,
                "",
                "",
                "",
                "",
                "",
                null,
                "",
                "123-45-6789")
        );

        ctx.addIdentityPiece(identity.getPublicId(), IdentityPieceBc.fromPhoneNumber("+15861234567"));

        return ctx.findIdentityByIdentityId(identity.getPublicId());
    }
}
