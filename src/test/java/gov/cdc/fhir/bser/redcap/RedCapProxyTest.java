package gov.cdc.fhir.bser.redcap;

import gov.cdc.fhir.bser.redcap.model.RedCapFeedbackInstrument;
import gov.cdc.fhir.bser.redcap.model.RequestReferalInstrument;
import gov.cdc.fhir.bser.redcap.service.RedCapProxy;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        ref.setPatientFirstName("John");
        ref.setPatientLastName("Doe");
        ref.setPatientDob("10/01/1995");
        ref.setPatientMRNumber("123456");
        ref.setPatientWeight("167");
        ref.setPatientHeight("5.4");
        proxy.saveReferral(ref);
    }

    @Test
    public void getFeedbackData() {
        RedCapProxy proxy = new RedCapProxy("https://redcap-azure-stage.ymca.net/api/", "1B0C7E1F7B2F4E2F7245E500D98D04E3");
        RedCapFeedbackInstrument feedbackInstrument = new RedCapFeedbackInstrument();
        feedbackInstrument.setCreate_feedback_complete("2");
        feedbackInstrument.setInstrument("createe_feedback");
        feedbackInstrument.setProject_id("46");
        feedbackInstrument.setProject_url("https://redcap-azure-stage.ymca.net/redcap_v8.9.3/index.php?pid=46");
        feedbackInstrument.setRecord("44");
        feedbackInstrument.setRedcap_event_name("create_feedback_arm_1");
        feedbackInstrument.setRedcap_url("https://redcap-azure-stage.ymca.net");
        feedbackInstrument.setUsername("rrt8@cdc.gov");
        Map<String, Object> result = proxy.getFeedBackData(feedbackInstrument);
        System.out.println("end");
    }


}