package com.acastrillo.supermarketbackend.controllers;

import com.acastrillo.supermarketbackend.model.Sample;
import com.acastrillo.supermarketbackend.repositories.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class SampleController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SampleRepository sampleRepository;

    @GetMapping("/ping")
    public String ping() {
        return "Backend is running " + new Date();
    }

    @GetMapping("/samples")
    public List<Sample> getSamples() {
        return sampleRepository.findAll();
    }

    @PostMapping("/samples")
    public Sample createSample(Sample sample) {
        return sampleRepository.save(sample);
    }


}
