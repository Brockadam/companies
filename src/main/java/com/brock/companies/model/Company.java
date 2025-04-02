package com.brock.companies.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private int yearFounded;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("country")
    private String country;

    @JsonProperty("zip_code")
    private String zipCode;

    @JsonProperty("full_time_employees")
    private String fullTimeEmployees;

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
    private String sourceCount;

    @JsonProperty("data_types")
    private String dataTypes;

    @JsonProperty("example_uses")
    private String exampleUses;

//    @JsonProperty("data_impacts") This is commented out because I don't think the data is correct in the json file.
//    private String dataImpacts;

    @JsonProperty("financial_info")
    private String financialInfo;

    @JsonProperty("last_updated")
    private String lastUpdated;

}
