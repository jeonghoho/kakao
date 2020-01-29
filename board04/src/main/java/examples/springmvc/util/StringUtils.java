package examples.springmvc.util;

public class StringUtils {
    public static String escapeHtml(String string) {
        StringBuilder escapedTxt = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            char tmp = string.charAt(i);
            switch (tmp) {
                case '<':
                    escapedTxt.append("&lt;");
                    break;
                case '>':
                    escapedTxt.append("&gt;");
                    break;
                case '&':
                    escapedTxt.append("&amp;");
                    break;
                case '"':
                    escapedTxt.append("&quot;");
                    break;
                case '\'':
                    escapedTxt.append("&#x27;");
                    break;
                case '/':
                    escapedTxt.append("&#x2F;");
                    break;
                case '\n':
                    escapedTxt.append("<br>");
                    break;
                default:
                    escapedTxt.append(tmp);
            }
        }
        return escapedTxt.toString();
    }
}
