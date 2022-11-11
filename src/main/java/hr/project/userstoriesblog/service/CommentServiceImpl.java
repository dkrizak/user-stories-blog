package hr.project.userstoriesblog.service;

import hr.project.userstoriesblog.model.Blog;
import hr.project.userstoriesblog.model.Comment;
import hr.project.userstoriesblog.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    private CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public void saveComment(Comment comment) {
        this.commentRepository.save(comment);
    }

    @Override
    public Comment getCommentById(long id) {

        Optional<Comment> optional = this.commentRepository.findById(id);
        Comment comment = null;

        if(optional.isPresent()){
            comment = optional.get();
        } else {
            throw  new RuntimeException("Comment with id " + id + " was not found");
        }

        return comment;
    }

    @Override
    public void deleteCommentById(long id) {
        if(this.commentRepository.existsById(id)){
            this.commentRepository.deleteById(id);
        } else {
            throw  new RuntimeException("Comment with id " + id + " was not found");
        }
    }
}
