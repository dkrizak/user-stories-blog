package hr.project.userstoriesblog.controller;

import hr.project.userstoriesblog.model.Blog;
import hr.project.userstoriesblog.model.Comment;
import hr.project.userstoriesblog.repository.BlogRepository;
import hr.project.userstoriesblog.service.BlogService;
import hr.project.userstoriesblog.service.CommentService;
import hr.project.userstoriesblog.service.SessionService;
import hr.project.userstoriesblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Controller
public class BlogController {

    private BlogService blogService;
    private CommentService commentService;
    private SessionService sessionService;
    UserService userService;

    @Autowired
    public BlogController(BlogService blogService, CommentService commentService, SessionService sessionService, UserService userService) {
        this.blogService = blogService;
        this.commentService = commentService;
        this.sessionService = sessionService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String showBlogs(Model model, HttpSession session){

        String user;

        if (!sessionService.loginCheck(session)) {
            model.addAttribute("isLoggedIn", false);
        } else {
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("role", session.getAttribute("role"));
            user = session.getAttribute("user").toString();
            model.addAttribute("user", user);
        }

        List<Blog> blogs = blogService.getAllBlogs();
        Collections.reverse(blogs);

        model.addAttribute("listOfBlogs", blogs);

        return "blogs/index";
    }

    @GetMapping("/addBlog")
    public String addBlog(Model model, HttpSession session){

        if (!sessionService.loginCheck(session)) {
            return "redirect:/";
        } else {
            if (!session.getAttribute("role").equals("ADMIN")) {
                return "redirect:/";
            }
        }

        model.addAttribute("blog", new Blog());

        return "blogs/addBlog";
    }

    @PostMapping("/saveBlog")
    public String saveBlog(@Valid @ModelAttribute Blog blog,
                               BindingResult bindingResult,
                                HttpSession session
                               ){

        if(bindingResult.hasErrors()){
            return "/blogs/addBlog";
        }

        blog.setUser(userService.getUserById(Long.parseLong(session.getAttribute("id").toString())));
        blog.setTimeOfPosting(Instant.now());
        blog.setTimeOfLastUpdate(Instant.now());

        blogService.saveBlog(blog);

        return "redirect:/";
    }

    @PostMapping("/blogs/editBlog")
    public String editBlog(@RequestParam long blogId, Model model){

        Blog blog = blogService.getBlogById(blogId);

        model.addAttribute("blog", blog);

        return "/blogs/editBlog";
    }

    @PostMapping("/saveEditedBlog")
    public String saveEditedBlog(@Valid @ModelAttribute Blog blog,
                           BindingResult bindingResult
    ){

        if(bindingResult.hasErrors()){
            return "/blogs/editBlog";
        }

        Blog editedBlog = blogService.getBlogById(blog.getId());

        editedBlog.setTimeOfLastUpdate(Instant.now());
        editedBlog.setTitle(blog.getTitle());
        editedBlog.setSummary(blog.getSummary());
        editedBlog.setText(blog.getText());

        blogService.saveBlog(editedBlog);

        String url = "redirect:/blogs/" + editedBlog.getTitle().replaceAll(" ", "%20") + "/" + editedBlog.getId();

        return url;
    }

    @PostMapping("/blogs/deleteBlog")
    public String deleteBlog(@RequestParam long blogId){

        List<Comment> comments = commentService.getAllComments();

        for (Comment comment : comments) {
            if (comment.getBlog().getId() == blogId) {
                commentService.deleteCommentById(comment.getId());
            }
        }

        blogService.deleteBlogById(blogId);

        return "redirect:/";
    }

    @GetMapping("/blogs/{title}/{id}")
    public String showBlog(@PathVariable(value = "id") long id, Model model, HttpSession session){

        Blog blog = blogService.getBlogById(id);

        model.addAttribute("blog", blog);
        model.addAttribute("listOfComments", commentService.getAllComments());
        model.addAttribute("comment", new Comment());

        if (!sessionService.loginCheck(session)) {
            model.addAttribute("isLoggedIn", false);
        } else {
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("role", session.getAttribute("role"));
            String user = session.getAttribute("user").toString();
            model.addAttribute("user", user);
            model.addAttribute("userId", session.getAttribute("id"));
        }

        return "/blogs/showBlog";
    }

    @PostMapping("/blogs/saveComment")
    public String saveComment(@Valid @ModelAttribute Comment comment, BindingResult bindingResult,
                              @RequestParam long blogId,
                              Model model,
                              HttpSession session){

        Blog blog = blogService.getBlogById(blogId);

        model.addAttribute("blog", blog);
        model.addAttribute("listOfComments", commentService.getAllComments());

        comment.setBlog(blog);
        model.addAttribute("comment", comment);

        if(bindingResult.hasErrors()){

            if (!sessionService.loginCheck(session)) {
                model.addAttribute("isLoggedIn", false);
            } else {
                model.addAttribute("isLoggedIn", true);
                model.addAttribute("role", session.getAttribute("role"));
                String user = session.getAttribute("user").toString();
                model.addAttribute("user", user);
                model.addAttribute("userId", session.getAttribute("id"));
            }

            return "/blogs/showBlogError";
        }

        Instant time = Instant.now();
        comment.setTimeOfPosting(time);
        comment.setTimeOfUpdate(time);
        comment.setUser(userService.getUserById(Long.parseLong(session.getAttribute("id").toString())));

        commentService.saveComment(comment);

        String url = "redirect:/blogs/" + blog.getTitle().replaceAll(" ", "%20") + "/" + blog.getId();

        return url;

    }

    @GetMapping("/blogs/editComment/{id}")
    public String showCommentForEdit (@PathVariable(value = "id") long id, Model model, HttpSession session){

        if(!sessionService.loginCheck(session)){
            return "redirect:/";
        }

        Comment comment = commentService.getCommentById(id);
        model.addAttribute("comment", comment);
        model.addAttribute("listOfAllComments", commentService.getAllComments());
        return "blogs/editComment";
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
        updatedComment.setTimeOfUpdate(Instant.now());

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

