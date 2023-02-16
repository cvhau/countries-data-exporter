package org.cvhau.exporters;

import org.cvhau.country.CountriesResource;
import org.cvhau.country.Country;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;

public class CountriesSQLExporter {

    public static void export() throws Exception {
        String outputResourceFilename = "countries.sql";

        List<Country> countries = CountriesResource.getCountries();

        StringBuilder sqlContent = new StringBuilder("INSERT INTO `countries` (`id`, `code`, `name`, `lang`, `lang3`) VALUES\n");

        countries.forEach(country -> {

            sqlContent.append(String.format(
                    "    (%s, '%s', '%s', '%s', '%s'),\n",
                    String.format("%3s", country.getId()).replace(' ', '0'),
                    country.getCode(),
                    country.getName(),
                    country.getLang(),
                    country.getLang3()
            ));
        });

        sqlContent.replace(sqlContent.length() - 2, sqlContent.length() - 1, ";");

        URL outputURL = CountriesSQLExporter.class.getClassLoader().getResource(outputResourceFilename);

        assert outputURL != null;

        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(outputURL.toURI()))) {
            fileOutputStream.write(sqlContent.toString().getBytes());
            fileOutputStream.flush();
        }

        System.out.println(sqlContent);
    }
}
