package org.cvhau.country;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Country {
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
}
