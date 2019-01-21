package gov.cdc.fhir.bser.redcap.controller;


import ca.uhn.fhir.context.FhirContext;
import gov.cdc.fhir.bser.redcap.model.RequestReferalInstrument;
import gov.cdc.fhir.bser.redcap.service.FHIRProxy;
import gov.cdc.fhir.bser.redcap.service.RedCapProxy;
import org.hl7.fhir.dstu3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@RestController
@RequestMapping(value="/referral")
public class Referrals {

    @Autowired
    private FHIRProxy fhirProxy;
    @Autowired
    private RedCapProxy redCapProxy;

    @PutMapping("Bundle/{bundleId}")
    public String receiveReferral(@PathVariable String bundleId, @RequestBody(required=false) String body) {
        FhirContext ctx = FhirContext.forDstu3();
        Bundle b = (Bundle) ctx.newXmlParser().parseResource(body);
        RequestReferalInstrument redCapInstrument = fhirProxy.processReferral(b);
        redCapProxy.saveReferral(redCapInstrument);
        return "Bundle OK";
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
