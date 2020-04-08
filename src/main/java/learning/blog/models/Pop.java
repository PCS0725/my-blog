package learning.blog.models;

import org.hibernate.search.annotations.Field;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
public class Pop {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pop_id")
    private Long id;

    @Column(name = "title", nullable = false)
    //@Length is a hibernate constraint and it does not set the bindingResult fields property
    @NotEmpty(message = "*Please provide title")
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "author",nullable = false)
    @NotEmpty(message = "Mention the author")
    private String author;

    @Column(name = "read_time")
    private String readTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReadTime() {
        return readTime;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }

}
