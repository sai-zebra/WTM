package com.workcloud.wtm.users.adapter.in.web;

import org.openapitools.api.UsersApi;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Adapter for Users module following Hexagonal Architecture.
 */
@RestController
public class UserController implements UsersApi {

    @Override
    public ResponseEntity<Void> getUser(String userId) {
        // TODO: Implement using use cases
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> listUsers() {
        // TODO: Implement using use cases
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> uploadUserImage(String userId, Resource body) {
        // TODO: Implement using use cases
        return ResponseEntity.ok().build();
    }
}
