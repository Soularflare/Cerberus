package com.project.cerberus.mumbleclient.db;

import com.project.cerberus.jumble.model.Server;

public class PublicServer extends Server {
    private String mCA;
    private String mContinentCode;
    private String mCountry;
    private String mCountryCode;
    private String mRegion;
    private String mUrl;

    public PublicServer(String name, String ca, String continentCode, String country, String countryCode, String ip, Integer port, String region, String url) {
        super(-1, name, ip, port.intValue(), "", "");
        this.mCA = ca;
        this.mContinentCode = continentCode;
        this.mCountry = country;
        this.mCountryCode = countryCode;
        this.mRegion = region;
        this.mUrl = url;
    }

    public String getCA() {
        return this.mCA;
    }

    public String getContinentCode() {
        return this.mContinentCode;
    }

    public String getCountry() {
        return this.mCountry;
    }

    public String getCountryCode() {
        return this.mCountryCode;
    }

    public String getRegion() {
        return this.mRegion;
    }

    public String getUrl() {
        return this.mUrl;
    }
}
