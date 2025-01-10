package com.inditex.challenge;

import io.cucumber.spring.CucumberContextConfiguration;
import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.r2dbc.AutoConfigureDataR2dbc;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Cucumber.class)
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureDataR2dbc
public class CucumberTestRunner {
}
