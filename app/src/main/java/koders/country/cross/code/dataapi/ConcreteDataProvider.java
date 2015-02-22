package koders.country.cross.code.dataapi;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import koders.country.cross.code.dataapi.datatypes.InfoLink;
import koders.country.cross.code.dataapi.datatypes.Interest;
import koders.country.cross.code.dataapi.datatypes.Occupation;
import koders.country.cross.code.dataapi.datatypes.Outlook;

/**
 * Created by Tony on 20/02/2015.
 */
public class ConcreteDataProvider implements DataProvider {

    /**
     * Singleton.
     */
    private static final ConcreteDataProvider THE_INSTANCE = new ConcreteDataProvider();
    private static final String TAG = "ConcreteDataProvider";

    /**
     * All of the occupations.
     */
    private final List<Occupation> masterList = new ArrayList<Occupation>();

    /**
     * The master mapping of all the interests, to their related occupations.
     */
    private final Map<Interest, Set<Occupation>> interestLookup = new EnumMap<Interest, Set<Occupation>>(Interest.class);

    /**
     * Singleton.
     */
    private ConcreteDataProvider() {
        {
            final Occupation foodService = new Occupation("0631", "Restaurant and Food Service Managers", Outlook.Balance, "Projected Canada-wide employment to grow from 112,600 to 117,500 by 2022.");
            masterList.add(foodService);
            addToLookup(Interest.People, foodService);
            addToLookup(Interest.Food, foodService);
            addToLookup(Interest.Organization, foodService);
        }

        {
            final Occupation accountants = new Occupation("1111", "Financial Auditors and Accountants", Outlook.Balance, "Projected Canada-wide employment to grow from 211,700 to 237,800 by 2022.");
            masterList.add(accountants);
            addToLookup(Interest.Math, accountants);
            addToLookup(Interest.Organization, accountants);
            addToLookup(Interest.Details, accountants);
            addToLookup(Interest.Computers, accountants);
        }

        {
            final Occupation planners = new Occupation("1226", "Conference and Event Planners", Outlook.Balance, "Projected Canada-wide employment to grow from 27,300, to 30,600 by 2022.");
            masterList.add(planners);
            addToLookup(Interest.People, planners);
            addToLookup(Interest.Food, planners);
            addToLookup(Interest.Organization, planners);
        }

        {
            final Occupation seng = new Occupation("2173", "Software Engineers", Outlook.Shortage, "Projected Canada-wide employment to grow from 45,500 to 54,400 by 2022.");
            masterList.add(seng);
            addToLookup(Interest.Math, seng);
            addToLookup(Interest.Organization, seng);
            addToLookup(Interest.Details, seng);
            addToLookup(Interest.Computers, seng);
        }

        {
            final Occupation diet = new Occupation("3132", "Dietitians and Nutritionists", Outlook.Shortage, "Projected Canada-wide employment to grow from 12,000 to 13,600 by 2022.");
            masterList.add(diet);
            addToLookup(Interest.People, diet);
            addToLookup(Interest.Food, diet);
            addToLookup(Interest.PhysicalActivity, diet);
        }

        {
            final Occupation rn = new Occupation("3152", "Registered Nurses", Outlook.Shortage, "Projected Canada-wide employment to grow from 280,800 to 335,300 by 2022.");
            masterList.add(rn);
            addToLookup(Interest.People, rn);
            addToLookup(Interest.Organization, rn);
            addToLookup(Interest.Details, rn);
            addToLookup(Interest.HelpingOthers, rn);
        }

        {
            final Occupation fire = new Occupation("6262", "Fire-fighters", Outlook.Shortage, "Projected Canada-wide employment to grow from 31,300 to 34,500 by 2022.");
            masterList.add(fire);
            addToLookup(Interest.People, fire);
            addToLookup(Interest.HelpingOthers, fire);
            addToLookup(Interest.PhysicalActivity, fire);
        }

        {
            final Occupation ops = new Occupation("7265", "Welders and Related Machine Operators", Outlook.Shortage, "Projected Canada-wide employment to grow from 109,000 to 122,000 by 2022.");
            masterList.add(ops);
            addToLookup(Interest.HandsOn, ops);
            addToLookup(Interest.Outdoors, ops);
            addToLookup(Interest.Organization, ops);
        }

        {
            final Occupation ops = new Occupation("1420", "Office Equipment Operators", Outlook.Surplus, "Projected Canada-wide employment to shrink from 51,200 to 46,700 by 2022.");
            masterList.add(ops);
            addToLookup(Interest.HandsOn, ops);
            addToLookup(Interest.Computers, ops);
        }

        {
            final Occupation agriculture = new Occupation("8430", "Agriculture And Horticulture Workers", Outlook.Surplus, "Projected Canada-wide employment to shrink from 84,600 to 82,400 by 2022.");
            masterList.add(agriculture);
            addToLookup(Interest.HandsOn, agriculture);
            addToLookup(Interest.Outdoors, agriculture);
        }
    }

