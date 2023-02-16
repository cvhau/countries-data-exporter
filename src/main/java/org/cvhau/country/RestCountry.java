package org.cvhau.country;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class RestCountry {
    private int id;
    private NameDetail name;
    private List<String> tld;
    private String cca2;
    private String ccn3;
    private String cca3;
    private String cioc;
    private boolean independent;
    private String status;
    private boolean unMember;
    private Map<String, CountryCurrency> currencies;
    private JsonNode idd;
    private List<String> capital;
    private List<String> altSpellings;
    private String region;
    private String subregion;
    private Map<String, String> languages;
    private JsonNode translations;
    private List<Double> latlng;
    private boolean landlocked;
    private List<String> borders;
    private double area;
    private JsonNode demonyms;
    private String flag;
    private Map<String, String> maps;
    private long population;
    private Map<String, Double> gini;
    private String fifa;
    private JsonNode car;
    private List<String> timezones;
    private List<String> continents;
    private Map<String, String> flags;
    private Map<String, String> coatOfArms;
    private String startOfWeek;
    private JsonNode capitalInfo;
    private JsonNode postalCode;

    public LanguageCodes getLanguageCodes() {
        String langCode3 = null;
        String langCode = null;

        if (languages == null || languages.isEmpty()) {
            langCode3 = "eng";
            langCode = "en";
        } else {
            langCode3 = languages.keySet().stream().findFirst().orElse("");
            langCode = langCode3.substring(0, 2).toLowerCase();
        }

        return new LanguageCodes(langCode, langCode3);
    }

    public @NonNull Set<String> getCurrencyCodes() {
        Set<String> currencyCodes = null;

        if (currencies == null || currencies.isEmpty()) {
            currencyCodes = new HashSet<>();
            currencyCodes.add("USD");
        } else {
            currencyCodes = currencies.keySet();
        }

        return currencyCodes;
    }

    @Data
    @AllArgsConstructor
    public static class LanguageCodes {
        private String langCode;
        private String langCode3;
    }
}
