package app.banking.bankmuscat.merchant.api;

/**
 * Created by amit.randhawa on 8/22/2016.
 */
public enum RESTUrl {

    ;

    private  String url;
    private RESTUrl(String url)
    {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

}
