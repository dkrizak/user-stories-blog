package hr.project.userstoriesblog.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Comment can't be empty.")
    @Column(name = "comment_text")
    @NotNull
    private String text;

    @Column(name = "time_of_posting")
    private Instant timeOfPosting;

    @ManyToOne(optional = false)
    @JoinColumn(name = "Blog_id", nullable = false)
    private Blog blog;

    public Comment(){}

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Instant getTimeOfPosting() {
        return timeOfPosting;
    }

    public void setTimeOfPosting(Instant timeOfPosting) {
        this.timeOfPosting = timeOfPosting;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }
}
