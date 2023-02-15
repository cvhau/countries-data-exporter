package org.cvhau.country;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;

public class CountriesResource {
    public static final String RESOURCE_FILENAME = "countries.json";
    private final List<Country> countries;
    private final int countriesCount;

    private CountriesResource() throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(RESOURCE_FILENAME)) {
            ObjectMapper objectMapper = new ObjectMapper();
            countries = objectMapper.readValue(inputStream, new TypeReference<List<Country>>() {
            });
            countries.sort(Comparator.comparing(c -> c.getName().getCommon()));
            countriesCount = countries.size();

            for (int i = 0; i < countriesCount; i++) {
                countries.get(i).setId(i + 1);
            }
        }
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
