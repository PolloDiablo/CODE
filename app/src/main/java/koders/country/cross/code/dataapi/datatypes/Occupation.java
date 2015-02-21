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
     * Some sort of high-low-medium number...<br> TODO: Maybe use an enum instead.
     */
    private final double outlook;

    /**
     * Create from a code and a display name.
     *
     * @param code        the occupation code.
     * @param displayName the name of the occupation.
     * @param outlook     the outlook rating, positive, flat, negative.
     */
    public Occupation(final String code, final String displayName, final double outlook) {
        this.code = code;
        this.displayName = displayName;
        this.outlook = outlook;
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getOutlook() {
        return outlook;
    }

    @Override
    public int compareTo(final Occupation another) {
        if (another == null) {
            throw new NullPointerException("Invalid argument.");
        }
        //TODO: confirm sort order.
        return (int) Math.signum(this.outlook - another.outlook);
    }
}
