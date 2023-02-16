package org.cvhau;

import org.cvhau.exporters.CountriesCurrenciesSQLExporter;
import org.cvhau.exporters.CountriesSQLExporter;
import org.cvhau.exporters.CurrenciesSQLExporter;
import org.cvhau.investing.InvestingCountriesValidator;

public class Main {
    public static void main(String[] args) throws Exception {

        CountriesSQLExporter.export();
        CurrenciesSQLExporter.export();
        CountriesCurrenciesSQLExporter.export();
        InvestingCountriesValidator.validate();
    }
}
