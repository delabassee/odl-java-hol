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

public final class Speaker {

    private final String id;
    private final String firstName;
    private final String lastName;
    private final String title;
    private final Track track;
    private final String company;

    public Speaker(String id, String firstName, String lastName, String title, String company, Track track) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.track = track;
        this.company = company;
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