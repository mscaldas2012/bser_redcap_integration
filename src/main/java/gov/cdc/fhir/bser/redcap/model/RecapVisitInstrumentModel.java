package gov.cdc.fhir.bser.redcap.model;

import com.google.gson.annotations.SerializedName;

public class RecapVisitInstrumentModel {
    @SerializedName("record_id")
    private String recordId;
    @SerializedName("visit_patient_weight")
    private String weight;
    @SerializedName("visit_patient_height")
    private String height;
    @SerializedName("visit_patient_bmi")
    private String bmi;
    @SerializedName("visit_patient_a1c_count")
    private String a1c;

}
