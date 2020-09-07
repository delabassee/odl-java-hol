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

import conference.session.*;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class AgendaService implements Service {

    private static final Logger LOGGER = Logger.getLogger(AgendaService.class.getName());
    private final AgendaRepository sessions;
    private final SpeakerRepository speakers = new SpeakerRepository();

    AgendaService() {
        sessions = new AgendaRepository();
    }

    @Override
    public void update(Routing.Rules rules) {
        rules.get("/", this::getAll);
        //rules.get("/detail/{sessionId}", this::getSessionDetails);
    }

/*    private void getSessionDetails(final ServerRequest request, final ServerResponse response) {
        LOGGER.fine("getSessionDetails");

        var sessionId = request.path().param("sessionId").trim();

        Optional<Session> session = sessions.getBySessionId(sessionId);
        if (session.isPresent()) {

            record SessionDetail(String title, String speaker, String location, String type) {
                JsonObject toJson() {
                    JsonObject payload = Json.createObjectBuilder()
                            .add("session", title)
                            .add("speaker", speaker)
                            .add("virtual_room", location)
                            .add("type", type)
                            .build();
                    return payload;
                }
            }

            var speakerDetail = "speaker TBC!";
            var s = session.get();

            if (s instanceof Keynote k) {

                var ks = speakers.getById(k.getKeynoteSpeaker());
                if (ks.isPresent()) {
                    var spk = ks.get();
                    speakerDetail = spk.firstName() + " " + spk.lastName() + " (" + spk.company() + ")";
                } else speakerDetail = "Keynote speaker to be announced!";

                var keynote = new SessionDetail("Keynote: " + k.getTitle(), speakerDetail, "Virtual Keynote hall", "General session");
                response.send(keynote.toJson());

            } else if (s instanceof Lecture l) {

                var speaker = speakers.getById(l.getSpeaker());
                if (speaker.isPresent()) {
                    var spk = speaker.get();
                    speakerDetail = spk.firstName() + " " + spk.lastName() + " (" + spk.company() + ")";
                }

                var lecture = new SessionDetail(l.getTitle(), speakerDetail, String.valueOf(l.getVirtualRoom()), "Conference session");
                response.send(lecture.toJson());

            } else if (s instanceof Lab l) {

                var speaker = speakers.getById(l.getSpeaker());
                if (speaker.isPresent()) {
                    var spk = speaker.get();
                    speakerDetail = spk.firstName() + " " + spk.lastName() + " (" + spk.company() + ")";
                }

                var lab = new SessionDetail(l.getTitle(), speakerDetail, String.valueOf(l.getVirtualRoom()), "Hands on Lab");
                response.send(lab.toJson());
            }

        } else {
            Util.sendError(response, 400, "SessionId not found : " + sessionId);
        }
    }*/


    private void getAll(final ServerRequest request, final ServerResponse response) {
        LOGGER.fine("getSessionsAll");

        List<Session> allSessions = this.sessions
                .getAll();
        response.send(allSessions);

    }

}
