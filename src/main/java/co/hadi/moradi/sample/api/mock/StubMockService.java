package co.hadi.moradi.sample.api.mock;

import com.github.tomakehurst.wiremock.WireMockServer;

public interface StubMockService {
    void setup(WireMockServer wireMockServer);
}
