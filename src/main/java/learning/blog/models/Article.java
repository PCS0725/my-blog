package learning.blog.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

//Indexed tells hibernate search to include this entity in indexing
//A separate index base is created for each entity.
@Indexed
@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "article_id")
    private Long id;

    //Length, NotEmpty are validation constraints. They are reported by hibernate before persisting. We can handle them
    // using bindingResults object.
    @Field
    @Column(name = "title", nullable = false)
    //@Length is a hibernate constraint and it does not set the bindingResult fields property
    @NotEmpty(message = "*Please provide title")
    private String title;

    @Field
    @Column(name = "summary", columnDefinition = "TEXT")
    private String summary;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Field
    @Column(name = "author",nullable = false)
    @NotEmpty(message = "Mention the author")
    private String author;

    @Field
    @Column(name = "category", nullable = false)
    @NotEmpty(message = "Please specify a category")
    private String category;

    @Column(name = "read_time")
    private String readTime;

    @Column(name = "image_url")
    private String imageURL;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createDate;


    //Here, in this bidirectional relation, comment is the owner entity and article is referencing side.
    //value of mappedBy is the name of property in owner side
    //On owner side, @JoinColumn specifies the foreign key. The side which owns the foreign key is owner side.
   // Usually, the 'many' side is the owner side.
    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Comment> comments;


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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getReadTime() {
        return readTime;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
