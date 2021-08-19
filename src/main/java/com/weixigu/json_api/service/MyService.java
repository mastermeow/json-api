package com.weixigu.json_api.service;

import com.weixigu.json_api.entity.Post;
import com.weixigu.json_api.entity.PostArray;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.logging.Logger;

//Service provider: fetch data from Hatchways API, filter and return data based on input params.
@Service("postService")
public class MyService {

    private static final Logger LOGGER = Logger.getLogger(MyService.class.getName());
    private static final String HATCHWAYS_API_BASE_URL = "https://api.hatchways.io/assessment/blog/posts";

    public PostArray getDataFromHatchwaysAPI(String inputTags, String sortBy, String direction) throws RuntimeException {
        LOGGER.info("Fetching data from Hatchways API.");

        //Split tags.
        //String baseURI = "https://api.hatchways.io/assessment/blog/posts";
        String[] tags = inputTags.split(",");

        //Fetch data
        Set<Post> postSet = new HashSet<>(); //use set to remove redundant data.

        for (String tag : tags) {
            String uri = MyService.HATCHWAYS_API_BASE_URL + "?tag=" + tag;
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<PostArray> responseEntity = restTemplate.getForEntity(uri, PostArray.class);
            PostArray postArray = responseEntity.getBody(); //the array of posts in the JSON blob.
            Post[] posts = postArray.getPosts();
            postSet.addAll(Arrays.asList(posts));
        }

        //Sort data.
        List<Post> postList = new ArrayList<>();
        postList.addAll(postSet);
        this.sortPosts(postList, sortBy, direction);

        //Return data
        return new PostArray(postList.toArray(Post[]::new));
    }

    private void sortPosts(List<Post> posts, String sortBy, String direction) {
        LOGGER.info("Sorting posts.");
        if (sortBy.equals("id")) {
            posts.sort(Comparator.comparingLong(Post::getId));
        } else if (sortBy.equals("reads")) {
            posts.sort(Comparator.comparingLong(Post::getReads));
        } else if (sortBy.equals("likes")) {
            posts.sort(Comparator.comparingLong(Post::getLikes));
        } else if (sortBy.equals("popularity")) {
            posts.sort(Comparator.comparingDouble(Post::getPopularity));
        }

        if (direction.equals("desc")) {
            Collections.reverse(posts);
        }
    }
}
