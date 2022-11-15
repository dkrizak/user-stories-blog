package hr.project.userstoriesblog.service;

import hr.project.userstoriesblog.model.Blog;
import hr.project.userstoriesblog.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService{

    private BlogRepository blogRepository;

    @Autowired
    public BlogServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    @Override
    public void saveBlog(Blog blog) {
        this.blogRepository.save(blog);
    }

    @Override
    public Blog getBlogById(long id) {

        Optional<Blog> optional = this.blogRepository.findById(id);
        Blog blog = null;

        if(optional.isPresent()){
            blog = optional.get();
        } else {
            throw  new RuntimeException("Blog with id " + id + " was not found");
        }

        return blog;
    }

    @Override
    public void deleteBlogById(long id) {

        if(this.blogRepository.existsById(id)){
            this.blogRepository.deleteById(id);
        } else {
            throw  new RuntimeException("Blog with id " + id + " was not found");
        }

    }

}
