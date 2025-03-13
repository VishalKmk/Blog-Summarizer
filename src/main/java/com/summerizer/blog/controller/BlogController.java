package com.summerizer.blog.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.summerizer.blog.dto.BlogDto;
import com.summerizer.blog.dto.BlogSummaryRequest;
import com.summerizer.blog.service.BlogService;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    private final BlogService blogService;
    private final PagedResourcesAssembler<BlogDto> pagedResourcesAssembler;

    public BlogController(BlogService blogService, PagedResourcesAssembler<BlogDto> pagedResourcesAssembler) {
        this.blogService = blogService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @PostMapping
    public ResponseEntity<BlogDto> createBlog(@Valid @RequestBody BlogDto blogDto) {
        return new ResponseEntity<>(blogService.createBlog(blogDto), HttpStatus.CREATED);
    }

    @GetMapping
    public PagedModel<EntityModel<BlogDto>> getBlogs(Pageable pageable) {
        Page<BlogDto> blogs = blogService.getAllBlogs(pageable);
        return pagedResourcesAssembler.toModel(blogs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogDto> getBlogById(@PathVariable Long id) {
        return ResponseEntity.ok(blogService.getBlogById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogDto> updateBlog(@PathVariable Long id, @Valid @RequestBody BlogDto blogDto) {
        return ResponseEntity.ok(blogService.updateBlog(id, blogDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/summary")
    public ResponseEntity<BlogDto> generateSummary(@RequestBody BlogSummaryRequest request) {
        return ResponseEntity.ok(blogService.generateSummary(request.getBlogId()));
    }
}