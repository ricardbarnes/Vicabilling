package com.vonblum.vicabilling.app.tool;

import com.vonblum.vicabilling.app.Context;
import com.vonblum.vicabilling.app.config.AppConstant;
import com.vonblum.vicabilling.app.data.model.BillSettings;
import com.vonblum.vicabilling.app.data.model.Company;
import com.vonblum.vicabilling.app.service.ObjectSerializer;

import java.net.URL;

public class ContextLoader {

    private final ObjectSerializer serializer;

    public ContextLoader(ObjectSerializer serializer) {
        this.serializer = serializer;
    }

    public void loadAll() {
        loadCss();
        loadCompany(serializer);
        loadBillSettings();
    }

    public void loadCss() {
        URL cssUrl = getClass().getResource(AppConstant.CSS_RES_PATH);
        Context.setCss(cssUrl.toExternalForm());
    }

    public void loadCompany(ObjectSerializer serializer) {
        Company company = (Company) serializer.deserializeObject(Company.getFilepath());
        Context.setCompany(company);
    }

    public void loadBillSettings() {
        BillSettings billSettings = BillSettings.getStandardConfig();
        Context.setBillSettings(billSettings);
    }

}
