package hr.project.userstoriesblog.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Table(name = "blogs")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Title can't be empty.")
    @Column(name = "blog_title")
    @NotNull
    private String title;

    @NotEmpty(message = "Blog text can't be empty.")
    @Column(name = "blog_text")
    @NotNull
    private String text;

    @NotEmpty(message = "Blog Summary can't be empty.")
    @Column(name = "blog_summary")
    @NotNull
    private String summary;

    @Column(name = "time_of_publishing")
    private Instant timeOfPosting;

    @Column(name = "time_of_last_update")
    private Instant timeOfLastUpdate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Blog(){
    }

    public long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTimeOfPosting() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm")
                .withZone(ZoneId.systemDefault());

        return formatter.format(timeOfPosting);
    }

    public void setTimeOfPosting(Instant timeOfPosting) {
        this.timeOfPosting = timeOfPosting;
    }

    public String getTimeOfLastUpdate() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm")
                .withZone(ZoneId.systemDefault());

        return formatter.format(timeOfLastUpdate);
    }

    public void setTimeOfLastUpdate(Instant timeOfLastUpdate) {
        this.timeOfLastUpdate = timeOfLastUpdate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
