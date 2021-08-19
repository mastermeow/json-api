package com.weixigu.json_api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Post implements Serializable {
    //The order of the following fields is the same as the one shown in the sample solution.
    @JsonProperty("author")
    private String author = "Foo Bar";
    @JsonProperty("authorId")
    private long authorId = 0;
    @JsonProperty("id")
    private long id = 0;
    @JsonProperty("likes")
    private long likes = 0;
    @JsonProperty("popularity")
    private double popularity = 0.0;
    @JsonProperty("reads")
    private long reads = 0;
    @JsonProperty("tags")
    private String[] tags = new String[]{};

    //overwrite hashcode and equals method
    @Override
    public int hashCode() {
        return Long.valueOf(this.getId()).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        Post post = (Post) obj;
        if (this.getClass() != post.getClass() ||
                this.getId() != post.getId() ||
                !this.getAuthor().equals(post.getAuthor()) ||
                this.getAuthorId() != post.getAuthorId() ||
                this.getLikes() != post.getLikes() ||
                this.getPopularity() != post.getPopularity() ||
                this.getReads() != post.getReads() ||
                this.getTags().length != post.getTags().length) {
            return false;
        }
        return true;
    }
}


