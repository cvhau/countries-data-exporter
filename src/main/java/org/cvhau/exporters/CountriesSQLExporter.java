package org.cvhau.exporters;

import org.cvhau.country.CountriesResource;
import org.cvhau.country.Country;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class CountriesSQLExporter {

    public static void export() throws Exception {
        String outputResourceFilename = "countries.sql";

        List<Country> countries = CountriesResource.getCountries();

        StringBuilder sqlContent = new StringBuilder("INSERT INTO `countries` (`id`, `code`, `name`, `lang`, `lang3`) VALUES\n");

        countries.forEach(country -> {
            String id = String.format("%3s", country.getId()).replace(' ', '0');
            String code = country.getCca2();
            String name = country.getName().getCommon();
            Map<String, String> languages = country.getLanguages();

            String langCode3 = null;
            String langCode = null;

            if (languages == null || languages.isEmpty()) {
                langCode3 = "eng";
                langCode = "en";
            } else {
                langCode3 = languages.keySet().stream().findFirst().orElse("");
                langCode = langCode3.substring(0, 2).toLowerCase();
            }

            sqlContent.append(String.format("    (%s, '%s', '%s', '%s', '%s'),\n", id, code, name, langCode, langCode3));
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
