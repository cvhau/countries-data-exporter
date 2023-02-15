package org.cvhau.country;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class NameDetail {
    private String common;
    private String official;
    private JsonNode nativeName;
}
