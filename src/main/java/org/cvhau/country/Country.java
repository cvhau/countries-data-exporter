package org.cvhau.country;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country {
    private int id;
    private String code;
    private String name;
    private String lang;
    private String lang3;

    /**
     * Set of currency code
     */
    private Set<String> currencies;
}
