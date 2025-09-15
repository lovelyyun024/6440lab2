package edu.gatech.hapifhir;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.*;

import java.util.ArrayList;
import java.util.LinkedList;


public class ResourceHandler {

    public ResourceHandler() {
        // Do not add anything to this constructor method.
    }

    public String getId(IBaseResource resource){
        String id = "";
        // Get the "id" value of any resource without any additional data. (No base server urls, resource types, etc.)
        // START STUDENT CODE HERE
        if (resource == null || resource.getIdElement() == null) return null;
        id = resource.getIdElement().getIdPart();
//        IdType curId = resource.getIdElement().getIdPart();
        // END STUDENT CODE HERE

        return id; // Replace this. Returns empty string just so it will compile.
    }

    public Observation addObservationCode(Observation observation, String system, String code, String display){
        // Create a CodeableConcept and add it to an existing Observation resource.
        // START STUDENT CODE HERE
        // check if observation is null. If it is, create new observation
        if (observation == null) observation = new Observation();

        Coding coding = new Coding();
        coding.setSystem(system);
        coding.setCode(code);
        coding.setDisplay(display);

        CodeableConcept codeableConcept = new CodeableConcept();
        codeableConcept.addCoding(coding);

        observation.setCode(codeableConcept);
//        return observation;

        // END STUDENT CODE HERE
        return observation;
    }

    public Patient updateOfficialGivenName(Patient patient, String givenName){
        // Being given a Patient resource and a given name string, set (not add) the name to the HumanName object in the
        // Patient's list of names with a use of "official". No other values should be modified.
        // Note: There is only one HumanName with use set to official, you do not need to handle multiple official
        // names. The name provided should be the only given name (a single item List).

        // START STUDENT CODE HERE
        for (HumanName name : patient.getName()) {
            if (name != null && name.getUse() == HumanName.NameUse.OFFICIAL) {
                name.getGiven().clear();
                name.addGiven(givenName);
                break;
            }
        }

        // END STUDENT CODE HERE
        return patient;
    }

    public Patient createUSCorePatient(String id, Identifier identifier, HumanName name, Coding ethnicityOmbCoding, Coding ethnicityDetailedCoding, String ethnicityText){
        Patient usCorePatient = new Patient(); // Use this Patient object.

        // Create a US Core Patient (a Patient resource using the US Core profile) with name, identifier, ethnicity, and
        // any required profile meta information. For name you are provided a HumanName object and for Identifier you
        // are provided with an Identifier object. These should be simple to handle. Ethnicity you are provided with
        // individual portions of the extension, the OMB category coding, Detailed ethnicity coding, and Text
        // representation as a String. You are responsible for setting these coding elements in the correct location
        // within the extension along with setting the metadata for each portion (the URLs).
        //
        // http://hl7.org/fhir/us/core/StructureDefinition-us-core-patient.html
        // http://hl7.org/fhir/us/core/StructureDefinition-us-core-ethnicity.html
        //
        // START STUDENT CODE HERE

        //Set id, name and identifier
        usCorePatient.setId(id);
        usCorePatient.addIdentifier(identifier);
        usCorePatient.addName(name);

        //set the US core patient profile URL
        usCorePatient.getMeta().addProfile("http://hl7.org/fhir/us/core/StructureDefinition/us-core-patient");

        //Set Ethnicity Extension(OMB category, detailed ethnicity, text)
        Extension ethnicityExtension = new Extension("http://hl7.org/fhir/us/core/StructureDefinition/us-core-ethnicity");

        CodeableConcept ombCategory = new CodeableConcept();
        ombCategory.addCoding(ethnicityOmbCoding);
        ethnicityExtension.addExtension("ombCategory", ethnicityOmbCoding);

        CodeableConcept detailedEthnicity = new CodeableConcept();
        detailedEthnicity.addCoding(ethnicityDetailedCoding);
        ethnicityExtension.addExtension("detailed", detailedEthnicity);

        ethnicityExtension.addExtension("text", new StringType(ethnicityText));
        usCorePatient.addExtension(ethnicityExtension);

//        return usCorePatient;

        // END STUDENT CODE HERE
        return usCorePatient;
    }
}
