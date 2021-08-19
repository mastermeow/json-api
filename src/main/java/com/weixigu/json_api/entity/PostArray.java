package com.weixigu.json_api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

//Wrapper class for Post class
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostArray implements Serializable {
    @JsonProperty("posts")
    private Post[] posts = new Post[]{};
}
