package hr.project.userstoriesblog.controller;

import hr.project.userstoriesblog.model.Blog;
import hr.project.userstoriesblog.model.Comment;
import hr.project.userstoriesblog.repository.CommentRepository;
import hr.project.userstoriesblog.service.BlogService;
import hr.project.userstoriesblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;

@Controller
public class BlogController {

    private BlogService blogService;
    private CommentService commentService;
    @Autowired
    public BlogController(BlogService blogService, CommentService commentService) {
        this.blogService = blogService;
        this.commentService = commentService;
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
        model.addAttribute("listOfComments", commentService.getAllComments());
        model.addAttribute("comment", new Comment());

        return "/blogs/showBlog";
    }

    @PostMapping("/blogs/saveComment")
    public String saveComment(@Valid @ModelAttribute Comment comment, BindingResult bindingResult,
                              @RequestParam long blogId,
                              Model model){

        Blog blog = blogService.getBlogById(blogId);

        model.addAttribute("blog", blog);
        model.addAttribute("listOfComments", commentService.getAllComments());

        comment.setBlog(blog);
        model.addAttribute("comment", comment);

        if(bindingResult.hasErrors()){
            return "/blogs/showBlogError";
        }

        Instant time = Instant.now();
        comment.setTimeOfPosting(time);

        commentService.saveComment(comment);

        String url = "redirect:/blogs/" + blog.getTitle().replaceAll(" ", "%20") + "/" + blog.getId();

        return url;

    }

    @GetMapping("/blogs/editComment/{id}")
    public String showCommentForEdit (@PathVariable(value = "id") long id, Model model){
        Comment comment = commentService.getCommentById(id);
        model.addAttribute("comment", comment);
        model.addAttribute("listOfAllComments", commentService.getAllComments());
        return "blogs/editComment";
    }
    @GetMapping("/showCommentForDelete/{id}")
    public String showCommentForDelete (@PathVariable(value = "id") long id){
        commentService.deleteCommentById(id);
        return "redirect:/comments";
    }

    @PostMapping("/blogs/saveEditedComment")
    public String saveEditedComment(@Valid @ModelAttribute Comment comment,
                                    BindingResult bindingResult,
                                    @RequestParam long blogId,
                                    Model model) {

        Blog blog = blogService.getBlogById(blogId);

        if(bindingResult.hasErrors()){
            model.addAttribute("comment", comment);
            model.addAttribute("blog", blog);
            return "/blogs/showEditError";
        }



        Comment updatedComment = commentService.getCommentById(comment.getId());
        updatedComment.setText(comment.getText());

        commentService.saveComment(updatedComment);
        String url = "redirect:/blogs/" + blog.getTitle().replaceAll(" ", "%20") + "/" + blog.getId();

        return url;
    }

    @GetMapping("/blogs/deleteComment/{id}")
    public String deleteComment (@PathVariable(value = "id") long id, Model model){

        Blog blog = commentService.getCommentById(id).getBlog();

        commentService.deleteCommentById(id);

        String url = "redirect:/blogs/" + blog.getTitle().replaceAll(" ", "%20") + "/" + blog.getId();
        return url;
    }
}

