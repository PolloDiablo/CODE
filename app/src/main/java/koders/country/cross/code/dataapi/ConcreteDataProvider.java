package koders.country.cross.code.dataapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import koders.country.cross.code.dataapi.datatypes.InfoLink;
import koders.country.cross.code.dataapi.datatypes.Interest;
import koders.country.cross.code.dataapi.datatypes.Occupation;

/**
 * Created by Tony on 20/02/2015.
 */
public class ConcreteDataProvider implements DataProvider {

    /**
     * Singleton.
     */
    private static final ConcreteDataProvider THE_INSTANCE = new ConcreteDataProvider();

    /**
     * Singleton.
     */
    private ConcreteDataProvider() {
    }

    /**
     * @return the data provider singleton to reference.
     */
    public DataProvider getTheInstance() {
        return THE_INSTANCE;
    }

    @Override
    public List<Occupation> getAllOccupations(List<Interest> interestedIn) {
        List<Occupation> toReturn = new ArrayList<Occupation>();
        Random r = new Random(55);
        for (int i = 0; i < MAX_OCCUPATIONS; i++) {
            toReturn.add(new Occupation("" + i, "occupation" + i, (r.nextDouble() * 10) - 5));
        }
        return toReturn;
    }

    @Override
    public List<InfoLink> getInfoLinks(Occupation occupation, String location) {
        List<InfoLink> toReturn = new ArrayList<InfoLink>();
        Random r = new Random(location.hashCode());
        for (int i = 0; i < MAX_INFO_LINKS; i++) {
            String query = occupation.getCode() + r.nextInt();
            toReturn.add(new InfoLink("http://www.google.ca/#q=" + query, "Google " + query));
        }
        return toReturn;
    }


    /*
     * Fake data set parameters.
     */
    private static final int MAX_OCCUPATIONS = 10;
    private static final int MAX_INFO_LINKS = 3;

}
