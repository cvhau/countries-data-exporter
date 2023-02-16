package org.cvhau.country;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class CountriesResource {
    public static final String RESOURCE_FILENAME = "countries.json";
    private final List<Country> countries;
    private final int countriesCount;

    private CountriesResource() throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(RESOURCE_FILENAME)) {
            ObjectMapper objectMapper = new ObjectMapper();
            List<RestCountry> restCountries = objectMapper.readValue(inputStream, new TypeReference<List<RestCountry>>() {
            });

            countries = new ArrayList<>();

            restCountries.forEach(restCountry -> {
                RestCountry.LanguageCodes langCodes = restCountry.getLanguageCodes();
                String countryName = restCountry.getName().getCommon();

                // Repair the common name of Czech Republic
                if (countryName.equals("Czechia")) {
                    countryName = "Czech Republic";
                }

                Country country = new Country();
                country.setCode(restCountry.getCca2());
                country.setName(countryName);
                country.setLang(langCodes.getLangCode());
                country.setLang3(langCodes.getLangCode3());
                country.setCurrencies(restCountry.getCurrencyCodes());

                countries.add(country);
            });

            countries.addAll(sourceMissingCountries());
            countries.sort(Comparator.comparing(Country::getName));
            countriesCount = countries.size();

            for (int i = 0; i < countriesCount; i++) {
                countries.get(i).setId(i + 1);
            }
        }
    }

    private @NonNull List<Country> sourceMissingCountries() {
        List<Country> missingCountries = new ArrayList<>();

        missingCountries.add(new Country(0, "EU", "European Union", "en", "eng", Set.of("EUR")));
        missingCountries.add(new Country(0, "EZ", "Euro Zone", "en", "eng", Set.of("EUR")));

        return missingCountries;
    }

    private static class SingletonHelper {
        private static final CountriesResource INSTANCE;

        static {
            try {
                INSTANCE = new CountriesResource();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static List<Country> getCountries() {
        return SingletonHelper.INSTANCE.countries;
    }

    public static int getCountriesCount() {
        return SingletonHelper.INSTANCE.countriesCount;
    }
}
