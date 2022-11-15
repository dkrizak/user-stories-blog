package hr.project.userstoriesblog.service;

import hr.project.userstoriesblog.model.Blog;
import hr.project.userstoriesblog.model.Comment;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogService {

    List<Blog> getAllBlogs();
    void saveBlog(Blog blog);
    Blog getBlogById(long id);
    void deleteBlogById(long id);

}
