package com.abdul_Codefellowship.codefellowship.model;

import javax.persistence.*;
import java.io.File;
import java.util.List;

@Entity
public class Resume {

    @Id @GeneratedValue(strategy = GenerationType.AUTO) private Long id;

    private File resume;


    @OneToMany(mappedBy = "resumeReview", cascade = CascadeType.ALL)
    @OrderBy("text")
    List<Post> reviewList;

    @ManyToOne
    AppUser userResume;




    public Resume() {
    }

    public Resume(File resume) {
        this.resume = resume;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public File getResume() {
        return resume;
    }

    public void setResume(File resume) {
        this.resume = resume;
    }

    public List<Post> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Post> reviewList) {
        this.reviewList = reviewList;
    }

    public AppUser getUserResume() {
        return userResume;
    }

    public void setUserResume(AppUser userResume) {
        this.userResume = userResume;
    }
}
