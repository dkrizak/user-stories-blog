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

    @Column(name = "time_of_last_update")
    private Instant timeOfUpdate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "Blog_id", nullable = false)
    private Blog blog;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

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

    public void setTimeOfPosting(Instant timeOfPosting) {
        this.timeOfPosting = timeOfPosting;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public String getTimeOfUpdate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm")
                .withZone(ZoneId.systemDefault());

        return formatter.format(timeOfUpdate);
    }

    public void setTimeOfUpdate(Instant timeOfUpdate) {
        this.timeOfUpdate = timeOfUpdate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
