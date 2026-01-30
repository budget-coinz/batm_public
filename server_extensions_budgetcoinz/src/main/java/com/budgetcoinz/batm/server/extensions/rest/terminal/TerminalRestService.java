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
package com.budgetcoinz.batm.server.extensions.rest.terminal;

import com.budgetcoinz.batm.server.extensions.shared.ExtensionRestResponse;
import com.budgetcoinz.batm.server.extensions.rest.BudgetCoinzRestExtension;
import com.generalbytes.batm.server.extensions.ApiAccessType;
import com.generalbytes.batm.server.extensions.IApiAccess;
import com.generalbytes.batm.server.extensions.IExtensionContext;
import com.generalbytes.batm.server.extensions.ITerminal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * REST service implementation class that uses JSR-000311 JAX-RS
 */
@Path("/")
public class TerminalRestService {
    protected final Logger log = LoggerFactory.getLogger("batm.master.budgetcoinz.TerminalRestService");
    private final IExtensionContext ctx = BudgetCoinzRestExtension.getExtensionContext();
    ExtensionRestResponse invalidApiKey = new ExtensionRestResponse(500, "Invalid API KEY");

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    /**
     * Returns JSON response of All Active Terminals on following URL https://localhost:7743/server/extensions/api/terminals
     */
    public Object getActiveTerminals(@QueryParam("apiKey") String apiKey) {
        log.debug("GET /terminal/all Called");

        if(!canAccess(apiKey)){
            return invalidApiKey;
        }

        List<ITerminal> allTerminals = ctx.findAllTerminals();
        allTerminals.removeIf(terminal -> !terminal.isActive());
        return allTerminals;
    }

    @GET
    @Path("/{serialNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    /**
     * Returns JSON response of Terminal on following URL https://localhost:7743/server/extensions/api/terminals/{serialNumber}
     */
    public Object helloWorld(@QueryParam("apiKey") String apiKey, @PathParam("serialNumber") String serialNumber) {
        log.debug("GET /terminal/{serialNumber} Called");

        if(!canAccess(apiKey)){
            return invalidApiKey;
        }

        ITerminal terminal = ctx.findTerminalBySerialNumber(serialNumber);

        if(terminal == null){
            return new ExtensionRestResponse(500, String.format("Invalid Serial Number %s", serialNumber));
        }

        return terminal;
    }

    private boolean canAccess(String apiKey){
        IApiAccess api = ctx.getAPIAccessByKey(apiKey, ApiAccessType.OSW);
        return api != null;
    }
}
