
package com.omaressam.browslyze2.NetworkLayer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetType {

    @SerializedName("categories")
    @Expose
    private List<String> categories = null;
    @SerializedName("domainName")
    @Expose
    private String domainName;
    @SerializedName("websiteResponded")
    @Expose
    private Boolean websiteResponded;

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public Boolean getWebsiteResponded() {
        return websiteResponded;
    }

    public void setWebsiteResponded(Boolean websiteResponded) {
        this.websiteResponded = websiteResponded;
    }

}
