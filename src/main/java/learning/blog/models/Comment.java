package learning.blog.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "content", columnDefinition = "TEXT")
    @Length(max = 2000, message = "Keep your comment between 2 to 200 characters")
    @NotEmpty(message = "Please post something or return back")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User commentor;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", referencedColumnName = "article_id")
    @NotNull
    private Article article;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getCommentor() {
        return commentor;
    }

    public void setCommentor(User commentor) {
        this.commentor = commentor;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

}
