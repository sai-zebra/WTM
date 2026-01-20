package com.workcloud.wtm.sessions.adapter.in.web;

import org.openapitools.api.SessionsApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Adapter for Sessions module following Hexagonal Architecture.
 */
@RestController
public class SessionController implements SessionsApi {

    @Override
    public ResponseEntity<Void> loginSuccess() {
        // TODO: Implement using use cases
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> logout() {
        // TODO: Implement using use cases
        return ResponseEntity.noContent().build();
    }
}
