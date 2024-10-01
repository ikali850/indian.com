package com.indian.indian.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogPostRequestDTO {
    private Long id;
    private String title;
    private String fullTitle;
    private String slug;
    private String content;
    private String date;
    private List<String> categories;
}
