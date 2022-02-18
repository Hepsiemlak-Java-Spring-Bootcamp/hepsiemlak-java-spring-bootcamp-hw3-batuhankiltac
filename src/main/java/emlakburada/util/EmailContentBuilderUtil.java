package emlakburada.util;

public class EmailContentBuilderUtil {
    private static final String template = "<p>Hoşgeldin ${user},</p>\n" ;

    private EmailContentBuilderUtil() {}

    public static String build(String userName) {
        return template.replaceAll("\\$\\{*user}", userName.split("@")[0]);
    }
}