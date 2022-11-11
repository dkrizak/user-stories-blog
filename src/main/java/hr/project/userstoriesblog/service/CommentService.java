package hr.project.userstoriesblog.service;

import hr.project.userstoriesblog.model.Blog;
import hr.project.userstoriesblog.model.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getAllComments();
    void saveComment(Comment comment);
    Comment getCommentById(long id);
    void deleteCommentById(long id);
}
