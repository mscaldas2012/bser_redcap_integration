package gov.cdc.fhir.bser.redcap.controller;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/referral")
public class Referrals {

    @PostMapping
    public String receiveNewReferral(@RequestBody String body) {
        System.out.println("body = " + body);
        return "ok";
    }

    @PutMapping("Encounter/{encounterID}")
    public String receiveReferral(@PathVariable String encounterID, @RequestBody String body) {
        System.out.println("encounterID = " + encounterID);
        System.out.println("body = " + body);
        return "encounter OK";
    }

    @PostMapping("$process-message")
    public String receiveReferralMessage(@RequestBody String body) {
        System.out.println("Processing referral mressage");
        System.out.println("body = " + body);
        return ("Message OK!");
    }
}
