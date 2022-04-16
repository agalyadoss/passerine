package com.heaerie.pillar.controller;

import com.google.common.base.Strings;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.heaerie.common.ROLE005TT;
import com.heaerie.common.USER002TT;
import com.heaerie.pillar.service.PillarBadFormatException;
import com.heaerie.pillar.service.SqliteFactory;
import com.heaerie.pillar.storage.MAIL001TTMapper;
import com.heaerie.pillar.storage.ROLE005TTMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/v2/role")

public class RoleController {
    static final Logger logger = LogManager.getLogger();
    private static final String PENDING = "PENDING";
    Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            //.excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .create();

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(String requestBody, @Context HttpHeaders httpHeaders) {
        ROLE005TTMapper role005TTMapper = SqliteFactory.getROLE005TTMapper();
        logger.error("requestBody={}", requestBody);
        System.out.println("requestBody" + requestBody);
        RoleController.V2Create v2Create = new RoleController.V2Create(requestBody);
        return  v2Create.post();
    }

    private class V2Create extends Controller<ROLE005TT, ROLE005TTMapper> {
        String requestBody;
        ROLE005TT role;
        public V2Create(String requestBody) {
            super();
            this.requestBody = requestBody;
        }

        @Override
        public void validate() throws PillarBadFormatException {
            role = execute();
            if (Strings.isNullOrEmpty(role.getRole())) {
                throw new PillarBadFormatException("role is mandatory");
            }
            if (role.getRoleValue() == null) {
                throw new PillarBadFormatException("role_value is mandatory");
            }

        }

        @Override
        public ROLE005TT execute() {
            return gson.fromJson(requestBody, ROLE005TT.class);
        }

        @Override
        public ROLE005TTMapper getMapper() {
            return SqliteFactory.getROLE005TTMapper();
        }

        @Override
        protected List<ROLE005TT> viewer(List<ROLE005TT> input) {
            return input;
        }
    }
}
