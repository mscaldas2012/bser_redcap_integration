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
}
