package com.workcloud.wtm.rtm.adapter.in.web;

import org.openapitools.api.RtmApi;
import org.openapitools.model.RtmOperationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Adapter for RTM (Real-Time Management) module following Hexagonal Architecture.
 */
@RestController
public class RtmController implements RtmApi {

    @Override
    public ResponseEntity<Void> performRtmOperation(RtmOperationRequest rtmOperationRequest) {
        // TODO: Implement using use cases
        return ResponseEntity.accepted().build();
    }
}
