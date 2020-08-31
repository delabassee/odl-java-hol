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

import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * The Speaker service endpoint.
 * http://localhost:8080/speakers Get all speakers
 * http://localhost:8080/speakers/{id} Get all speakers by id
 */
public class SpeakerService implements Service {
    private final SpeakerRepository speakers;
    private static final Logger LOGGER = Logger.getLogger(SpeakerService.class.getName());

    SpeakerService() {
        speakers = new SpeakerRepository();
    }

    @Override
    public void update(Routing.Rules rules) {
        rules.get("/", this::getAll)
                .get("/lastname/{lastname}", this::getByLastName)
                .get("/company/{companyName}", this::getByCompany)
                .get("/track/{track}", this::getByTrack)
                .get("/{id}", this::getSpeakersById);
    }


    private String getTrackDetail(Speaker speaker) {

        var trackDetail = switch (speaker.track()) {
            case DB -> "Oracle Database track";
            case JAVA -> "Java track";
            case MYSQL -> "MySQL track";
        };

        return trackDetail;
    }


    private void getAll(final ServerRequest request, final ServerResponse response) {
        LOGGER.fine("getAll");

        List<Speaker> allSpeakers = this.speakers.getAll();
        if (allSpeakers.size() > 0) {
            response.send(allSpeakers.stream()
                    .map(conference.Speaker::toJson)
                    .collect(Collectors.toList()));
        } else Util.sendError(response, 400, "getAll - no speaker found!?");


    }

    private void getByLastName(final ServerRequest request, final ServerResponse response) {
        LOGGER.fine("getByLastName");

        var lastname = request.path().param("lastname").trim();
        if (Util.isValidQueryStr(response, lastname)) {
            var match = this.speakers.getByLastName(lastname);
            if (match.size() > 0) response.send(match);
            else Util.sendError(response, 400, "getByLastName - not found: " + lastname);
        } else {
            Util.sendError(response, 500, "Internal error! getByLastName");
        }

    }


    private void getByTrack(final ServerRequest request, final ServerResponse response) {
        LOGGER.fine("getByTrack");

        try {
            var trackName = request.path().param("track").trim();
            Track track = Track.valueOf(trackName.toUpperCase());
            var match = this.speakers.getByTrack(track);
            if (match.size() > 0) response.send(match);
            else Util.sendError(response, 400, "getByTrack - not found: " + trackName);
        } catch (Exception e) {
            Util.sendError(response, 500, "Internal error! getByTrack: " + e.getMessage());
        }

    }


    private void getByCompany(final ServerRequest request, final ServerResponse response) {
        LOGGER.fine("getByCompany");

        try {
            var companyName = request.path().param("companyName").trim();
            if (Util.isValidQueryStr(response, companyName)) {
                var match = this.speakers.getByCompany(companyName);
                if (match.size() > 0) response.send(match);
                else Util.sendError(response, 400, "getByCompany - not found: " + companyName);
            }
        } catch (Exception e) {
            Util.sendError(response, 500, "Internal error! getByCompany: " + e.getMessage());
        }

    }

    private void getSpeakersById(ServerRequest request, ServerResponse response) {
        LOGGER.fine("getSpeakersById");

        String id = request.path().param("id").trim();

        record Speaker(String id, String name, String title, String company, String trackName) {
            JsonObject toJson() {
                return Json.createObjectBuilder()
                        .add("id", id)
                        .add("name", name)
                        .add("title", title)
                        .add("company", company)
                        .add("trackName", trackName)
                        .build();
            }
        }

        try {
            if (Util.isValidQueryStr(response, id)) {
                var match = this.speakers.getById(id);
                if (match.isPresent()) {
                    var s = match.get();
                    var speaker = new Speaker(s.id(),
                            s.firstName() + " " + s.lastName(),
                            s.title(),
                            s.company(),
                            getTrackDetail(match.get()));
                    response.send(speaker.toJson());
                } else Util.sendError(response, 400, "getSpeakersById - not found: " + id);
            }
        } catch (Exception e) {
            Util.sendError(response, 500, "Internal error! getSpeakersById : " + e.getMessage());
        }
    }

}
