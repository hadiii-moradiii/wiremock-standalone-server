package co.hadi.moradi.sample.api.mock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import co.hadi.moradi.sample.api.mock.services.EbMockServices;
import co.hadi.moradi.sample.api.mock.utils.JsonUtil;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class Stubs {
    private JsonUtil jsonUtil;
    public WireMockServer wireMockServer;

    public Stubs setUp() {
        wireMockServer = new WireMockServer(wireMockConfig().port(3456)
              /*.bindAddress("127.0.0.1")
                // for develop
                .usingFilesUnderClasspath("src/main/resources")
                .withRootDirectory("D:/jwork/eb-mock-server/src/main/resources/")*/
                // for test
                .withRootDirectory("/opt/app/")
                .extensions(new ResponseTemplateTransformer(false))
        );
        wireMockServer.addMockServiceRequestListener(Stubs::requestReceived);
        wireMockServer.start();
        // WireMock.configureFor("127.0.0.1", wireMockServer.port());
        return this;
    }

    /**
     * Logs information from supplied WireMock request and response objects.
     */
    protected static void requestReceived(Request inRequest,
                                          com.github.tomakehurst.wiremock.http.Response inResponse) {
        System.out.println("WireMock request at URL: " + inRequest.getAbsoluteUrl());
        String reqBody = inRequest.getBodyAsString();
        if (reqBody != null) {
            System.out.println("WireMock request body: \n" + reqBody);
        }
        System.out.println("WireMock request headers: \n" + inRequest.getHeaders());
        System.out.println("WireMock response body: \n" + inResponse.getBodyAsString());
        System.out.println("WireMock response headers: \n" + inResponse.getHeaders());
    }

    public Stubs resetServer() {
        wireMockServer.resetAll();
        return this;
    }

    public Stubs setupEbMockServer() {
        new EbMockServices().setup(this.wireMockServer);
        return this;
    }


    public Stubs status() {
        System.out.println("Stubs Started!");
        return this;
    }
}