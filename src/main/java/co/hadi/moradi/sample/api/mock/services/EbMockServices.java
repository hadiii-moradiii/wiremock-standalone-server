package co.hadi.moradi.sample.api.mock.services;


import co.hadi.moradi.sample.api.mock.GeneralMaps;
import co.hadi.moradi.sample.api.mock.StubMockService;
import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class EbMockServices implements StubMockService {

    private WireMockServer wireMockServer;

    @Override
    public void setup(WireMockServer wireMockServer) {
        this.wireMockServer = wireMockServer;
        this.setupDefault()
                .setupCustomerSimpleInfo()
                .setupCustomerPhoto()
                .setupCustomerInfoByAccNo()
        ;

    }

    private EbMockServices setupDefault() {
        wireMockServer.stubFor(any(urlPathMatching("/some/*")).atPriority(99)
                .atPriority(10)
                .willReturn(aResponse()
                        .withStatus(404)
                        .withBody("{\"status\":\"Error\",\"message\":\"Endpoint not found\"}")));

        return this;
    }

    private EbMockServices setupCustomerSimpleInfo() {

        wireMockServer.stubFor(get(urlPathEqualTo("/some/Customers/CustomerSmplInfo")).atPriority(1)
                //.withHeader("Content-Type", equalToIgnoreCase("application/json"))
                .withQueryParam("ssn", matching("([0-9]*)"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "  \"SSN\": \"{{request.query.ssn}}\",\n" +
                                "  \"FirstName\": \"{{lookup parameters.first_name request.query.ssn }}\",\n" +
                                "  \"LastName\": \"{{lookup parameters.last_name request.query.ssn }}\",\n" +
                                "  \"EngFirstName\": \"\",\n" +
                                "  \"EngLastName\": \"\",\n" +
                                "  \"Photo\": null\n" +
                                "}")
                        .withTransformers("response-template")
                        .withTransformerParameter("first_name", GeneralMaps.firstName)
                        .withTransformerParameter("last_name", GeneralMaps.lastName)
                ));
        return this;
    }

    private EbMockServices setupCustomerPhoto() {

        wireMockServer.stubFor(get(urlPathEqualTo("/some/Customers/CustomerPhoto")).atPriority(1)
                .withQueryParam("ssn", matching("([0-9]*)"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "  \"SSN\": \"{{request.query.ssn}}\",\n" +
                                "  \"Photo\": \"{{lookup parameters.user_photo request.query.ssn }}\"" +
                                "}")
                        .withTransformers("response-template")
                        .withTransformerParameter("user_photo", GeneralMaps.userPhoto)
                ));
        return this;
    }

    private EbMockServices setupCustomerInfoByAccNo() {

        wireMockServer.stubFor(get(urlPathEqualTo("/some/Customers/GetCustomerInfoByAccNo")).atPriority(1)
                .withQueryParam("accNo", matching("([0-9]*)"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "\"SSN\": \"123333\",\n" +
                                "\"FirstName\": \"دانشگاه سس\",\n" +
                                "\"LastName\": \" \",\n" +
                                "\"EngFirstName\": \"\",\n" +
                                "\"EngLastName\": \"\",\n" +
                                "\"FatherName\": \" \",\n" +
                                "\"IdNo\": \"0100132490358\",\n" +
                                "\"IdSeries\": \" \",\n" +
                                "\"IdSerial\": \" \",\n" +
                                "\"TypeID\": \"02\",\n" +
                                "\"SubTypeID\": \"07\" \n" +
                                "}")
                        .withTransformers("response-template")
                ));
        return this;
    }
}
