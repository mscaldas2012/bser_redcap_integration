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
public class RequestReferalInstrument {
    @SerializedName("record_id")
    private String recordId;
    @SerializedName("referral_organization_name")
    private String referralOrganizationName;
    @SerializedName("referral_organization_type")
    private String referralOrganizationType;
    @SerializedName("referral_practitioner_name")
    private String referralPractitionerName;
    @SerializedName("referral_practitioner_phone")
    private String referralPractitionerPhone;
    @SerializedName("patient_mr_number")
    private String patientMRNumber;
    @SerializedName("patient_first_name")
    private String patientFirstName;
    @SerializedName("patient_last_name")
    private String patientLastName;
    @SerializedName("patient_dob")
    private String patientDob;
    @SerializedName("patient_phone")
    private String patientPhone;
    @SerializedName("patient_height")
    private String patientHeight;
    @SerializedName("patient_weight")
    private String patientWeight;
    @SerializedName("patient_bmi")
    private String patientBMI;
    @SerializedName("patient_a1cobservation")
    private String patientA1CObservation;



}
