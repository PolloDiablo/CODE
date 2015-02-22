package koders.country.cross.code.dataapi.datatypes;

/**
 * Created by Tony on 20/02/2015.
 */
public enum Interest {
    Computers,
    Math,
    Organization,
    People,
    Food,
    Details,
    PhysicalActivity("Physical Activity"),
    HelpingOthers("Helping Others"),
    HandsOn("Hands On"),
    Outdoors;

    /**
     * How to display it.
     */
    private final String displayName;

    private Interest() {
        this.displayName = this.name();
    }

    private Interest(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
