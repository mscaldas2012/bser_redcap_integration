<?xml version="1.0" encoding="UTF-8"?>
<Bundle xmlns="http://hl7.org/fhir">
    <id value="DiabetesFeedbackReferralResponseBundle"/>
    <meta>
    <versionId value="2"/>
    <lastUpdated value="2019-01-13T16:53:12.000+00:00"/>
    <profile value="http://hl7.org/fhir/us/bser/StructureDefinition/DiabetesPreventionReferralfeedbackMessageBundle"/>
    </meta>
    <type value="message"/>
    <entry>
        <fullUrl value="urn:uuid:337e22ab-0ced-480a-9be7-450094e7267c"/>
        <resource>
            <MessageHeader xmlns="http://hl7.org/fhir">
                <event>
                    <system value="http://loinc.org"/>
                    <code value="TBD4"/>
                    <display value="Referral Feedback"/>
                </event>
                <receiver>

                    <extension url="https://eip-fhir.experimental.aimsplatform.com/hapi-fhir/baseDstu3/StructureDefinition/Practioner-Role">
                        <valueReference>
                            <reference value="urn:uuid:538791da-3555-460e-9e7f-76d25988a546"/>
                        </valueReference>
                    </extension>

                    <reference value="urn:uuid:598af951-66e4-4910-a964-7e41533b183c"/>
                </receiver>
                <sender>

                    <extension url="http://expample.org/practitioner-role">
                        <valueReference>
                            <reference value="urn:uuid:ffa2bf53-ee09-43a1-b11a-73ed0f1cd73b"/>
                        </valueReference>
                    </extension>

                    <reference value="urn:uuid:421269ad-abef-49bc-8cac-d3a5d4b00299"/>
                </sender>
                <timestamp value="2019-01-11T07:05:11Z"/>
                <source>
                <name value="RedCap"/>
                <endpoint value="https://YMCAsandbox.RedCap.com:9443/demoAPIServer"/>
                </source>
                <reason>
                    <coding>
                        <system value="https://www.hl7.org/fhir/valueset-message-reason-encounter"/>
                        <code value="interim feedback report"/>
                        <display value="interim feedback report"/>
                    </coding>
                </reason>
                <focus>
                    <reference value="urn:uuid:6b2fd643-a3b1-47af-bbce-027103ed3c1d"/>
                </focus>
            </MessageHeader>
        </resource>
    </entry>
    <entry>
        <fullUrl value="urn:uuid:6b2fd643-a3b1-47af-bbce-027103ed3c1d"/>
        <resource>
            <Composition xmlns="http://hl7.org/fhir">
                <identifier>
                    <system value="urn:oid:1.3.6.1.4.1.26580.190.12457709"/>
                    <value value="1234561"/>
                </identifier>
                <status value="final"/>
                <type>
                    <coding>
                        <system value="http://hl7.org/fhir/v3/NullFlavor"/>
                        <code value="UNC"/>
                        <display value="Referral Feedback"/>
                    </coding>
                </type>
                <subject>
                    <reference value="urn:uuid:cec258d7-a76a-4a1d-bc4c-c656b0a5d6f8"/>
                </subject>
                <date value="2019-01-11"/>
                <author>
                    <!--This should be the practitioner who recived ther request-->
                    <reference value="urn:uuid:598af951-66e4-4910-a964-7e41533b183c"/>

                </author>
                <title value="Interim Referral Feedback Report"/>
                <section>
                    <title value="Interim Feedback Supporting Information"/>
                    <code>
                        <coding>
                            <system value="http://hl7.org/fhir/v3/NullFlavor"/>
                            <code value="UNC"/>
                            <display value="InterimFeedbackSupportingInformation"/>
                        </coding>
                    </code>
                    <text>
                        <status value="additional"/>
                        <div xmlns="http://www.w3.org/1999/xhtml">
                            ${feedback_note}
                        </div>
                    </text>
                    <entry>
                        <!-- FeedbackReferralStatusObservation Instance Example-->
                        <reference value="urn:uuid:91a42efb-ddaf-4cbe-8fa4-7ead01d05fb6"/>
                    </entry>
                </section>
                <section>
                    <title value="Referral Feedback Supporting Bundle"/>
                    <code>
                        <coding>
                            <system value="http://hl7.org/fhir/v3/NullFlavor"/>
                            <code value="UNC"/>
                            <display value="ReferralFeedbackSupportingBundle"/>
                        </coding>
                    </code>
                    <entry>
                        <reference value="urn:uuid:34cfcf90-3c83-4886-a68c-acc73ac64f07"/>
                    </entry>
                </section>
            </Composition>
        </resource>
    </entry>
    <entry>
        <fullUrl value="urn:uuid:91a42efb-ddaf-4cbe-8fa4-7ead01d05fb6"/>
        <resource>
            <Observation xmlns="http://hl7.org/fhir">
                <status value="final"/>
                <code>
                    <coding>
                        <system value="http://hl7.org/fhir/v3/NullFlavor"/>
                        <code value="UNC"/>
                        <display value="Feedback Referral Status Observation"/>
                    </coding>
                </code>
                <component>
                    <code>
                        <coding>
                            <system value="http://hl7.org/fhir/v3/NullFlavor"/>
                            <code value="UNC"/>
                            <display value="Referral Activity Status"/>
                        </coding>
                    </code>
                    <valueCodeableConcept>
                        <coding>
                            <system value="http://hl7.org/fhir/v3/NullFlavor"/>
                            <code value="UNC"/>
                            <display value="Intake Completed"/>
                        </coding>
                    </valueCodeableConcept>
                </component>
                <component>
                    <code>
                        <coding>
                            <system value="http://hl7.org/fhir/v3/NullFlavor"/>
                            <code value="UNC"/>
                            <display value="Referral Activity Status Reason"/>
                        </coding>
                    </code>
                    <valueCodeableConcept>
                        <coding>
                            <system value="http://hl7.org/fhir/v3/NullFlavor"/>
                            <code value="UNC"/>
                            <display value="Patient met all intake requirements"/>
                        </coding>
                    </valueCodeableConcept>
                </component>
                <component>
                    <code>
                        <coding>
                            <system value="http://hl7.org/fhir/v3/NullFlavor"/>
                            <code value="UNC"/>
                            <display value="Referral Type"/>
                        </coding>
                    </code>
                    <valueCodeableConcept>
                        <coding>
                            <system value="http://hl7.org/fhir/v3/NullFlavor"/>
                            <code value="UNC"/>
                            <display value="Diabetes"/>
                        </coding>
                    </valueCodeableConcept>
                </component>
                <component>
                    <code>
                        <coding>
                            <system value="http://hl7.org/fhir/v3/NullFlavor"/>
                            <code value="UNC"/>
                            <display value="Referral Status"/>
                        </coding>
                    </code>
                    <valueCodeableConcept>
                        <coding>
                            <system value="http://hl7.org/fhir/v3/NullFlavor"/>
                            <code value="UNC"/>
                            <display value="Active"/>
                        </coding>
                    </valueCodeableConcept>
                </component>
                <component>
                    <!-- Better represented by a Procedure resource instance that is referenced elsewhere -->
                    <!--
                         <coding>
                             <system value="http://hl7.org/fhir/observation-category"/>
                             <code value="procedure"/>
                             <display value="Procedure"/>
                       </coding>
                       -->
                    <code>
                        <coding>
                            <system value="http://snomed.info/sct"/>
                            <code value="298057009"/>
                            <display value="Number of Appointments Attended"/>
                        </coding>
                    </code>
                    <!-- Added valueQuantity showing 3 appointments -->
                    <valueQuantity>
                        <value value="3"/>
                        <unit value="1"/>
                    </valueQuantity>
                </component>
            </Observation>
        </resource>
    </entry>
    <entry>
        <fullUrl value="urn:uuid:cec258d7-a76a-4a1d-bc4c-c656b0a5d6f8"/>
        <resource>
            <Patient xmlns="http://hl7.org/fhir">
                <id value="cec258d7-a76a-4a1d-bc4c-c656b0a5d6f8"/>
                <meta>
                <profile value="http://hl7.org/fhir/us/bser/StructureDefinition/PatientRole"/>
                </meta>
                <identifier>
                    <type>
                        <coding>
                            <system value="http://hl7.org/fhir/v2/0203"/>
                            <code value="MR"/>
                            <display value="Medical Record Number"/>
                        </coding>
                    </type>
                    <system value="urn:oid:2.16.840.1.113883.4.3.17"/>
                    <value value="${patient_mr_number}"/>
                </identifier>
                <name>
                    <use value="official"/>
                    <family value="${patient_first_name}"/>
                    <given value="${patient_last_name}"/>
                </name>
                <telecom>
                    <system value="phone"/>
                    <value value="${patient_phone}"/>
                </telecom>
                <gender value="male"/>
                <birthDate value="1950-03-22"/>
                <address>
                    <line value="609 Lizeth Streets"/>
                    <city value="Chicago"/>
                    <state value="IL"/>
                    <postalCode value="60609"/>
                </address>
                <communication>
                    <language>
                        <coding>
                            <system value="urn:ietf:bcp:47"/>
                            <code value="en-US"/>
                            <display value="English (United States)"/>
                        </coding>
                    </language>
                    <preferred value="true"/>
                </communication>
            </Patient>
        </resource>
    </entry>
    <entry>
        <fullUrl value="urn:uuid:34cfcf90-3c83-4886-a68c-acc73ac64f07"/>
        <!-- DiabetesFeedbackSupportBundleInstanceExample -->
        <resource>
            <Bundle xmlns="http://hl7.org/fhir">
                <type value="collection"/>
                <entry>
                    <fullUrl value="urn:uuid:70a5b9d0-3153-45cb-adce-672edeace7c2"/>
                    <resource>
                        <Observation xmlns="http://hl7.org/fhir">
                            <id value="70a5b9d0-3153-45cb-adce-672edeace7c2"/>
                            <status value="final"/>
                            <category>
                                <coding>
                                    <system value="http://hl7.org/fhir/observation-category"/>
                                    <code value="vital-signs"/>
                                    <display value="Vital Signs"/>
                                </coding>
                            </category>
                            <code>
                                <coding>
                                    <system value="http://loinc.org"/>
                                    <code value="39156-5"/>
                                    <display value="Body mass index (BMI) [Ratio]"/>
                                </coding>
                            </code>
                            <subject>
                                <reference value="urn:uuid:cec258d7-a76a-4a1d-bc4c-c656b0a5d6f8"/>
                            </subject>
                            <effectiveDateTime value="2019-01-11"/>
                            <valueQuantity>
                                <value value="${visit_patient_bmi}"/>
                                <unit value="kg/m2"/>
                                <system value="http://unitsofmeasure.org"/>
                                <code value="kg/m2"/>
                            </valueQuantity>
                        </Observation>
                    </resource>
                </entry>
                <entry>
                    <fullUrl value="urn:uuid:30bcd878-ce0a-4558-95a4-365287155895"/>
                    <resource>
                        <Observation xmlns="http://hl7.org/fhir">
                            <id value="30bcd878-ce0a-4558-95a4-365287155895"/>
                            <status value="final"/>
                            <category>
                                <coding>
                                    <system value="http://hl7.org/fhir/observation-category"/>
                                    <code value="vital-signs"/>
                                    <display value="Vital Signs"/>
                                </coding>
                            </category>
                            <code>
                                <coding>
                                    <system value="http://loinc.org"/>
                                    <code value="29463-7"/>
                                    <display value="Body Weight"/>
                                </coding>
                            </code>
                            <subject>
                                <reference value="urn:uuid:cec258d7-a76a-4a1d-bc4c-c656b0a5d6f8"/>
                            </subject>
                            <effectiveDateTime value="2019-01-11"/>
                            <valueQuantity>
                                <value value="${visit_patient_weight}"/>
                                <unit value="kg"/>
                                <system value="http://unitsofmeasure.org"/>
                                <code value="kg"/>
                            </valueQuantity>
                        </Observation>
                    </resource>
                </entry>
                <entry>
                    <fullUrl value="urn:uuid:9c332515-b833-4529-975b-3007578dc4cd"/>
                    <resource>
                        <Observation xmlns="http://hl7.org/fhir">
                            <id value="9c332515-b833-4529-975b-3007578dc4cd"/>
                            <status value="final"/>
                            <category>
                                <coding>
                                    <system value="http://hl7.org/fhir/observation-category"/>
                                    <code value="vital-signs"/>
                                    <display value="Vital Signs"/>
                                </coding>
                            </category>
                            <code>
                                <coding>
                                    <system value="http://loinc.org"/>
                                    <code value="4548-4"/>
                                    <display value="Hemoglobin A1c/​Hemoglobin.total in Blood"/>
                                </coding>
                            </code>
                            <subject>
                                <reference value="urn:uuid:cec258d7-a76a-4a1d-bc4c-c656b0a5d6f8"/>
                            </subject>
                            <effectiveDateTime value="2019-01-11"/>
                            <valueQuantity>
                                <value value="${visit_a1c_count}"/>
                                <unit value="mg/dL"/>
                                <system value="http://unitsofmeasure.org"/>
                                <code value="mg/dL"/>
                            </valueQuantity>
                        </Observation>
                    </resource>
                </entry>
                <entry>
                    <fullUrl value="urn:uuid:ac1199e5-d0dc-4294-ba66-37628bc45654"/>
                    <resource>
                        <Observation xmlns="http://hl7.org/fhir">
                            <id value="ac1199e5-d0dc-4294-ba66-37628bc45654"/>
                            <status value="final"/>
                            <category>
                                <coding>
                                    <system value="http://hl7.org/fhir/observation-category"/>
                                    <code value="vital-signs"/>
                                    <display value="Vital Signs"/>
                                </coding>
                            </category>
                            <code>
                                <coding>
                                    <system value="http://loinc.org"/>
                                    <code value="8302-2"/>
                                    <display value="Body height"/>
                                </coding>
                            </code>
                            <subject>
                                <reference value="urn:uuid:cec258d7-a76a-4a1d-bc4c-c656b0a5d6f8"/>
                            </subject>
                            <effectiveDateTime value="2018-12-11"/>
                            <valueQuantity>
                                <value value="${visit_patient_height}"/>
                                <unit value="cm"/>
                                <system value="http://unitsofmeasure.org"/>
                                <code value="cm"/>
                            </valueQuantity>
                        </Observation>
                    </resource>
                </entry>
            </Bundle>
        </resource>
    </entry>
</Bundle>