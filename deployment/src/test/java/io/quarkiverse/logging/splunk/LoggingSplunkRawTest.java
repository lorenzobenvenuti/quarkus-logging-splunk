/*
Copyright (c) 2021 Amadeus s.a.s.
Contributor(s): Kevin Viet, Romain Quinio (Amadeus s.a.s.)
 */
package io.quarkiverse.logging.splunk;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.test.QuarkusUnitTest;

class LoggingSplunkRawTest extends AbstractMockServerTest {

    @RegisterExtension
    static final QuarkusUnitTest unitTest = new QuarkusUnitTest()
            .withConfigurationResource("application-splunk-logging-raw.properties")
            .withConfigurationResource("mock-server.properties")
            .setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class));

    static final org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger(LoggingSplunkRawTest.class);

    @Test
    void sendsTheRawEvent() {
        logger.warn("hello splunk");
        awaitMockServer();
        httpServer.verify(requestToRawEndpoint().withBody("hello splunk"));
    }

}
