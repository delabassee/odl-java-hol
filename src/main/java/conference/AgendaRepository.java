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

import conference.session.Keynote;
import conference.session.Lab;
import conference.session.Lecture;
import conference.session.Session;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class AgendaRepository {

    private final List<Session> sessionList;

    public AgendaRepository() {

        var keynote = new Keynote("001", "007", "The Future of Java Is Now");
        var s1 = new Lecture("005", "Java Language Futures - Mid 2020 Edition", "021", "https://speakerdeck/s1");
        var s2 = new Lecture("006", "ZGC: The Next Generation Low-Latency Garbage Collector", "005", "https://slideshare/s2");
        var s3 = new Lecture("007", "Continuous Monitoring with JDK Flight Recorder (JFR)", "010", "https://speakerdeck/007");
        var hol1 = new Lab("010", "Building Java Cloud Native Applications with Micronaut and OCI", "030", "https://github.com/micronaut");
        var hol2 = new Lab("011", "Using OCI to Build a Java Application", "019", "https://github.com/011");

        sessionList = List.of(keynote, s1, s2, s3, hol1, hol2);
    }


    public List<Session> getAll() {

        List<Session> allSessions = sessionList.stream()
                .collect(Collectors.toList());
        return allSessions;
    }


    public Optional<Session> getBySessionId(String sessionId) {

        Optional<Session> session = sessionList.stream()
                .filter(s -> s.getId().equals(sessionId))
                .findFirst();
        return session;
    }


}