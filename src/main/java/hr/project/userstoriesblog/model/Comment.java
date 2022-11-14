package hr.project.userstoriesblog.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTimeOfPosting() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm")
                .withZone(ZoneId.systemDefault());

        return formatter.format(timeOfPosting);
    }

    public Instant getOriginalTime() {
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
