package org.cvhau.currency;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CurrenciesResource {
    public static final String RESOURCE_FILENAME = "currencies.json";
    private final List<Currency> currencies;
    private final int currenciesCount;

    private CurrenciesResource() throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(RESOURCE_FILENAME)) {
            ObjectMapper objectMapper = new ObjectMapper();
            List<TradingViewCurrency> tradingViewCurrencies = objectMapper.readValue(inputStream, new TypeReference<List<TradingViewCurrency>>() {
            });

            currencies = new ArrayList<>();

            tradingViewCurrencies.forEach(tvCurrency -> {
                currencies.add(new Currency(tvCurrency));
            });

            currencies.addAll(sourceMissingCurrencies());
            currencies.sort(Comparator.comparing(Currency::getCode));
            currenciesCount = currencies.size();

            for (int i = 0; i < currenciesCount; i++) {
                currencies.get(i).setId(i + 1);
            }
        }
    }

    /**
     * A collection of missing currencies from TradingView resource.
     *
     * @return Collection of currencies
     */
    private @NonNull List<Currency> sourceMissingCurrencies() {
        List<Currency> currencies = new ArrayList<>();

        currencies.add(new Currency(0, "CKD", "Cook Islands dollar", "fiat", ""));
        currencies.add(new Currency(0, "CUC", "Cuban convertible peso", "fiat", ""));
        currencies.add(new Currency(0, "FKP", "Falkland Islands pound", "fiat", ""));
        currencies.add(new Currency(0, "FOK", "Faroese króna", "fiat", ""));
        currencies.add(new Currency(0, "GGP", "Guernsey pound", "fiat", ""));
        currencies.add(new Currency(0, "IMP", "Manx pound", "fiat", ""));
        currencies.add(new Currency(0, "JEP", "Jersey pound", "fiat", ""));
        currencies.add(new Currency(0, "KID", "Kiribati dollar", "fiat", ""));
        currencies.add(new Currency(0, "SHP", "Saint Helena pound", "fiat", ""));
        currencies.add(new Currency(0, "SSP", "South Sudanese pound", "fiat", ""));
        currencies.add(new Currency(0, "TVD", "Tuvaluan dollar", "fiat", ""));
        currencies.add(new Currency(0, "ZWL", "Zimbabwean dollar", "fiat", ""));

        return currencies;
    }

    private static class SingletonHelper {
        private static final CurrenciesResource INSTANCE;

        static {
            try {
                INSTANCE = new CurrenciesResource();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static List<Currency> getCurrencies() {
        return SingletonHelper.INSTANCE.currencies;
    }

    public static int getCurrenciesCount() {
        return SingletonHelper.INSTANCE.currenciesCount;
    }
}
