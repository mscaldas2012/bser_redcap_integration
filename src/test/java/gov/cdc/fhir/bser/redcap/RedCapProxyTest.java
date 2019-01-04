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
        RedCapProxy proxy = new RedCapProxy("https://redcap-azure-stage.ymca.net/api/", "5C0B853CFE636538BD5C839C2C6DBE3E");

        RequestReferalInstrument ref = new RequestReferalInstrument("7", "Gandalf", "The Gray", "99");
        proxy.doPost(ref);
    }
}