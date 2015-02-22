package koders.country.cross.code.dataapi.datatypes;

/**
 * Created by Tony on 20/02/2015.
 * <p/>
 * The occupation has a code and display name.
 */
public class Occupation implements Comparable<Occupation> {

    private final String code;

    private final String displayName;

    /**
     * Projected outlook for this occupation.
     */
    private final Outlook outlook;

    /**
     * Informational summary blurb.
     */
    private final String blurb;

    /**
     * Create from a code and a display name.
     *
     * @param code        the occupation code.
     * @param displayName the name of the occupation.
     * @param outlook     the outlook rating, positive, flat, negative.
     * @param blurb       the informational blurb/summary.
     */
    public Occupation(final String code, final String displayName, final Outlook outlook, final String blurb) {
        this.code = code;
        this.displayName = displayName;
        this.outlook = outlook;
        this.blurb = blurb;
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Outlook getOutlook() {
        return outlook;
    }

    public String getBlurb() {
        return blurb;
    }

    @Override
    public int compareTo(final Occupation another) {
        if (another == null) {
            throw new NullPointerException("Invalid argument.");
        }
        //TODO: confirm sort order.
        return (int) Math.signum(this.outlook.ordinal() - another.outlook.ordinal());
    }
}
