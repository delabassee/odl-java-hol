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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public final class SpeakerRepository {

    private final CopyOnWriteArrayList<Speaker> speakers = new CopyOnWriteArrayList<>();

    public SpeakerRepository() {
        List<Speaker> speakersList = new ArrayList<>();
        InputStream csvFile = SpeakerRepository.class.getResourceAsStream("/speakers.csv");

        try (BufferedReader csvReader = new BufferedReader(new InputStreamReader(csvFile))) {
            String csvRow;
            while ((csvRow = csvReader.readLine()) != null) {
                String[] col = csvRow.split("\\|");
                speakersList.add(new Speaker(col[0], col[1], col[2], col[3], col[4], Track.valueOf(col[5].toUpperCase())));
            }
            this.speakers.addAll(speakersList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Speaker> getByLastName(String name) {
        List<Speaker> matchList = speakers.stream().filter((e) -> (e.getLastName().toLowerCase().contains(name.toLowerCase())))
                .collect(Collectors.toList());
        return matchList;
    }


    public List<Speaker> getByTrack(Track track) {

        List<Speaker> matchList = speakers.stream().filter((e) -> (e.getTrack() == track))
                .collect(Collectors.toList());
        return matchList;
    }


    public List<Speaker> getByCompany(String company) {

        List<Speaker> matchList = speakers.stream()
                .filter((e) -> (e.getCompany().toLowerCase().contains(company.toLowerCase())))
                .collect(Collectors.toList());
        return matchList;
    }


    public List<Speaker> getAll() {

        List<Speaker> allSpeakers = speakers.stream()
                .sorted(Comparator.comparing(Speaker::getLastName))
                .collect(Collectors.toList());
        return allSpeakers;
    }


    public Optional<Speaker> getById(String id) {

        Optional<Speaker> speaker = speakers.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
        return speaker;
    }

}
