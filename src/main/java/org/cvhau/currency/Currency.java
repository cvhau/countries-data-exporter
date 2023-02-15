package org.cvhau.currency;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class Currency {
    private int id;
    private String code;
    private String description;
    private String kind;
    private String sourceUri;

    public Currency(@NonNull TradingViewCurrency tradingViewCurrency) {
        this.id = 0;
        this.code = tradingViewCurrency.getCode();
        this.description = tradingViewCurrency.getDescription();
        this.kind = tradingViewCurrency.getKind();
        this.sourceUri = String.format("urn:xtv:%s", tradingViewCurrency.getId());
    }
}
