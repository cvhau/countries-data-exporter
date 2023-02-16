package org.cvhau.investing;

import lombok.NonNull;
import org.cvhau.country.CountriesResource;
import org.cvhau.country.Country;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class InvestingCountriesValidator {
    public static final String INVESTING_RESOURCE_NAME = "investing_countries.txt";

    public static void validate() throws IOException {
        List<String> investingCountries = new ArrayList<>();

        try (InputStream inputStream = InvestingCountriesValidator.class.getClassLoader().getResourceAsStream(INVESTING_RESOURCE_NAME)) {
            assert inputStream != null;
            String content = new String(inputStream.readAllBytes());
            StringTokenizer tokenizer = new StringTokenizer(content, "\n");
            while (tokenizer.hasMoreTokens()) {
                String countryName = tokenizer.nextToken();
                if (countryName != null && !countryName.isBlank()) {
                    investingCountries.add(countryName.trim());
                }
            }
        }

        // Start validate
        List<Country> countries = CountriesResource.getCountries();
        Map<String, String> altCountries = alternativeCountries();

        investingCountries.forEach(countryName -> {
            String altCountryName = altCountries.getOrDefault(countryName, countryName);
            if (countries.parallelStream().noneMatch(country -> country.getName().equals(altCountryName))) {
                System.out.println(countryName);
            }
        });
        System.out.println("================");
        System.out.println("Validated done.");
    }

    public static @NonNull Map<String, String> alternativeCountries() {
        Map<String, String> altCountries = new TreeMap<>();

        altCountries.put("Bosnia-Herzegovina", "Bosnia and Herzegovina");
        altCountries.put("Cote D'Ivoire", "Ivory Coast");
        altCountries.put("Palestinian Territory", "Palestine");
        altCountries.put("Türkiye", "Turkey");

        return altCountries;
    }
}
