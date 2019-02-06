package gov.cdc.fhir.bser.redcap.service;

import com.google.gson.Gson;
import org.json.JSONObject;

import gov.cdc.fhir.bser.redcap.model.RedCapFeedbackInstrument;
import gov.cdc.fhir.bser.redcap.model.RequestReferalInstrument;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Created - 2019-01-04
 * @Author Marcelo Caldas mcq1@cdc.gov
 */
@Component
public class RedCapProxy {
    Log logger = LogFactory.getLog(RedCapProxy.class.getName());

    private final HttpPost post;


    @Value("${redcap.api.url}")
    private String _redcapURL;
    @Value("${redcap.api.token}")
    private String _token;


    public RedCapProxy(@Value("${redcap.api.url}") String url, @Value("${redcap.api.token}") String token) {
        _redcapURL = url;
        _token = token;

        post = new HttpPost(_redcapURL);
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");

    }

    private List<NameValuePair> prepareRedcapParams() {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("token", _token));
        params.add(new BasicNameValuePair("content", "record"));
        params.add(new BasicNameValuePair("format", "json"));
        params.add(new BasicNameValuePair("returnFormat", "json"));
        params.add(new BasicNameValuePair("type", "flat"));
        params.add(new BasicNameValuePair("overwriteBehavior", "normal"));
        params.add(new BasicNameValuePair("forceAutoNumber", "false"));

        return params;
    }

    public void saveReferral(RequestReferalInstrument newRecord) {
        Gson gson = new Gson();

        List<NameValuePair> params = prepareRedcapParams();
        //params.add(new BasicNameValuePair("data", "[{\"record_id\":\"6\",\"firstname\":\"Spring\",\"lastname\":\"Boot\",\"age\":\"2\"}]"));
        params.add(new BasicNameValuePair("data", "[" + gson.toJson(newRecord) + "]"));
        params.add(new BasicNameValuePair("returnContent", "count"));
        callRedcap(params);
    }




    public Map<String , Object> getFeedBackData(RedCapFeedbackInstrument feedback) {
        //two subsequent calls will have to be made
        //1. Call redcap to glean selected data (Patient info , Patient Id's)from the referral request form
        //2. Call redcap to glean Observation Data from the visit form (bmi height weight etc)
        //3. get the feedback note
        //When all data is received then start creating the feedback Bundle with the values recieved.
        if(!feedback.isFeedBackTrigger()) {
            //as the create feedback form was not completed and clicked save.
            //this is a catch all for all other form saves  in referral and visit form clicks should not process
            return null;
        }
        List<NameValuePair> params = prepareRedcapParams();
        params.add(new BasicNameValuePair("records", feedback.getRecord()));
        String fields = "feedback_note,patient_a1cobservation,patient_dob,patient_height,patient_mr_number,patient_first_name,patient_last_name,patient_phone,patient_weight,record_id,referral_organization_name,referral_organization_type,referral_practitioner_name,referral_practitioner_phone,visit_a1c_count,visit_patient_bmi,visit_patient_height,visit_patient_weight";
        params.add(new BasicNameValuePair("fields", fields));
        String forms =  "dpp_referral_request,dpp_visit,dpp_feedback";
        params.add(new BasicNameValuePair("forms", forms));
        params.add(new BasicNameValuePair("rawOrLabel", "raw"));
        params.add(new BasicNameValuePair("rawOrLabelHeaders", "raw"));
        params.add(new BasicNameValuePair("exportCheckboxLabel", "false"));
        params.add(new BasicNameValuePair("exportSurveyFields", "false"));
        params.add(new BasicNameValuePair("exportDataAccessGroups", "false"));
        params.add(new BasicNameValuePair("returnFormat", "json"));
        params.add(new BasicNameValuePair("events", "referral_received_arm_1,visit_8_arm_1,create_feedback_arm_1,"));

        String result = callRedcap(params);


        ArrayList array = new Gson().fromJson(result, ArrayList.class);
        return getMapfromArray(array, "visit_8_arm_1");
    }

    private Map getMapfromArray(ArrayList list, String eventName) {
        Map<String, Object> returnMap = new HashMap();
        Gson g = new Gson();
        JSONObject use = new JSONObject();
        for (Object object : list) {
            String str  = g.toJson(object, HashMap.class);
            JSONObject o = new JSONObject(str);
            String redcapevent = o.getString("redcap_event_name");

            if(redcapevent.equalsIgnoreCase("referral_received_arm_1")) {
                use=o;
            }else if(redcapevent.equalsIgnoreCase(eventName)) {
                use.put("visit_patient_bmi", o.get("visit_patient_bmi"));
                use.put("visit_patient_weight", o.get("visit_patient_weight"));
                use.put("visit_patient_height", o.get("visit_patient_height"));
                use.put("visit_a1c_count", o.get("visit_a1c_count"));

            }else if(redcapevent.equalsIgnoreCase("create_feedback_arm_1")) {
                use.put("feedback_note", o.get("feedback_note"));
            }

        }
        return use.toMap();
    }

    private String callRedcap(List<NameValuePair> params) {
        StringBuilder result = new StringBuilder();
        HttpClient client = HttpClientBuilder.create().build();

        try {
            post.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse resp = client.execute(post);

            if (resp != null) {
                logger.info("respCode: " + resp.getStatusLine().getStatusCode());
                BufferedReader reader = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

        logger.info("result:\n" + result.toString() + "\n---------------------------\n");
        return result.toString();
    }
}
