package com.summerizer.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.summerizer.blog.model.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long> {
}
