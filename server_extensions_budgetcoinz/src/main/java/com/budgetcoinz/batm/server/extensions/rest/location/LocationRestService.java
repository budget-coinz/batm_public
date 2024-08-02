package com.budgetcoinz.batm.server.extensions.rest.location;

import com.budgetcoinz.batm.server.extensions.rest.BaseRestService;
import com.budgetcoinz.batm.server.extensions.rest.BudgetCoinzRestExtension;
import com.budgetcoinz.batm.server.extensions.rest.edd.models.EddObject;
import com.budgetcoinz.batm.server.extensions.rest.location.models.LocationCreateModel;
import com.budgetcoinz.batm.server.extensions.shared.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.generalbytes.batm.server.extensions.ApiAccessType;
import com.generalbytes.batm.server.extensions.IExtensionContext;
import com.generalbytes.batm.server.extensions.ILocation;
import com.generalbytes.batm.server.extensions.ILocationDetail;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.text.ParseException;
import java.util.ArrayList;

@Path("/")
public class LocationRestService extends BaseRestService {
    protected final Logger log = LoggerFactory.getLogger("batm.master.budgetcoinz.LocationRestService");

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object DeployTerminalToLocation(@HeaderParam("X-Api-Key") String apiKey, String data) throws JsonProcessingException {
        log.debug("POST /location/create called");

        if(!canAccess(apiKey)){
            return invalidApiKey;
        }

        try {
            LocationCreateModel model = new ObjectMapper().readValue(data, LocationCreateModel.class);
            LocationDetailBc locationDetail = new LocationDetailBc(
                model.getName(),
                model.getContactAddress(),
                model.getCity(),
                model.getCountry(),
                model.getCountry(),
                model.getProvince(),
                model.getZip(),
                model.getDescription(),
                model.getGpsLat(),
                model.getGpsLon(),
                model.getTimeZone(),
                null,
                new PersonBc(1L),
                new OrganizationBc("1"),
                null,
                1,
                new ArrayList<NoteBc>(),
                new ArrayList<OpeningHoursBc>(),
                new ArrayList<CashCollectionDayBc>());

                ILocation location = ctx.addLocation(locationDetail);
                return new ExtensionRestResponse(200, "Successfully created new location " + location.getName(), location);

        } catch (Exception ex){
            return ex;
        }
    }
}
