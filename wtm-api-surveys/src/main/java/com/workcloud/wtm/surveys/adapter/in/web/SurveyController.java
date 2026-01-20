package com.workcloud.wtm.surveys.adapter.in.web;

import org.openapitools.api.SurveysApi;
import org.openapitools.model.SurveyCreate;
import org.openapitools.model.SurveyResponseCreate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Adapter for Surveys module following Hexagonal Architecture.
 */
@RestController
public class SurveyController implements SurveysApi {

    @Override
    public ResponseEntity<Void> createSurvey(SurveyCreate surveyCreate) {
        // TODO: Implement using use cases
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> listSurveys() {
        // TODO: Implement using use cases
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> submitSurveyResponse(String surveyId, SurveyResponseCreate surveyResponseCreate) {
        // TODO: Implement using use cases
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
