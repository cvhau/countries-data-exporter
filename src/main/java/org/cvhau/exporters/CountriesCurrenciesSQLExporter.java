package org.cvhau.exporters;

import org.cvhau.country.CountriesResource;
import org.cvhau.country.Country;
import org.cvhau.currency.CurrenciesResource;
import org.cvhau.currency.Currency;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;
import java.util.Optional;

public class CountriesCurrenciesSQLExporter {

    public static void export() throws Exception {
        String outputResourceFilename = "countries_currencies.sql";

        List<Country> countries = CountriesResource.getCountries();
        List<Currency> currencies = CurrenciesResource.getCurrencies();

        StringBuilder sqlContent = new StringBuilder("INSERT INTO `country_currency` (`country_id`, `currency_id`) VALUES\n");

        countries.forEach(country -> {
            country.getCurrencies().forEach(currencyCode -> {
                Optional<Currency> currency = currencies.stream()
                        .filter(c -> c.getCode().equals(currencyCode))
                        .findFirst();
                currency.ifPresent(c -> {
                    sqlContent.append(String.format(
                            "    (%s, %s),\n",
                            String.format("%3s", country.getId()).replace(' ', '0'),
                            String.format("%3s", c.getId()).replace(' ', '0')
                    ));
                });
            });
        });

        sqlContent.replace(sqlContent.length() - 2, sqlContent.length() - 1, ";");

        URL outputURL = CountriesCurrenciesSQLExporter.class.getClassLoader().getResource(outputResourceFilename);

        assert outputURL != null;

        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(outputURL.toURI()))) {
            fileOutputStream.write(sqlContent.toString().getBytes());
            fileOutputStream.flush();
        }

        System.out.println(sqlContent);
    }
}
