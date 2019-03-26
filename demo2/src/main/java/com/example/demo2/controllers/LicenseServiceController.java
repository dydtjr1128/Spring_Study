package com.example.demo2.controllers;

import com.example.demo2.models.License;
import com.example.demo2.services.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController//REST 기반 서비스라고 명시하고 서비스 요청 응답을 JSON으로 자동 역/직렬화 해주는 역활
@RequestMapping(value = "v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {

    @Autowired
    private LicenseService licenseService;

    @RequestMapping(value="/{licenseId}",method = RequestMethod.GET)
    public License getLicenses(@PathVariable("organizationId") String organizationId,
                               @PathVariable("licenseId") String licenseId) {

        //return licenseService.getLicense(licenseId);
        return new License()
                .withId(licenseId)
                .withOrganizationId(organizationId)
                .withProductName("Teleco")
                .withLicenseType("Seat");
    }

    @RequestMapping(value="{licenseId}",method = RequestMethod.PUT)
    public String updateLicenses( @PathVariable("licenseId") String licenseId) {
        return String.format("This is the put");
    }

    @RequestMapping(value="{licenseId}",method = RequestMethod.POST)
    public String saveLicenses( @PathVariable("licenseId") String licenseId) {
        return String.format("This is the post");
    }

    @RequestMapping(value="{licenseId}",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteLicenses( @PathVariable("licenseId") String licenseId) {
        return String.format("This is the Delete");
    }
}