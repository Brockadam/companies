package com.brock.companies.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Company {

    @JsonProperty("company_name_id")
    private String companyNameId;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("url")
    private String url;

    @JsonProperty("year_founded")
    private Integer yearFounded;  // Using Integer instead of int to handle null values

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("country")
    private String country;

    @JsonProperty("zip_code")
    private Object zipCode;  // Using Object to handle both numeric and string values

    @JsonProperty("full_time_employees")
    private String fullTimeEmployees;  // "10-Jan" is a string in your data, not a number range

    @JsonProperty("company_type")
    private String companyType;

    @JsonProperty("company_category")
    private String companyCategory;

    @JsonProperty("revenue_source")
    private String revenueSource;

    @JsonProperty("business_model")
    private String businessModel;

    @JsonProperty("social_impact")
    private String socialImpact;

    @JsonProperty("description")
    private String description;

    @JsonProperty("description_short")
    private String descriptionShort;

    @JsonProperty("source_count")
    private String sourceCount;  // "NA" is a string, not a number

    @JsonProperty("data_types")
    private String dataTypes;

    @JsonProperty("example_uses")
    private String exampleUses;

    @JsonProperty("data_impacts")
    private List<String> dataImpacts = new ArrayList<>();  // Initialize to prevent NPE

    @JsonProperty("financial_info")
    private String financialInfo;

    @JsonProperty("last_updated")
    private String lastUpdated;

    // Add getter that handles null dataImpacts
    public List<String> getDataImpacts() {
        if (dataImpacts == null) {
            return new ArrayList<>();
        }
        return dataImpacts;
    }
}