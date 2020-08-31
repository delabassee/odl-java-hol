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

import io.helidon.webserver.ServerResponse;

import javax.json.Json;
import java.util.HashMap;
import java.util.Map;

public class Util {

    /**
     * Validates the parameter.
     */
    static boolean isValidQueryStr(ServerResponse response, String nameStr) {

        Map<String, String> errorMessage = new HashMap<>();
        if (nameStr == null || nameStr.isEmpty() || nameStr.length() > 100) {
            errorMessage.put("errorMessage", "Invalid query string");
            response.status(400).send(errorMessage);
            return false;
        } else {
            return true;
        }
    }

    /**
     * Construct and send a JSON error
     */
    static void sendError(final ServerResponse response, int statusCode, String message) {
        response.status(statusCode)
                .send(Json.createObjectBuilder()
                        .add("message", message)
                        .add("statusCode", statusCode)
                        .build());
    }

}