package net.novafactory.example.gradle.buildtypes;

/**
 * @author nova
 * @since 2014. 8. 5.
 */
public class Setting {

    public static final String SERVER_URL = initServerUrl();

    private static String initServerUrl() {
        switch (BuildConfig.DEPLOY_PHASE) {
            case Debug:
                return "http://debug-server-url";
            case Stage:
                return "http://stage-server-url";
            case Release:
                return "http://release-server-url";
            default:
        }

        return null;
    }
}