    /**
     * Adds the given occupation to the corresponding set.
     *
     * @param interest
     * @param occupation
     */
    private void addToLookup(final Interest interest, final Occupation occupation) {
        if (!interestLookup.containsKey(interest)) {
            interestLookup.put(interest, new HashSet<Occupation>());
        }
        interestLookup.get(interest).add(occupation);
    }

    /**
     * @return the data provider singleton to reference.
     */
    public static DataProvider getTheInstance() {
        return THE_INSTANCE;
    }


    @Override
    public List<Occupation> getAllOccupations(List<Interest> interestedIn) {
        Set<Occupation> accumulate = new HashSet<Occupation>();
        if(interestedIn!=null) {
            for (Interest inter : interestedIn) {
                accumulate.addAll(interestLookup.get(inter));
            }
        }
        List<Occupation> toReturn = new ArrayList<Occupation>();
        toReturn.addAll(accumulate);
        return toReturn;
    }

    @Override
    public Occupation getOccupationByDisplayName(final String displayName) {
        for(Occupation o:masterList){
            if(o.getDisplayName().equals(displayName)){
                return o;
            }
        }
        return null;
    }

    @Override
    public List<InfoLink> getInfoLinks(final Occupation occupation, final String province, final String city) {
        List<InfoLink> toReturn = new ArrayList<InfoLink>();
        if (occupation != null && province != null && city != null) {
            /*
            Add in city links, if available.
             */
            final String cityLower = city.toLowerCase();
            switch (cityLower) {
                case "vancouver":
                    toReturn.add(new InfoLink("http://vancouvercitycentreesc.ca/job-search-resources/", "VAN - Jobs Resources"));
                    break;
                case "kingston":
                    toReturn.add(new InfoLink("https://www.cityofkingston.ca/city-hall/careers", "KING - Jobs Resources"));
                    break;
                default:
                    //TODO: add in other cities?
                    break;
            }

            /*
            Add in Province Links, if available.
             */
            final String provinceUpper = province.toUpperCase();
            switch (province) {
                case "ALBERTA":
                    toReturn.add(new InfoLink("http://www.albertacanada.com/opportunity/working/jobs.aspx", "AB - Jobs"));
                    toReturn.add(new InfoLink("http://www.albertacanada.com/opportunity/working/jobs-in-alberta.aspx", "AB - Job Opportunities"));
                    break;
                case "BRITISH COLUMBIA":
                    toReturn.add(new InfoLink("http://www.workbc.ca/CareerCompass", "BC - Career Compass"));
                    toReturn.add(new InfoLink("http://www.itabc.ca/youth/programs", "BC - Youth Programs"));
                    break;
                case "ONTARIO":
                    toReturn.add(new InfoLink("http://www.tcu.gov.on.ca/eng/employmentontario/jobs/", "ON - Jobs"));
                    toReturn.add(new InfoLink("http://www.skills.edu.gov.on.ca/OSP2Web/EDU/Welcome.xhtml", "ON - Skills Passport"));
                    break;
                default:
                    //TODO: add in other province support?
                    break;
            }


             /*
            Add in federal links.
             */
            try {
                String provinceUEncode = URLEncoder.encode(province, "UTF-8");
                String cityUEncode = URLEncoder.encode(city, "UTF-8");
                final String jobBankUrl = "http://www.jobbank.gc.ca/report-eng.do?lang=eng&noc=" + occupation.getCode() + "&regionKeyword=" + cityUEncode + ",+" + provinceUEncode + "&source=0&action=final";
                toReturn.add(new InfoLink(jobBankUrl, "Job Outlook"));
            } catch (UnsupportedEncodingException e) {
            }
        }
        //Always available federal links
        toReturn.add(new InfoLink("http://www.educationplanner.ca/", "Education Planner"));
        return toReturn;
    }
}
