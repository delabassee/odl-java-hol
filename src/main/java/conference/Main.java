/*
 * Copyright (c) 2020 Oracle and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package conference;

import io.helidon.common.http.MediaType;
import io.helidon.config.Config;
import io.helidon.media.jsonb.JsonbSupport;
import io.helidon.media.jsonp.JsonpSupport;
import io.helidon.webserver.Routing;
import io.helidon.webserver.StaticContentSupport;
import io.helidon.webserver.WebServer;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

/**
 * Conferences REST app
 */
public final class Main {

    /**
     * Cannot be instantiated.
     */
    private Main() {
    }

    /**
     * Application main entry point.
     *
     * @param args command line arguments.
     * @throws IOException if there are problems reading logging properties
     */
    public static void main(final String[] args) throws IOException {
        startServer();
    }

    /**
     * Start the server.
     *
     * @return the created {@link WebServer} instance
     * @throws IOException if there are problems reading logging properties
     */
    static WebServer startServer() throws IOException {

        // load logging configuration
        try (InputStream logFile = Main.class.getResourceAsStream("/logging.properties")) {
            LogManager.getLogManager().readConfiguration(logFile);
        }

        // By default this will pick up application.yaml from the classpath
        Config config = Config.create();

        // Get webserver config from the "server" section of application.yaml and JSON support registration
        WebServer server = WebServer.builder(createRouting(config))
                .config(config.get("server"))
                .addMediaSupport(JsonpSupport.create())
                .addMediaSupport(JsonbSupport.create())
                .build();

        server.start().thenAccept(ws -> {
            System.out.println("WEB server is up (Lab10 branch) at: http://localhost:" + ws.port() + "/public/index.html");
            ws.whenShutdown().thenRun(() -> System.out.println("WEB server is DOWN. Bye!"));
        }).exceptionally(t -> {
            System.err.println("Startup failed: " + t.getMessage());
            t.printStackTrace(System.err);
            return null;
        });

        // Server threads are not daemon. No need to block. Just react.

        return server;
    }

    /**
     * Creates new {@link Routing}.
     *
     * @param config configuration of this server
     * @return routing configured with a health check, and a service
     */
    private static Routing createRouting(Config config) {

        SpeakerService speakerService = new SpeakerService();

        var snippet = """
              <html>
                 <title>Almost there</title>
                 <body>
                    You are being redirected...
                    <meta http-equiv="refresh" content="0; url=/public/" />
                </body>
              </html>
              """;

        return Routing.builder()
                .get("/", (req, res) -> {
                    res.headers().contentType(MediaType.TEXT_HTML);
                    res.send(snippet);})
                .register("/public", StaticContentSupport.builder("public").welcomeFileName("index.html"))
                .register("/speakers", speakerService)
                .build();
    }

}



