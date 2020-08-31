/*
 * Copyright (c) 2020 Oracle and/or its affiliates. All rights reserved.
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

import javax.json.Json;
import javax.json.JsonObject;

public record Speaker (String id,
                       String firstName,
                       String lastName,
                       String title,
                       String company,
                       Track track) {

    JsonObject toJson() {
        JsonObject payload = Json.createObjectBuilder()
                .add("id", id)
                .add("firstName", firstName)
                .add("lastName", lastName)
                .add("title", title)
                .add("company", company)
                .add("track", track.toString())
                .build();
        return payload;
    }

}