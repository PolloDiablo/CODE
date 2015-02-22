package koders.country.cross.code.dataapi;

import java.util.List;
import java.util.Map;

import koders.country.cross.code.dataapi.datatypes.InfoLink;
import koders.country.cross.code.dataapi.datatypes.Interest;
import koders.country.cross.code.dataapi.datatypes.Occupation;

/**
 * Created by Tony on 20/02/2015.
 */
public interface DataProvider {

    /**
     * Does an OR operation on the interested in activities.
     *
     * @param interestedIn which interests we are interested in.
     * @return a list of all the valid occupation codes and their proper names.
     */
    List<Occupation> getAllOccupations(final List<Interest> interestedIn);

    /**
     * Lookup by the occupation code.
     * @param displayName of the occupation.
     * @return the Occupation.
     */
    Occupation getOccupationByDisplayName(final String displayName);

    /**
     * @param occupation the occupation code.
     * @param province the location
     * @param city the location proper name/region. (eg. bc). //TODO: narrow down exactly
     *                   how/what we want the location to be.
     * @return the list of info links.
     */
    List<InfoLink> getInfoLinks(final Occupation occupation, final String province, final String city);


}
