package org.cvhau.currency;

import lombok.Data;

@Data
public class TradingViewCurrency {
    private String id;
    private String code;
    private String description;
    private String kind;
    private String logoid;
}
