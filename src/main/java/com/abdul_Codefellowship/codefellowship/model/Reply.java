package com.abdul_Codefellowship.codefellowship.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Reply {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

     String reply;
   Date replyDate;

    @ManyToOne
    Post post;

    public Reply() {
    }

    public Reply(String reply) {
        this.reply = reply;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
