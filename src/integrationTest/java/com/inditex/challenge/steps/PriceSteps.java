package com.inditex.challenge.steps;

import com.inditex.challenge.dto.GetPriceResponse;
import com.inditex.challenge.dto.PriceFilterRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import static org.assertj.core.api.Assertions.assertThat;


public class PriceSteps {

    private PriceFilterRequest request;
    private ResponseEntity<GetPriceResponse> response;

    @Autowired
    private TestRestTemplate restTemplate;

    @Given("request at {string} for product {int} and brand {int}")
    public void given(String applicationDate, Integer productId, Integer brandId) {
        this.request = new PriceFilterRequest(applicationDate, productId, brandId);
    }

    @When("I get the price")
    public void whenIGetThePrice() {
        final var url = UriComponentsBuilder.fromUriString("/v1/prices")
                .queryParam("datetime", request.getApplicationDate())
                .queryParam("productId", request.getProductId())
                .queryParam("brandId", request.getBrandId())
                .toUriString();

        this.response = restTemplate.getForEntity(url, GetPriceResponse.class);
    }

    @Then("the response should be price id {int}, price {float} and currency {string}")
    public void thenTheResponseShouldContain(Integer priceId, Float price, String currency) {
        assertThat(response).isNotNull();
        assertThat(this.response.getStatusCode()).isEqualTo(HttpStatus.OK);

        final GetPriceResponse body = this.response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getPriceId()).isEqualTo(priceId);
        assertThat(body.getPrice().floatValue()).isEqualTo(price);
        assertThat(body.getCurrency()).isEqualTo(currency);
    }

    @Then("price not found")
    public void priceNotFound() {
        assertThat(this.response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
