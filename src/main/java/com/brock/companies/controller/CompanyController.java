package com.brock.companies.controller;

import com.brock.companies.exception.SearchException;
import com.brock.companies.exception.UpdateException;
import com.brock.companies.model.Company;
import com.brock.companies.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller for managing companies.
 * Provides endpoints for searching and updating company information.
 */
@Tag(name = "Company API", description = "API for searching and updating companies")
@RestController
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    /**
     * Endpoint to search for companies by name or description.
     */
    @Operation(summary = "Search for companies", description = "Searches for companies by name or description")
    @GetMapping("/search")
    public ResponseEntity<List<Company>> search(@RequestParam String query) {
        try {
            List<Company> companies = companyService.searchCompanies(query);
            return ResponseEntity.ok(companies);
        } catch (SearchException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Endpoint to update a company's details.
     */
    @Operation(summary = "Update a company", description = "Updates a company's details by its ID")
    @PostMapping("/update")
    public ResponseEntity<String> updateCompany(@RequestParam String company_name_id, @RequestBody Map<String, Object> updates) {
        try {
            String result = companyService.updateCompany(company_name_id, updates);
            if (result.equals("Company not found.")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
            }
            return ResponseEntity.ok(result);
        } catch (UpdateException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating company.");
        }
    }
}
