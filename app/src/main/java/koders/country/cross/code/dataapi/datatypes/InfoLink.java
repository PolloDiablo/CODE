package koders.country.cross.code.dataapi.datatypes;

/**
 * Created by Tony on 20/02/2015.
 * <p/>
 * An InfoLink is a tuple that consists of a url and a display name.<br>
 */
public class InfoLink {

    private final String url;

    private final String displayName;

    public InfoLink(final String url, final String displayName) {
        this.url = url;
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUrl() {
        return url;
    }
}
