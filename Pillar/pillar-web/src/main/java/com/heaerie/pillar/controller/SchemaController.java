package com.heaerie.pillar.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/schema")
public class SchemaController {
    static final Logger logger = LogManager.getLogger();

    @GET
    @Path("/basicDet.sjson")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Context HttpHeaders httpHeaders) {
        String resp = "[{\"group\":\"USS\",\"name\":\"register\",\"label\":\"Register\",\"task\":\"ES\",\"desc\":\"\",\"htmlType\":\"CONTAINER\",\"entitle\":\"READONLY\",\"enttlname\":\"\",\"mndf\":\"N\",\"dataType\":\"CONTAINER\",\"cclass\":\"ctable\",\"parent\":\"\",\"parentHtmlType\":\"\",\"validate\":\"\",\"dflt\":\"\",\"min\":\"0\",\"max\":\"60\",\"tips\":\"\",\"onkeyup\":\"onKeyUp(this);\",\"onchange\":\"onChange(this);\",\"onkeydown\":\"onKeyDown(this);\",\"onkeypress\":\"onKeyPress(this);\",\"onclick\":\"onClick(this);\",\"onblure\":\"onBlure(this);\",\"listVal\":\"||A|A-ADD|M|M-MODIFY|I|I-INQURY|C|C-CANCEL|V|V-VERIFY\",\"help\":\"N\",\"helpLink\":\"helpload\",\"xml\":\"Y\",\"xmlname\":\"\",\"Xpath\":\"/\",\"maxCol\":\"1\",\"col\":\"0\",\"childs\":[{\"group\":\"USS\",\"name\":\"fName\",\"label\":\"First Name\",\"task\":\"NONE\",\"desc\":\"\",\"htmlType\":\"INPUT\",\"entitle\":\"NONREADONLY\",\"enttlname\":\"\",\"mndf\":\"Y\",\"dataType\":\"VARCHAR\",\"cclass\":\"ctable\",\"parent\":\"\",\"parentHtmlType\":\"\",\"validate\":\"\",\"dflt\":\"\",\"min\":\"0\",\"max\":\"30\",\"tips\":\"First Name / Given Name\",\"onkeyup\":\"onKeyUp(this);\",\"onchange\":\"onChange(this);\",\"onkeydown\":\"onKeyDown(this);\",\"onkeypress\":\"onKeyPress(this);\",\"onclick\":\"onClick(this);\",\"onblure\":\"onBlure(this);\",\"listVal\":\"||A|A-ADD|M|M-MODIFY|I|I-INQURY|C|C-CANCEL|V|V-VERIFY\",\"help\":\"N\",\"helpLink\":\"helpload\",\"xml\":\"Y\",\"xmlname\":\"\",\"Xpath\":\"/\",\"maxCol\":\"1\",\"col\":\"0\",\"childs\":[]},{\"group\":\"USS\",\"name\":\"lName\",\"label\":\"Last Name\",\"task\":\"NONE\",\"desc\":\"\",\"htmlType\":\"INPUT\",\"entitle\":\"NONREADONLY\",\"enttlname\":\"\",\"mndf\":\"Y\",\"dataType\":\"VARCHAR\",\"cclass\":\"ctable\",\"parent\":\"\",\"parentHtmlType\":\"\",\"validate\":\"\",\"dflt\":\"\",\"min\":\"0\",\"max\":\"30\",\"tips\":\"Lasr Name/Surname\",\"onkeyup\":\"onKeyUp(this);\",\"onchange\":\"onChange(this);\",\"onkeydown\":\"onKeyDown(this);\",\"onkeypress\":\"onKeyPress(this);\",\"onclick\":\"onClick(this);\",\"onblure\":\"onBlure(this);\",\"listVal\":\"||A|A-ADD|M|M-MODIFY|I|I-INQURY|C|C-CANCEL|V|V-VERIFY\",\"help\":\"N\",\"helpLink\":\"helpload\",\"xml\":\"Y\",\"xmlname\":\"\",\"Xpath\":\"/\",\"maxCol\":\"1\",\"col\":\"0\",\"childs\":[]},{\"group\":\"USS\",\"name\":\"mName\",\"label\":\"Middle Name\",\"task\":\"NONE\",\"desc\":\"\",\"htmlType\":\"INPUT\",\"entitle\":\"NONREADONLY\",\"enttlname\":\"\",\"mndf\":\"N\",\"dataType\":\"VARCHAR\",\"cclass\":\"ctable\",\"parent\":\"\",\"parentHtmlType\":\"\",\"validate\":\"\",\"dflt\":\"\",\"min\":\"0\",\"max\":\"20\",\"tips\":\"Initial / Middle Name \",\"onkeyup\":\"onKeyUp(this);\",\"onchange\":\"onChange(this);\",\"onkeydown\":\"onKeyDown(this);\",\"onkeypress\":\"onKeyPress(this);\",\"onclick\":\"onClick(this);\",\"onblure\":\"onBlure(this);\",\"listVal\":\"||A|A-ADD|M|M-MODIFY|I|I-INQURY|C|C-CANCEL|V|V-VERIFY\",\"help\":\"N\",\"helpLink\":\"helpload\",\"xml\":\"Y\",\"xmlname\":\"\",\"Xpath\":\"/\",\"maxCol\":\"1\",\"col\":\"0\",\"childs\":[]},{\"group\":\"USS\",\"name\":\"email\",\"label\":\"email address\",\"task\":\"NONE\",\"desc\":\"\",\"htmlType\":\"INPUT\",\"entitle\":\"NONREADONLY\",\"enttlname\":\"\",\"mndf\":\"Y\",\"dataType\":\"VARCHAR\",\"cclass\":\"ctable\",\"parent\":\"\",\"parentHtmlType\":\"\",\"validate\":\"\",\"dflt\":\"\",\"min\":\"0\",\"max\":\"40\",\"tips\":\"email@myroomexpense.com\",\"onkeyup\":\"onKeyUp(this);\",\"onchange\":\"onChange(this);\",\"onkeydown\":\"onKeyDown(this);\",\"onkeypress\":\"onKeyPress(this);\",\"onclick\":\"onClick(this);\",\"onblure\":\"onBlure(this);\",\"listVal\":\"||A|A-ADD|M|M-MODIFY|I|I-INQURY|C|C-CANCEL|V|V-VERIFY\",\"help\":\"N\",\"helpLink\":\"helpload\",\"xml\":\"Y\",\"xmlname\":\"\",\"Xpath\":\"/\",\"maxCol\":\"1\",\"col\":\"0\",\"childs\":[]},{\"group\":\"USS\",\"name\":\"password\",\"label\":\"New Password\",\"task\":\"NONE\",\"desc\":\"\",\"htmlType\":\"INPUT\",\"entitle\":\"NONREADONLY\",\"enttlname\":\"\",\"mndf\":\"Y\",\"dataType\":\"VARCHAR\",\"cclass\":\"ctable\",\"parent\":\"\",\"parentHtmlType\":\"\",\"validate\":\"\",\"dflt\":\"\",\"min\":\"0\",\"max\":\"40\",\"tips\":\"example : !AWEwdf123\",\"onkeyup\":\"onKeyUp(this);\",\"onchange\":\"onChange(this);\",\"onkeydown\":\"onKeyDown(this);\",\"onkeypress\":\"onKeyPress(this);\",\"onclick\":\"onClick(this);\",\"onblure\":\"onBlure(this);\",\"listVal\":\"||A|A-ADD|M|M-MODIFY|I|I-INQURY|C|C-CANCEL|V|V-VERIFY\",\"help\":\"N\",\"helpLink\":\"helpload\",\"xml\":\"Y\",\"xmlname\":\"\",\"Xpath\":\"/\",\"maxCol\":\"1\",\"col\":\"0\",\"childs\":[]},{\"group\":\"USS\",\"name\":\"verify\",\"label\":\"Confirm Password\",\"task\":\"NONE\",\"desc\":\"\",\"htmlType\":\"INPUT\",\"entitle\":\"NONREADONLY\",\"enttlname\":\"\",\"mndf\":\"Y\",\"dataType\":\"VARCHAR\",\"cclass\":\"ctable\",\"parent\":\"\",\"parentHtmlType\":\"\",\"validate\":\"\",\"dflt\":\"\",\"min\":\"0\",\"max\":\"40\",\"tips\":\"example : !AWEwdf123\",\"onkeyup\":\"onKeyUp(this);\",\"onchange\":\"onChange(this);\",\"onkeydown\":\"onKeyDown(this);\",\"onkeypress\":\"onKeyPress(this);\",\"onclick\":\"onClick(this);\",\"onblure\":\"onBlure(this);\",\"listVal\":\"||A|A-ADD|M|M-MODIFY|I|I-INQURY|C|C-CANCEL|V|V-VERIFY\",\"help\":\"N\",\"helpLink\":\"helpload\",\"xml\":\"Y\",\"xmlname\":\"\",\"Xpath\":\"/\",\"maxCol\":\"1\",\"col\":\"0\",\"childs\":[]}]}]";
        return Response.status(200).type(MediaType.APPLICATION_JSON_TYPE).entity(resp).build();
    }

    @GET
    @Path("/signup.sjson")
    @Produces(MediaType.APPLICATION_JSON)
    public Response signup(@Context HttpHeaders httpHeaders) {
        String resp = "[{\"group\":\"USS\",\"name\":\"register\",\"label\":\"Register\",\"task\":\"ES\",\"desc\":\"\",\"htmlType\":\"CONTAINER\",\"entitle\":\"READONLY\",\"enttlname\":\"\",\"mndf\":\"N\",\"dataType\":\"CONTAINER\",\"cclass\":\"ctable\",\"parent\":\"\",\"parentHtmlType\":\"\",\"validate\":\"\",\"dflt\":\"\",\"min\":\"0\",\"max\":\"60\",\"tips\":\"\",\"onkeyup\":\"onKeyUp(this);\",\"onchange\":\"onChange(this);\",\"onkeydown\":\"onKeyDown(this);\",\"onkeypress\":\"onKeyPress(this);\",\"onclick\":\"onClick(this);\",\"onblure\":\"onBlure(this);\",\"listVal\":\"||A|A-ADD|M|M-MODIFY|I|I-INQURY|C|C-CANCEL|V|V-VERIFY\",\"help\":\"N\",\"helpLink\":\"helpload\",\"xml\":\"Y\",\"xmlname\":\"\",\"Xpath\":\"/\",\"maxCol\":\"1\",\"col\":\"0\",\"childs\":[{\"group\":\"USS\",\"name\":\"fName\",\"label\":\"First Name\",\"task\":\"NONE\",\"desc\":\"\",\"htmlType\":\"INPUT\",\"entitle\":\"NONREADONLY\",\"enttlname\":\"\",\"mndf\":\"Y\",\"dataType\":\"VARCHAR\",\"cclass\":\"ctable\",\"parent\":\"\",\"parentHtmlType\":\"\",\"validate\":\"\",\"dflt\":\"\",\"min\":\"0\",\"max\":\"30\",\"tips\":\"First Name / Given Name\",\"onkeyup\":\"onKeyUp(this);\",\"onchange\":\"onChange(this);\",\"onkeydown\":\"onKeyDown(this);\",\"onkeypress\":\"onKeyPress(this);\",\"onclick\":\"onClick(this);\",\"onblure\":\"onBlure(this);\",\"listVal\":\"||A|A-ADD|M|M-MODIFY|I|I-INQURY|C|C-CANCEL|V|V-VERIFY\",\"help\":\"N\",\"helpLink\":\"helpload\",\"xml\":\"Y\",\"xmlname\":\"\",\"Xpath\":\"/\",\"maxCol\":\"1\",\"col\":\"0\",\"childs\":[]},{\"group\":\"USS\",\"name\":\"lName\",\"label\":\"Last Name\",\"task\":\"NONE\",\"desc\":\"\",\"htmlType\":\"INPUT\",\"entitle\":\"NONREADONLY\",\"enttlname\":\"\",\"mndf\":\"Y\",\"dataType\":\"VARCHAR\",\"cclass\":\"ctable\",\"parent\":\"\",\"parentHtmlType\":\"\",\"validate\":\"\",\"dflt\":\"\",\"min\":\"0\",\"max\":\"30\",\"tips\":\"Lasr Name/Surname\",\"onkeyup\":\"onKeyUp(this);\",\"onchange\":\"onChange(this);\",\"onkeydown\":\"onKeyDown(this);\",\"onkeypress\":\"onKeyPress(this);\",\"onclick\":\"onClick(this);\",\"onblure\":\"onBlure(this);\",\"listVal\":\"||A|A-ADD|M|M-MODIFY|I|I-INQURY|C|C-CANCEL|V|V-VERIFY\",\"help\":\"N\",\"helpLink\":\"helpload\",\"xml\":\"Y\",\"xmlname\":\"\",\"Xpath\":\"/\",\"maxCol\":\"1\",\"col\":\"0\",\"childs\":[]},{\"group\":\"USS\",\"name\":\"mName\",\"label\":\"Middle Name\",\"task\":\"NONE\",\"desc\":\"\",\"htmlType\":\"INPUT\",\"entitle\":\"NONREADONLY\",\"enttlname\":\"\",\"mndf\":\"N\",\"dataType\":\"VARCHAR\",\"cclass\":\"ctable\",\"parent\":\"\",\"parentHtmlType\":\"\",\"validate\":\"\",\"dflt\":\"\",\"min\":\"0\",\"max\":\"20\",\"tips\":\"Initial / Middle Name \",\"onkeyup\":\"onKeyUp(this);\",\"onchange\":\"onChange(this);\",\"onkeydown\":\"onKeyDown(this);\",\"onkeypress\":\"onKeyPress(this);\",\"onclick\":\"onClick(this);\",\"onblure\":\"onBlure(this);\",\"listVal\":\"||A|A-ADD|M|M-MODIFY|I|I-INQURY|C|C-CANCEL|V|V-VERIFY\",\"help\":\"N\",\"helpLink\":\"helpload\",\"xml\":\"Y\",\"xmlname\":\"\",\"Xpath\":\"/\",\"maxCol\":\"1\",\"col\":\"0\",\"childs\":[]},{\"group\":\"USS\",\"name\":\"email\",\"label\":\"email address\",\"task\":\"NONE\",\"desc\":\"\",\"htmlType\":\"INPUT\",\"entitle\":\"NONREADONLY\",\"enttlname\":\"\",\"mndf\":\"Y\",\"dataType\":\"VARCHAR\",\"cclass\":\"ctable\",\"parent\":\"\",\"parentHtmlType\":\"\",\"validate\":\"\",\"dflt\":\"\",\"min\":\"0\",\"max\":\"40\",\"tips\":\"email@myroomexpense.com\",\"onkeyup\":\"onKeyUp(this);\",\"onchange\":\"onChange(this);\",\"onkeydown\":\"onKeyDown(this);\",\"onkeypress\":\"onKeyPress(this);\",\"onclick\":\"onClick(this);\",\"onblure\":\"onBlure(this);\",\"listVal\":\"||A|A-ADD|M|M-MODIFY|I|I-INQURY|C|C-CANCEL|V|V-VERIFY\",\"help\":\"N\",\"helpLink\":\"helpload\",\"xml\":\"Y\",\"xmlname\":\"\",\"Xpath\":\"/\",\"maxCol\":\"1\",\"col\":\"0\",\"childs\":[]},{\"group\":\"USS\",\"name\":\"password\",\"label\":\"New Password\",\"task\":\"NONE\",\"desc\":\"\",\"htmlType\":\"INPUT\",\"entitle\":\"NONREADONLY\",\"enttlname\":\"\",\"mndf\":\"Y\",\"dataType\":\"VARCHAR\",\"cclass\":\"ctable\",\"parent\":\"\",\"parentHtmlType\":\"\",\"validate\":\"\",\"dflt\":\"\",\"min\":\"0\",\"max\":\"40\",\"tips\":\"example : !AWEwdf123\",\"onkeyup\":\"onKeyUp(this);\",\"onchange\":\"onChange(this);\",\"onkeydown\":\"onKeyDown(this);\",\"onkeypress\":\"onKeyPress(this);\",\"onclick\":\"onClick(this);\",\"onblure\":\"onBlure(this);\",\"listVal\":\"||A|A-ADD|M|M-MODIFY|I|I-INQURY|C|C-CANCEL|V|V-VERIFY\",\"help\":\"N\",\"helpLink\":\"helpload\",\"xml\":\"Y\",\"xmlname\":\"\",\"Xpath\":\"/\",\"maxCol\":\"1\",\"col\":\"0\",\"childs\":[]},{\"group\":\"USS\",\"name\":\"verify\",\"label\":\"Confirm Password\",\"task\":\"NONE\",\"desc\":\"\",\"htmlType\":\"INPUT\",\"entitle\":\"NONREADONLY\",\"enttlname\":\"\",\"mndf\":\"Y\",\"dataType\":\"VARCHAR\",\"cclass\":\"ctable\",\"parent\":\"\",\"parentHtmlType\":\"\",\"validate\":\"\",\"dflt\":\"\",\"min\":\"0\",\"max\":\"40\",\"tips\":\"example : !AWEwdf123\",\"onkeyup\":\"onKeyUp(this);\",\"onchange\":\"onChange(this);\",\"onkeydown\":\"onKeyDown(this);\",\"onkeypress\":\"onKeyPress(this);\",\"onclick\":\"onClick(this);\",\"onblure\":\"onBlure(this);\",\"listVal\":\"||A|A-ADD|M|M-MODIFY|I|I-INQURY|C|C-CANCEL|V|V-VERIFY\",\"help\":\"N\",\"helpLink\":\"helpload\",\"xml\":\"Y\",\"xmlname\":\"\",\"Xpath\":\"/\",\"maxCol\":\"1\",\"col\":\"0\",\"childs\":[]}]}]";
        return Response.status(200).type(MediaType.APPLICATION_JSON_TYPE).entity(resp).build();
    }

}