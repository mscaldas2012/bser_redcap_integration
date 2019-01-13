package gov.cdc.fhir.bser.redcap.controller;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/referral")
public class Referrals {

    @PostMapping
    public String receiveNewReferral(@RequestParam(required= false) String queryparam, @RequestBody String body) {
        System.out.println("param: " + queryparam);
        System.out.println("body = " + body);
        return "ok";
    }
}
