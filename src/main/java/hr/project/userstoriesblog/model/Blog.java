package hr.project.userstoriesblog.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
}
