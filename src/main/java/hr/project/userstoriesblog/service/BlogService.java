package hr.project.userstoriesblog.service;

import hr.project.userstoriesblog.model.Blog;

import java.util.List;

public interface BlogService {

    List<Blog> getAllBlogs();
    void saveBlog(Blog blog);
    Blog getBlogById(long id);
    void deleteBlogById(long id);
    
}
