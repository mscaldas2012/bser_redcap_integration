package gov.cdc.fhir.bser.redcap.controller;


import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import freemarker.template.TemplateException;
import gov.cdc.fhir.bser.redcap.model.RedCapFeedbackInstrument;
import gov.cdc.fhir.bser.redcap.model.RequestReferalInstrument;
import gov.cdc.fhir.bser.redcap.service.AOCProxy;
import gov.cdc.fhir.bser.redcap.service.FHIRProxy;
import gov.cdc.fhir.bser.redcap.service.RedCapProxy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hl7.fhir.dstu3.model.Bundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping(value="/referral")
public class Referrals {
    Log logger = LogFactory.getLog("ReferralsController");


    @Autowired
    private FHIRProxy fhirProxy;
    @Autowired
    private RedCapProxy redCapProxy;
    @Autowired
    private AOCProxy aocProxy;

    @PutMapping("Bundle/{bundleId}")
    public ResponseEntity receiveReferral(@PathVariable String bundleId, @RequestBody(required=false) String body) {
        logger.info("AUDIT: received Referral Bundle - " + bundleId);
        Bundle b = getBundle(body);
        if (b!=null) {
            RequestReferalInstrument redCapInstrument = fhirProxy.processReferral(b);
            redCapProxy.saveReferral(redCapInstrument);
            return ResponseEntity.ok("Bundle OK");
        } else {
            return ResponseEntity.status(204).build();
        }
    }

    //TODO::RISHI to Add Code here!
    @PostMapping(value="/feedback", consumes= MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity processFeedback(RedCapFeedbackInstrument feedback) throws IOException, TemplateException {
        logger.info("AUDIT: processing feedback - " + feedback.getRedcap_event_name() + "[" + feedback.getRecord() + "]");
        Map<String,Object> map = redCapProxy.getFeedBackData(feedback);
        if (map != null) {
            String result =  fhirProxy.processFeedback(map);
            //POST Feedback back to AOC:
            aocProxy.sendFeedback(result);
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.ok("Feedback still not Final!");
        }
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


}
