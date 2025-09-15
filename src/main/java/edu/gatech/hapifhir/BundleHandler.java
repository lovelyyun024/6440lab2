package edu.gatech.hapifhir;

import org.hl7.fhir.r4.model.*;

import java.util.ArrayList;
import java.util.List;

public class BundleHandler {

    public BundleHandler() { }

    public String navigateBundle(Bundle bundle, String link) {

        // START STUDENT CODE HERE
        if (bundle == null || link == null) return "-1";

        List<Bundle.BundleLinkComponent> links = bundle.getLink();
        if (links != null){
            for (Bundle.BundleLinkComponent linkComponent : links) {
                if (link.equals(linkComponent.getRelation())) {
                    //return the URL for the requested page of the bundle
                    return linkComponent.getUrl();
                }
            }
        }

        return "-1";

        // END STUDENT CODE HERE

//        return ""; // Returning empty string so starter code compiles.
    }

    public ArrayList<Patient> getListOfDeceasedPatients(Bundle bundle) {
        ArrayList<Patient> patientArrayList = new ArrayList<>();

        // START STUDENT CODE HERE
        if (bundle != null) return patientArrayList;


        List<Bundle.BundleEntryComponent> entries = bundle.getEntry();
        if (entries == null) return patientArrayList;

        for (Bundle.BundleEntryComponent entry : entries) {
            // Get the resource from the entry
            Resource resource = entry.getResource();

            // Check if the resource is a Patient
            if (resource instanceof Patient) {
                Patient patient = (Patient) resource;
                Boolean checkDeceased = false;

                // Check if patient is deceased by check hasDeceased value
                if (patient.hasDeceased()) {
                    // Check if it's a boolean and true, or if it's a dateTime
                    Type typeValue = patient.getDeceased();
                    if (typeValue instanceof BooleanType) {
                        BooleanType deceasedBoolean = (BooleanType) patient.getDeceased();
                        if (deceasedBoolean.getValue()) {
                            checkDeceased = true;
                        }
                    } else if (typeValue instanceof DateTimeType) {
                        // Any dateTime value means deceased
                        checkDeceased = true;
                    }
                }
                if (checkDeceased) {
                    patientArrayList.add(patient);
                }
            }
        }

        // END STUDENT CODE HERE

        return patientArrayList; //
    }
}
