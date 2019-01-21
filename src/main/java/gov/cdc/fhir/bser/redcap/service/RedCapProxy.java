package gov.cdc.fhir.bser.redcap.service;

import com.google.gson.Gson;
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
import java.util.List;

/**
 * @Created - 2019-01-04
 * @Author Marcelo Caldas mcq1@cdc.gov
 */
@Component
public class RedCapProxy {

    Log logger = LogFactory.getLog(RedCapProxy.class.getName());

    private final HttpPost post;
    private final HttpClient client;
    private final StringBuffer result;
    private int respCode;
    private BufferedReader reader;
    private String line;


    @Value("${redcap.api.url}")
    private String _redcapURL;
    @Value("${redcap.api.token}")
    private String _token;


    public RedCapProxy(@Value("${redcap.api.url}") String url, @Value("${redcap.api.token}") String token) {
        _redcapURL = url;
        _token = token;

        post = new HttpPost(_redcapURL);
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");

        result = new StringBuffer();
        client = HttpClientBuilder.create().build();
        respCode = -1;
        reader = null;
        line = null;
    }

    public void saveReferral(RequestReferalInstrument newRecord) {
        HttpResponse resp = null;
        Gson gson = new Gson();

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token", _token));
        params.add(new BasicNameValuePair("content", "record"));
        params.add(new BasicNameValuePair("format", "json"));
        params.add(new BasicNameValuePair("returnFormat", "json"));
        params.add(new BasicNameValuePair("type", "flat"));
        params.add(new BasicNameValuePair("overwriteBehavior", "normal"));
        params.add(new BasicNameValuePair("forceAutoNumber", "false"));
        params.add(new BasicNameValuePair("returnContent", "count"));
        //params.add(new BasicNameValuePair("data", "[{\"record_id\":\"6\",\"firstname\":\"Spring\",\"lastname\":\"Boot\",\"age\":\"2\"}]"));
        params.add(new BasicNameValuePair("data", "[" + gson.toJson(newRecord) + "]"));

        try {
            post.setEntity(new UrlEncodedFormEntity(params));
            resp = client.execute(post);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        if (resp != null) {
            respCode = resp.getStatusLine().getStatusCode();

            try {
                reader = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }

        if (reader != null) {
            try {
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }

        logger.info("respCode: " + respCode);
        logger.info("result: " + result.toString());
    }
}
