package org.cvhau.exporters;

import org.cvhau.currency.CurrenciesResource;
import org.cvhau.currency.Currency;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;

public class CurrenciesSQLExporter {

    public static void export() throws Exception {
        String outputResourceFilename = "currencies.sql";

        List<Currency> currencies = CurrenciesResource.getCurrencies();

        StringBuilder sqlContent = new StringBuilder("INSERT INTO `currencies` (`id`, `code`, `description`, `kind`, `source_uri`) VALUES\n");

        currencies.forEach(currency -> {
            String id = String.format("%3s", currency.getId()).replace(' ', '0');
            String code = currency.getCode();
            String description = currency.getDescription();
            String kind = currency.getKind();
            String sourceUri = currency.getSourceUri();

            sqlContent.append(String.format("    (%s, '%s', '%s', '%s', '%s'),\n", id, code, description, kind, sourceUri));
        });

        sqlContent.replace(sqlContent.length() - 2, sqlContent.length() - 1, ";");

        URL outputURL = CurrenciesSQLExporter.class.getClassLoader().getResource(outputResourceFilename);

        assert outputURL != null;

        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(outputURL.toURI()))) {
            fileOutputStream.write(sqlContent.toString().getBytes());
            fileOutputStream.flush();
        }

        System.out.println(sqlContent);
    }
}
