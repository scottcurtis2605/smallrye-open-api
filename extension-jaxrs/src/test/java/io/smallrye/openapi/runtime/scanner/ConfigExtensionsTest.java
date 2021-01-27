package io.smallrye.openapi.runtime.scanner;

import java.io.IOException;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.jboss.jandex.Index;
import org.json.JSONException;
import org.junit.Test;

import io.smallrye.openapi.api.OpenApiConfig;
import io.smallrye.openapi.api.OpenApiConfigImpl;
import io.smallrye.openapi.runtime.OpenApiProcessor;
import test.io.smallrye.openapi.runtime.scanner.entities.Greeting;
import test.io.smallrye.openapi.runtime.scanner.resources.GreetingGetResource;

/**
 * Basic tests to check the setting of certain Info elements via config, using
 * extensions config properties
 * 
 * @author Phillip Kruger (phillip.kruger@redhat.com)
 */
public class ConfigExtensionsTest extends JaxRsDataObjectScannerTestBase {

    private static final String TITLE = "mp.openapi.extensions.smallrye.info.title";
    private static final String VERSION = "mp.openapi.extensions.smallrye.info.version";
    private static final String DESCRIPTION = "mp.openapi.extensions.smallrye.info.description";
    private static final String TERMS = "mp.openapi.extensions.smallrye.info.termsOfService";
    private static final String CONTACT_EMAIL = "mp.openapi.extensions.smallrye.info.contact.email";
    private static final String CONTACT_NAME = "mp.openapi.extensions.smallrye.info.contact.name";
    private static final String CONTACT_URL = "mp.openapi.extensions.smallrye.info.contact.url";
    private static final String LICENSE_NAME = "mp.openapi.extensions.smallrye.info.license.name";
    private static final String LICENSE_URL = "mp.openapi.extensions.smallrye.info.license.url";

    @Test
    public void testSettingJustTitle() throws IOException, JSONException {
        System.setProperty(TITLE, "My Awesome Service");
        Config config = ConfigProvider.getConfig();
        OpenApiConfig openApiConfig = OpenApiConfigImpl.fromConfig(config);
        try {
            Index i = indexOf(GreetingGetResource.class, Greeting.class);
            OpenAPI result = OpenApiProcessor.bootstrap(openApiConfig, i);

            printToConsole(result);
            assertJsonEquals("resource.testTitleViaConfig.json", result);

        } finally {
            System.clearProperty(TITLE);
        }
    }

    @Test
    public void testSettingJustContactEmail() throws IOException, JSONException {
        System.setProperty(CONTACT_EMAIL, "phillip.kruger@redhat.com");
        Config config = ConfigProvider.getConfig();
        OpenApiConfig openApiConfig = OpenApiConfigImpl.fromConfig(config);
        try {
            Index i = indexOf(GreetingGetResource.class, Greeting.class);
            OpenAPI result = OpenApiProcessor.bootstrap(openApiConfig, i);

            printToConsole(result);
            assertJsonEquals("resource.testContactEmailViaConfig.json", result);

        } finally {
            System.clearProperty(CONTACT_EMAIL);
        }
    }

    @Test
    public void testSettingJustLicenseName() throws IOException, JSONException {
        System.setProperty(LICENSE_NAME, "Apache License 2.0");
        Config config = ConfigProvider.getConfig();
        OpenApiConfig openApiConfig = OpenApiConfigImpl.fromConfig(config);
        try {
            Index i = indexOf(GreetingGetResource.class, Greeting.class);
            OpenAPI result = OpenApiProcessor.bootstrap(openApiConfig, i);

            printToConsole(result);
            assertJsonEquals("resource.testLicenseNameViaConfig.json", result);

        } finally {
            System.clearProperty(LICENSE_NAME);
        }
    }

    @Test
    public void testSettingAllInfo() throws IOException, JSONException {

        System.setProperty(TITLE, "My own awesome REST service");
        System.setProperty(VERSION, "1.2.3");
        System.setProperty(DESCRIPTION, "This service is awesome");
        System.setProperty(TERMS, "The terms is also awesome");
        System.setProperty(CONTACT_EMAIL, "phillip.kruger@redhat.com");
        System.setProperty(CONTACT_NAME, "Phillip Kruger");
        System.setProperty(CONTACT_URL, "https://www.phillip-kruger.com");
        System.setProperty(LICENSE_NAME, "Apache License 2.0");
        System.setProperty(LICENSE_URL, "https://choosealicense.com/licenses/apache-2.0/");

        Config config = ConfigProvider.getConfig();
        OpenApiConfig openApiConfig = OpenApiConfigImpl.fromConfig(config);
        try {
            Index i = indexOf(GreetingGetResource.class, Greeting.class);
            OpenAPI result = OpenApiProcessor.bootstrap(openApiConfig, i);

            printToConsole(result);
            assertJsonEquals("resource.testAllInfoViaConfig.json", result);

        } finally {
            System.clearProperty(TITLE);
            System.clearProperty(VERSION);
            System.clearProperty(DESCRIPTION);
            System.clearProperty(TERMS);
            System.clearProperty(CONTACT_EMAIL);
            System.clearProperty(CONTACT_NAME);
            System.clearProperty(CONTACT_URL);
            System.clearProperty(LICENSE_NAME);
            System.clearProperty(LICENSE_URL);
        }
    }
}
