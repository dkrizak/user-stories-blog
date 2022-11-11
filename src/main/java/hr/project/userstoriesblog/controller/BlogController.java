package hr.project.userstoriesblog.controller;

import hr.project.userstoriesblog.model.Blog;
import hr.project.userstoriesblog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class BlogController {

    private BlogService blogService;
    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/")
    public String showBlogs(Model model){
        model.addAttribute("listOfBlogs", blogService.getAllBlogs());

        return "blogs/index";
    }

    @GetMapping("/addBlog")
    public String addBlog(Model model){
        model.addAttribute("blog", new Blog());

        return "blogs/addBlog";
    }

    @PostMapping("/saveBlog")
    public String saveBlog(@Valid @ModelAttribute Blog blog,
                               BindingResult bindingResult
                               ){

        if(bindingResult.hasErrors()){
            return "/blogs/addBlog";
        }

        blogService.saveBlog(blog);

        return "redirect:/";
    }

    @GetMapping("/blogs/{title}/{id}")
    public String showBlog(@PathVariable(value = "id") long id, Model model){

        Blog blog = blogService.getBlogById(id);

        model.addAttribute("blog", blog);

        return "/blogs/showBlog";
    }
}
