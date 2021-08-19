package com.weixigu.json_api.rest_controller;

import com.weixigu.json_api.entity.MyError;
import com.weixigu.json_api.entity.Ping;
import com.weixigu.json_api.entity.PostArray;
import com.weixigu.json_api.exception.MyInvalidServletRequestParameterException;
import com.weixigu.json_api.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@RestController
public class MyRestController {
    private static final Logger LOGGER = Logger.getLogger(MyRestController.class.getName());
    private final MyService myService;
    private final Set<String> validSortBy;
    private final Set<String> validDirection;

    @Autowired
    public MyRestController(MyService myService) {
        this.myService = myService;
        this.validSortBy = new HashSet<>(Arrays.asList("id", "reads", "likes", "popularity"));
        this.validDirection = new HashSet<>(Arrays.asList("desc", "asc"));
    }

    //Route 1
    @GetMapping(value = "/api/ping")
    public ResponseEntity<Ping> getPing() {
        LOGGER.info("Sending GET request to /api/ping");
        Ping ping = new Ping();
        return ResponseEntity.status(200).body(ping);
    }

    //Validator for Route 2.
    private void validateRequestParams(String tags, String sortBy, String direction) throws MyInvalidServletRequestParameterException {
        if (tags == null || tags.length() == 0) {
            throw new MyInvalidServletRequestParameterException("Tags parameter is required.");
        }
        if (!this.validSortBy.contains(sortBy)) {
            throw new MyInvalidServletRequestParameterException("sortBy parameter is invalid.");
        }
        if (!this.validDirection.contains(direction)) {
            throw new MyInvalidServletRequestParameterException("Direction parameter is invalid.");
        }
    }

    //Route 2
    @GetMapping(value = "/api/posts")
    public ResponseEntity<?> getPosts(
            @RequestParam(name = "tags") String tags,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction) throws MyInvalidServletRequestParameterException {

        LOGGER.info("Sending GET request to /api/posts");

        //Validate request params
        LOGGER.info("Validating params: tags = " + tags + ", sortBy= " + sortBy + ", direction = " + direction);

        this.validateRequestParams(tags, sortBy, direction);

        //Fetch data
        try {
            LOGGER.info("Fetching Data.");
            PostArray posts = this.myService.getDataFromHatchwaysAPI(tags, sortBy, direction);
            return ResponseEntity.status(200).body(posts);
        } catch (Exception exception) {
            LOGGER.warning("Exception: " + exception.getMessage());
            return ResponseEntity.status(400).body(new MyError(exception.getMessage()));
        }
    }
}
