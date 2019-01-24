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

    private final static String REDCAP_EVENT_CREATE_FEEDBACK = "create_feedback_arm_1";

    // This variable is set to 2 for Completed Feedback form and the event name is create_feedback_arm_1
    public boolean isFeedBackTrigger() {
        return REDCAP_EVENT_CREATE_FEEDBACK.equalsIgnoreCase(redcap_event_name) && "2".equals(create_feedback_complete);
//        if(create_feedback_complete!=null && !create_feedback_complete.isEmpty() && redcap_event_name!=null && !redcap_event_name.isEmpty() ) {
//            return redcap_event_name.equalsIgnoreCase(REDCAP_EVENT_CREATE_FEEDBACK) && Integer.parseInt(create_feedback_complete) == 2;
//        }
//        return false;
    }
}
