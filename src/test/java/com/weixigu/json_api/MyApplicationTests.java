package com.weixigu.json_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MyApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    public void getRoute1() throws Exception {
        this.mockMvc.perform(get("/api/ping").contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //This command prints the JSON blob on screen; You can comment it out if you don't like it.
                .andExpect(status().is(200));
    }

    @Test
    public void getRoute2ValidTag() throws Exception {
        this.mockMvc.perform(get("/api/posts").param("tags", "tech").contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //This command prints the JSON blob on screen; You can comment it out if you don't like it.
                .andExpect(status().is(200));
    }

    @Test
    public void getRoute2ValidTags() throws Exception {
        this.mockMvc.perform(get("/api/posts").param("tags", "tech, health").contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //This command prints the JSON blob on screen; You can comment it out if you don't like it.
                .andExpect(status().is(200));
    }

    @Test
    public void getRoute2ValidTagsAndSortBy() throws Exception {
        this.mockMvc.perform(get("/api/posts")
                .param("tags", "tech, health")
                .param("sortBy", "reads")
                .param("direction", "desc")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //This command prints the JSON blob on screen; You can comment it out if you don't like it.
                .andExpect(status().is(200));
    }

    @Test
    public void getRoute2InvalidSortBy() throws Exception {
        this.mockMvc.perform(get("/api/posts")
                .param("tags", "tech, health")
                .param("sortBy", "author")  //author should not be used for sorting.
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //This command prints the JSON blob on screen; You can comment it out if you don't like it.
                .andExpect(status().is(400));
    }

}
