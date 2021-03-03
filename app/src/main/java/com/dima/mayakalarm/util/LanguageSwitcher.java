package com.dima.mayakalarm.util;

import android.content.Context;
import android.content.res.Configuration;

import com.dima.mayakalarm.model.Alarm;
import com.dima.mayakalarm.repository.Repository;

import java.util.Locale;

public class LanguageSwitcher {

    private final Context context;

    public LanguageSwitcher (Context context) {
        this.context = context;
    }

    public void switchLanguage() {
        Repository repositoryPrefs = new Repository(context);
        Alarm alarm = repositoryPrefs.getAlarmClock();
        String currentLang = alarm.getLanguage();

        if (currentLang.equals("en")) {
            Locale localeRu = new Locale("ru", "RU");
            Locale.setDefault(localeRu);
            Configuration config = context.getResources().getConfiguration();
            config.locale = localeRu;
            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
            alarm.setLanguage("ru");
            repositoryPrefs.updateAlarmClock(alarm);

        } else {
            Locale localeEn = new Locale("en", "EN");
            Locale.setDefault(localeEn);
            Configuration config = context.getResources().getConfiguration();
            config.locale = localeEn;
            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
            alarm.setLanguage("en");
            repositoryPrefs.updateAlarmClock(alarm);
        }
    }
}
