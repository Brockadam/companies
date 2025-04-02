package com.brock.companies.service;

import com.brock.companies.exception.DataLoadException;
import com.brock.companies.exception.SearchException;
import com.brock.companies.exception.UpdateException;
import com.brock.companies.model.Company;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Service class for managing company data.
 */
@Service
public class CompanyService {

    List<Company> companies;
    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${company.json.filepath}")
    private String jsonFilePath;

    /**
     * Initializes the service by loading company data from the specified JSON file.
     * This method is called automatically after the service is constructed.
     */
    @PostConstruct
    public void loadCompanies() throws IOException {
        try {
            companies = mapper.readValue(Paths.get(jsonFilePath).toFile(), new TypeReference<List<Company>>() {
            });
        } catch (IOException e) {
            throw new DataLoadException("Failed to load company data from JSON file.", e);
        }
    }

    /**
     * Searches for companies that match the specified query.
     * The search is case-insensitive and checks the company name and description.
     */
    public List<Company> searchCompanies(String query) throws SearchException {
        try {
            String regex = "\\b" + Pattern.quote(query) + "\\b"; // Whole word match
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            return companies.stream()
                    .filter(company -> {
                        Matcher nameMatcher = pattern.matcher(company.getCompanyName());
                        Matcher descMatcher = pattern.matcher(company.getDescription());
                        return nameMatcher.find() || descMatcher.find();
                    })
                    .limit(50)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new SearchException("Error occurred while searching for companies.", e);
        }
    }

    /**
     * Updates the details of a company based on the provided company ID and update data.
     * The updates are applied using reflection to match JSON property names to Java field names.
     */
    public String updateCompany(String company_name_id, Map<String, Object> updates) throws UpdateException {
        try {
            for (Company company : companies) {
                if (company.getCompanyNameId().equals(company_name_id)) {
                    updates.forEach((jsonProperty, value) -> {
                        try {
                            String fieldName = getFieldNameFromJsonProperty(jsonProperty);
                            Field field = Company.class.getDeclaredField(fieldName);
                            field.setAccessible(true);
                            field.set(company, value);
                        } catch (NoSuchFieldException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });
                    saveUpdatedCompanies();
                    return "Company updated successfully.";
                }
            }
            return "Company not found.";
        } catch (Exception e) {
            throw new UpdateException("Error occurred while updating company.", e);
        }
    }

    /**
     * Maps a JSON property name to its corresponding Java field name in the Company class.
     */
    public String getFieldNameFromJsonProperty(String jsonProperty) {
        for (Field field : Company.class.getDeclaredFields()) {
            JsonProperty annotation = field.getAnnotation(JsonProperty.class);
            if (annotation != null && annotation.value().equals(jsonProperty)) {
                return field.getName();
            }
        }
        return jsonProperty;
    }

    /**
     * Saves the updated list of companies back to the JSON file in a pretty-printed format.
     */
    private void saveUpdatedCompanies() throws IOException {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(jsonFilePath), companies);
        } catch (IOException e) {
            throw new IOException("Failed to save updated company data to JSON file.", e);
        }
    }
}
