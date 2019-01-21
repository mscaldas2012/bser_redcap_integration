package gov.cdc.fhir.bser.redcap;

import gov.cdc.fhir.bser.redcap.model.RequestReferalInstrument;
import gov.cdc.fhir.bser.redcap.service.RedCapProxy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Created - 2019-01-04
 * @Author Marcelo Caldas mcq1@cdc.gov
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedCapProxyTest {

//    @Autowired
//    private RedCapProxy proxy ;


    @Test
    public void doPost() {
        RedCapProxy proxy = new RedCapProxy("https://redcap-azure-stage.ymca.net/api/", "1B0C7E1F7B2F4E2F7245E500D98D04E3");

        RequestReferalInstrument ref = new RequestReferalInstrument();
        ref.setRecordId("1");
        ref.setReferralOrganizationName("CDC");
        ref.setReferralOrganizationType("Public health");
        ref.setReferralPractitionerName("Marcelo Caldas");
        ref.setPatientName("John Doe");
        ref.setPatientAge("27");
        ref.setPatientMRNumber("123456");
        ref.setPatientWeight("167");
        ref.setPatientHeight("5.4");
        proxy.saveReferral(ref);
    }
}