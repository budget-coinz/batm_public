package com.budgetcoinz.batm.server.extensions.rest.identity;

import com.budgetcoinz.batm.server.extensions.rest.BaseRestService;
import com.budgetcoinz.batm.server.extensions.rest.identity.models.IdentityCreateModel;
import com.budgetcoinz.batm.server.extensions.rest.identity.models.IdentityUpdateModel;
import com.budgetcoinz.batm.server.extensions.shared.ExtensionRestResponse;
import com.budgetcoinz.batm.server.extensions.shared.IdentityPieceBc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.generalbytes.batm.server.extensions.IIdentity;
import com.generalbytes.batm.server.extensions.IIdentityPiece;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Path("/")
public class IdentityRestService extends BaseRestService {
    protected final Logger log = LoggerFactory.getLogger("batm.master.budgetcoinz.IdentityRestService");

    @POST
    @Path("/ssn/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object UpdateIdentity(@HeaderParam("X-Api-Key") String apiKey, String data) {
        log.debug("POST /identity/ssn/update called");

        if(!canAccess(apiKey)){
            return invalidApiKey;
        }

        try{
            IdentityUpdateModel model = new ObjectMapper().readValue(data, IdentityUpdateModel.class);

            IIdentity identity = ctx.findIdentityByIdentityId(model.getPublicId());

            List<IIdentityPiece> personalInfoPieces = identity.getIdentityPieces()
                .stream().filter(piece -> piece.getPieceType() == IIdentityPiece.TYPE_PERSONAL_INFORMATION).collect(Collectors.toList());

            String identityNote = "Identity Updated via CAS REST Extension 'IdentityRestService (/identity/update/ssn)'." + "\n" +
                "SSN was set to ***-**-" + model.getSsn().substring(model.getSsn().length() -4) + "\n" +
                "Extension was called from Zapier Automation " + model.getAutomation();
            ctx.updateIdentity(identity.getPublicId(),
                identity.getExternalId(),
                identity.getState(),
                identity.getType(),
                identity.getCreated(),
                identity.getRegistered(),
                identity.getVipBuyDiscount(),
                identity.getVipSellDiscount(),
                identityNote,
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

            if((long) personalInfoPieces.size() == 1){
                ctx.updateIdentityPiecePersonalInfo(model.getPublicId(), IdentityPieceBc.fromSsn(model.getSsn(), personalInfoPieces.get(0)));
            }else{
                for(IIdentityPiece piece : personalInfoPieces){
                    IdentityPieceBc pieceBc = IdentityPieceBc.fromSsn(model.getSsn(), piece);
                    ctx.updateIdentityPiecePersonalInfo(model.getPublicId(), pieceBc);
                }
            }

            identity = ctx.findIdentityByIdentityId(model.getPublicId());

            return new ExtensionRestResponse(200, "Successfully updated Identity " + model.getPublicId(), identity);
        }
        catch(Exception ex){
            return ex;
        }
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object RegisterIdentity(@HeaderParam("X-Api-Key") String apiKey, String data){
        log.debug("POST /identity/create called");

        if(!canAccess(apiKey)){
            return invalidApiKey;
        }

        try {
            IdentityCreateModel model = new ObjectMapper().readValue(data, IdentityCreateModel.class);

            IIdentity identity = ctx.addIdentity(
                "USD",
                "BT106938",
                null,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                "Created via Extension from Automation " + model.getAutomation(),
                IIdentity.STATE_TO_BE_VERIFIED,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                new Date(),
                new Date(),
                "en");

            ctx.addIdentityPiece(
                identity.getPublicId(),
                IdentityPieceBc.fromPersonalInfo(model.getFirstName(),
                    model.getLastName(),
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
                    "")
            );

            ctx.addIdentityPiece(identity.getPublicId(), IdentityPieceBc.fromPhoneNumber(model.getPhoneNumber()));

            return new ExtensionRestResponse(200, "Successfully registered Identity", ctx.findIdentityByIdentityId(identity.getPublicId()));
        }catch (Exception ex){
            return ex;
        }
    }
}
