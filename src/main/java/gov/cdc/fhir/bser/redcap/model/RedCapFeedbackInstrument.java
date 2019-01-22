package gov.cdc.fhir.bser.redcap.model;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RedCapFeedbackInstrument {
    private String redcap_url;
    private String project_url;
    private String project_id;
    private String username;
    private String record;
    private String redcap_event_name;
    private String instrument;
    private String create_feedback_complete;

}
