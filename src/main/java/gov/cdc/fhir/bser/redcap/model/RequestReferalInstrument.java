package gov.cdc.fhir.bser.redcap.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Created - 2019-01-04
 * @Author Marcelo Caldas mcq1@cdc.gov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestReferalInstrument {
    @SerializedName("record_id")
    private String recordId;
    private String firstname;
    private String lastname;
    private String age;


}
