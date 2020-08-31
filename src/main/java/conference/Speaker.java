/*
 * Copyright (c) 2019 Oracle and/or its affiliates. All rights reserved.
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

import java.util.UUID;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public final class Speaker {

    private final String id;
    private final String firstName;
    private final String lastName;
    private final String title;
    private final Track track;
    private final String company;

    private Speaker(String id, String firstName, String lastName, String title, Track track, String company) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.track = track;
        this.company = company;
    }

    @JsonbCreator
    public static Speaker of(
            @JsonbProperty("id") String id,
            @JsonbProperty("firstName") String firstName,
            @JsonbProperty("lastName") String lastName,
            @JsonbProperty("title") String title,
            @JsonbProperty("track") Track track,
            @JsonbProperty("company") String company) {
        if (id == null || id.trim().equals("")) {
            id = UUID.randomUUID().toString();
        }
        Speaker s = new Speaker(id, firstName, lastName, title, track, company);
        return s;
    }

    public String getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getTitle() { return this.title; }

    public Track getTrack() {
        return this.track;
    }

    public String getCompany() {
        return this.company;
    }

    @Override
    public String toString() {
        return "ID: " + id + " First Name: " + firstName + " Last Name: " + lastName + " Title: " + title
               + " Track: " + track + " Company: " + company ;
    }

}