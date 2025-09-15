package edu.gatech.hapifhir;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;

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

        return ""; // Returning empty string so starter code compiles.
    }

    public ArrayList<Patient> getListOfDeceasedPatients(Bundle bundle) {
        ArrayList<Patient> patientArrayList = new ArrayList<>();

        // START STUDENT CODE HERE

        // END STUDENT CODE HERE

        return patientArrayList; //
    }
}
