package gov.cdc.fhir.bser.redcap.controller;


import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import freemarker.template.TemplateException;
import gov.cdc.fhir.bser.redcap.model.RedCapFeedbackInstrument;
import gov.cdc.fhir.bser.redcap.model.RequestReferalInstrument;
import gov.cdc.fhir.bser.redcap.service.FHIRProxy;
import gov.cdc.fhir.bser.redcap.service.RedCapProxy;
import org.hl7.fhir.dstu3.model.Bundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping(value="/referral")
public class Referrals {

    @Autowired
    private FHIRProxy fhirProxy;
    @Autowired
    private RedCapProxy redCapProxy;

    @PutMapping("Bundle/{bundleId}")
    public String receiveReferral(@PathVariable String bundleId, @RequestBody(required=false) String body) {
        System.out.println("body = \n\n" + body + "\n\n");
        Bundle b = getBundle(body);
        if (b!=null) {
            RequestReferalInstrument redCapInstrument = fhirProxy.processReferral(b);
            redCapProxy.saveReferral(redCapInstrument);
            return "Bundle OK";
        } else {
            return "Empty Payload";
        }
    }

    //TODO::RISHI to Add Code here!
    @PostMapping(value="/feedback", consumes= MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String processFeedback(RedCapFeedbackInstrument feedback) throws IOException, TemplateException {
        System.out.println("body = " + feedback);
        Map<String,Object> map = redCapProxy.getFeedBackData(feedback);
        fhirProxy.processFeedback(map);
        return "OK";
    }

    //This method parsers either XML or JSON content:
    private Bundle getBundle(String body) {
        FhirContext ctx = FhirContext.forDstu3();
        IParser parser;
        if (body != null && body.trim().length() >0 ) {
            if (body.startsWith("<")) {
                parser = ctx.newXmlParser();
            } else {
                parser = ctx.newJsonParser();
            }
            return (Bundle) parser.parseResource(body);
        }
        return null;
    }



    @PostMapping
    public String receiveNewReferral(@RequestBody(required=false) String body) {
        System.out.println("body = " + body);
        return "ok";
    }
    @PostMapping("$process-message")
    public String receiveReferralMessage(@RequestBody(required=false) String body) {
        System.out.println("Processing referral mressage");
        System.out.println("body = " + body);
        return ("Message OK!");
    }



}
