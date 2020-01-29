package examples.springmvc.dto;

import java.net.URLEncoder;

public class PageInfo {
    private int page;
    private String kind;
    private String value;

    public PageInfo(int page, String kind, String value) {
        this.page = page;
        this.kind = kind;
        this.value = value;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        try {
            if(kind == null || value == null)
                return "page=" + page;
            else
                return "page=" + page + "&kind=" + URLEncoder.encode(kind, "UTF-8") + "&value=" + URLEncoder.encode(value, "UTF-8");
        }catch(Exception ex){
            return "link error";
        }
    }
}
