package com.dima.mayakalarm.util;

import android.content.Context;
import android.content.res.Configuration;

import com.dima.mayakalarm.repository.Repository;

import java.util.Locale;

public class LocaleManager {

    private Context context;
    private final Repository repository = Repository.getInstance(context);

    public LocaleManager(Context context) {
        this.context = context;
    }

    public void switchLocale() {

        if (repository.getAppLocale().equals("en")) {
            Locale localeRu = new Locale("ru", "RU");
            Locale.setDefault(localeRu);
            Configuration config = context.getResources().getConfiguration();
            config.locale = localeRu;
            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
            repository.saveAppLocale(localeRu.getLanguage());

        } else {
            Locale localeEn = new Locale("en", "EN");
            Locale.setDefault(localeEn);
            Configuration config = context.getResources().getConfiguration();
            config.locale = localeEn;
            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
            repository.saveAppLocale(localeEn.getLanguage());
        }
    }

    public String getCurrentLocale() {
        return repository.getAppLocale();
    }
}
