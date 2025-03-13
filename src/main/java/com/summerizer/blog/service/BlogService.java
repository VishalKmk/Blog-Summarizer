package com.summerizer.blog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.summerizer.blog.dto.BlogDto;
import com.summerizer.blog.exception.ResourceNotFoundException;
import com.summerizer.blog.model.Blog;
import com.summerizer.blog.repository.BlogRepository;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final AiService aiService;

    public BlogDto createBlog(BlogDto blogDto) {
        Blog blog = Blog.builder()
                .title(blogDto.getTitle())
                .content(blogDto.getContent())
                .author(blogDto.getAuthor())
                .build();

        Blog savedBlog = blogRepository.save(blog);
        return mapToDto(savedBlog);
    }

    public Page<BlogDto> getAllBlogs(Pageable pageable) {
        Page<Blog> blogs = blogRepository.findAll(pageable);
        return blogs.map(this::mapToDto);
    }

    public BlogDto getBlogById(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found with id: " + id));
        return mapToDto(blog);
    }

    public BlogDto updateBlog(Long id, BlogDto blogDto) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found with id: " + id));

        blog.setTitle(blogDto.getTitle());
        blog.setContent(blogDto.getContent());
        blog.setAuthor(blogDto.getAuthor());

        Blog updatedBlog = blogRepository.save(blog);
        return mapToDto(updatedBlog);
    }

    public void deleteBlog(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found with id: " + id));
        blogRepository.delete(blog);
    }

    public BlogDto generateSummary(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found with id: " + id));

        String summary = aiService.generateSummary(blog.getContent());
        blog.setSummary(summary);
        Blog savedBlog = blogRepository.save(blog);

        return mapToDto(savedBlog);
    }

    private BlogDto mapToDto(Blog blog) {
        return BlogDto.builder()
                .id(blog.getId())
                .title(blog.getTitle())
                .content(blog.getContent())
                .author(blog.getAuthor())
                .createdAt(blog.getCreatedAt())
                .summary(blog.getSummary())
                .build();
    }
}