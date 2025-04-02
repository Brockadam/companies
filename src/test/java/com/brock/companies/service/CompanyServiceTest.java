package com.brock.companies.service;

import com.brock.companies.model.Company;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the CompanyService class.
 * Tests include searching for companies, updating companies, and verifying interactions with the ObjectMapper.
 */
@SpringBootTest
public class CompanyServiceTest {

    @InjectMocks
    private CompanyService companyService;

    @Mock
    private ObjectMapper objectMapper;

    @Value("${company.json.filepath}")
    private String jsonFilePath;

    private List<Company> testCompanies;

    /**
     * Sets up the test environment by creating test companies and mocking
     * the behavior of the ObjectMapper to simulate reading from a JSON file.
     * Initializes the company service with a list of test companies.
     *
     * @throws IOException if there is an issue reading the JSON file during setup
     */
    @BeforeEach
    public void setUp() throws IOException {
        testCompanies = new ArrayList<>();

        // Create test companies
        Company company1 = Company.builder()
                .companyNameId("1")
                .companyName("Company A")
                .url("https://companya.com")
                .yearFounded(2000)
                .city("City A")
                .state("State A")
                .country("Country A")
                .zipCode("12345")
                .fullTimeEmployees("1000")
                .companyType("Tech")
                .companyCategory("Software")
                .revenueSource("Product Sales")
                .businessModel("B2B")
                .socialImpact("Environmental")
                .description("Company A is a tech company.")
                .descriptionShort("Tech company A")
                .sourceCount("5")
                .dataTypes("Financial")
                .exampleUses("Financial analysis")
                .financialInfo("Revenue: $1B")
                .lastUpdated("2024-04-01")
                .build();

        Company company2 = Company.builder()
                .companyNameId("2")
                .companyName("Company B")
                .url("https://companyb.com")
                .yearFounded(2010)
                .city("City B")
                .state("State B")
                .country("Country B")
                .zipCode("67890")
                .fullTimeEmployees("500")
                .companyType("Retail")
                .companyCategory("E-commerce")
                .revenueSource("Advertising")
                .businessModel("B2C")
                .socialImpact("Education")
                .description("Company B is an e-commerce platform.")
                .descriptionShort("E-commerce company B")
                .sourceCount("3")
                .dataTypes("Customer Data")
                .exampleUses("Retail analytics")
                .financialInfo("Revenue: $500M")
                .lastUpdated("2024-04-01")
                .build();

        testCompanies.add(company1);
        testCompanies.add(company2);

        // Mock JSON file reading
        when(objectMapper.readValue(any(File.class), any(TypeReference.class)))
                .thenReturn(testCompanies);

        setJsonFilePathForTest("src/test/resources/test_companies.json");

        // Initialize company service with test data
        companyService.companies = new ArrayList<>(testCompanies);
    }

    /**
     * Tests searching for companies by a partial name match.
     * Verifies that the company search functionality works correctly and returns the expected company.
     */
    @Test
    public void testSearchCompanies() {
        // Test searching for companies with a partial name match
        List<Company> result = companyService.searchCompanies("A");
        assertEquals(1, result.size(), "Expected exactly 1 matching company.");
        assertEquals("Company A", result.get(0).getCompanyName(), "Expected to find 'Company A'.");
    }

    /**
     * Tests searching for a company that doesn't exist.
     * Verifies that no companies are returned when the search term doesn't match any company.
     */
    @Test
    public void testSearchCompanies_NoMatch() {
        // Test searching for a company that doesn't exist
        List<Company> result = companyService.searchCompanies("Z");
        assertTrue(result.isEmpty(), "Expected no matches.");
    }

    /**
     * Tests updating the details of an existing company.
     * Verifies that the company is updated successfully and the changes are reflected.
     */
    @Test
    public void testUpdateCompany_Success() {
        // Prepare update data
        Map<String, Object> updates = new HashMap<>();
        updates.put("companyName", "Updated Company A");

        // Perform the update
        String result = companyService.updateCompany("1", updates);

        // Verify the result and the updated data
        assertEquals("Company updated successfully.", result);
        assertEquals("Updated Company A", testCompanies.get(0).getCompanyName());
    }

    /**
     * Tests attempting to update a company that doesn't exist.
     * Verifies that the correct error message is returned when a non-existent company is updated.
     */
    @Test
    public void testUpdateCompany_NotFound() {
        // Prepare update data for a non-existing company
        Map<String, Object> updates = new HashMap<>();
        updates.put("companyName", "Non-existent Company");

        // Perform the update on a non-existing company
        String result = companyService.updateCompany("999", updates);

        // Verify that the company was not found
        assertEquals("Company not found.", result);
    }

    /**
     * Uses reflection to set the jsonFilePath field in the CompanyService class for testing purposes.
     *
     * @param path the file path to set for the jsonFilePath field
     */
    private void setJsonFilePathForTest(String path) {
        try {
            Field field = CompanyService.class.getDeclaredField("jsonFilePath");
            field.setAccessible(true);
            field.set(companyService, path);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to set jsonFilePath in test", e);
        }
    }
}
