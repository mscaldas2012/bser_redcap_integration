package gov.cdc.fhir.bser.redcap.service;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import gov.cdc.fhir.bser.redcap.model.RequestReferalInstrument;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hl7.fhir.dstu3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class FHIRProxy {
    public static final String VITAL_SIGN_HEIGHT = "8302-2";
    public static final String VITAL_SIGN_WEIGHT = "29463-7";
    public static final String VITAL_SIGN_A1C    = "4548-4";
    public static final String VITAL_SIGN_BMI    = "39156-5";
    public static final String PRACTITIONER_ROLE_PROFILE = "http://hl7.org/fhir/us/bser/StructureDefinition/ReferralInitiatorPractitionerRole";

    private Log logger = LogFactory.getLog(FHIRProxy.class.getName());

    @Autowired
    private TemplateConfiguration config;

    public RequestReferalInstrument processReferral(Bundle results) {
        RequestReferalInstrument instrument = new RequestReferalInstrument();
        List<Bundle.BundleEntryComponent> entries = results.getEntry();
        //instrument.setRecordId(results.getId());
        instrument.setRecordId("87");
        for (Bundle.BundleEntryComponent bundleEntryComponent : entries) {
            Resource resource = bundleEntryComponent.getResource();
            if ((ResourceType.MessageHeader).name().equalsIgnoreCase(resource.getResourceType().name())) {
                MessageHeader mheader = (MessageHeader) resource;
                Practitioner sender = (Practitioner) mheader.getSender().getResource();
                instrument.setReferralPractitionerName(sender.getNameFirstRep().getNameAsSingleString());
                instrument.setReferralPractitionerPhone(sender.getTelecomFirstRep().getValue());
            } else if ((ResourceType.Patient).name().equalsIgnoreCase(resource.getResourceType().name())) {
                Patient patient = (Patient) resource;
                instrument.setPatientMRNumber(patient.getIdentifierFirstRep().getValue()); //improve to go throu list of indentifiers
                instrument.setPatientFirstName(patient.getNameFirstRep().getGivenAsSingleString());
                instrument.setPatientLastName(patient.getNameFirstRep().getFamily());
                instrument.setPatientPhone(patient.getTelecomFirstRep().getValue());
                instrument.setPatientDob(patient.getBirthDate().toString()) ;
            } else if ((ResourceType.PractitionerRole).name().equalsIgnoreCase(resource.getResourceType().name())) {
                PractitionerRole prole = (PractitionerRole) resource;
                //if (prole.getMeta() != null && prole.getMeta().getProfile() != null && prole.getMeta().getProfile().size() > 0 ) { //&& PRACTITIONER_ROLE_PROFILE.equalsIgnoreCase(prole.getMeta().getProfile().get(0).getValue())) {
                    Organization org = (Organization) prole.getOrganization().getResource();
                    instrument.setReferralOrganizationName(org.getName());
                    instrument.setReferralOrganizationType(org.getTypeFirstRep().getCodingFirstRep().getDisplay());
                //}
            } else if ((ResourceType.Bundle.name().equalsIgnoreCase(resource.getResourceType().name()))) { //Vital Signs Bundle:
                Bundle bundle = (Bundle) resource;
                for (Bundle.BundleEntryComponent vitalSigns : bundle.getEntry()) {
                    try {
                        Observation vitalSign = (Observation) vitalSigns.getResource();
                        String value = vitalSign.getValueQuantity().getValue() + "";
                        switch (vitalSign.getCode().getCodingFirstRep().getCode()) {
                            case VITAL_SIGN_WEIGHT:
                                instrument.setPatientWeight(value);
                                break;
                            case VITAL_SIGN_HEIGHT:
                                instrument.setPatientHeight(value);
                                break;
                            case VITAL_SIGN_A1C:
                                instrument.setPatientA1CObservation(value);
                                break;
                            case VITAL_SIGN_BMI:
                                instrument.setPatientBMI(value);
                                break;
                        }
                    } catch (ClassCastException e) {
                        //ignore exception - not interested in anything other than observations!
                    }
                }

            }
        }
        return instrument;
    }

    public String processFeedback(Map<String, Object> data) throws IOException, TemplateException {
        Template temp = config.getConfig().getTemplate("feedback.ftlh");
        OutputStream os = new OutputStream() {
            private StringBuilder string = new StringBuilder();
            @Override
            public void write(int b) {
                this.string.append((char) b );
            }
            //Netbeans IDE automatically overrides this toString()
            public String toString(){
                return this.string.toString();
            }
        };
        Writer out = new OutputStreamWriter(os);
        temp.process(data, out);
        logger.info("Feedback generated:\n" + os.toString() + "\n-------------------------\n");
        return os.toString();
    }

    public static int getAge(Date dateOfBirth) {

        Calendar today = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();

        int age = 0;

        birthDate.setTime(dateOfBirth);
        if (birthDate.after(today)) {
            //throw new IllegalArgumentException("Can't be born in the future");
            return age;
        }

        age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

        // If birth date is greater than todays date (after 2 days adjustment of leap year) then decrement age one year
        if ((birthDate.get(Calendar.DAY_OF_YEAR) - today.get(Calendar.DAY_OF_YEAR) > 3) ||
                (birthDate.get(Calendar.MONTH) > today.get(Calendar.MONTH))) {
            age--;

            // If birth date and todays date are of same month and birth day of month is greater than todays day of month then decrement age
        } else if ((birthDate.get(Calendar.MONTH) == today.get(Calendar.MONTH)) &&
                (birthDate.get(Calendar.DAY_OF_MONTH) > today.get(Calendar.DAY_OF_MONTH))) {
            age--;
        }

        return age;
    }
}
