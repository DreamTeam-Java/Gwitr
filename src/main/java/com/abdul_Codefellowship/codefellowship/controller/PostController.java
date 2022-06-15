package com.abdul_Codefellowship.codefellowship.controller;

import com.abdul_Codefellowship.codefellowship.model.AppUser;
import com.abdul_Codefellowship.codefellowship.model.Post;
import com.abdul_Codefellowship.codefellowship.model.Reply;
import com.abdul_Codefellowship.codefellowship.repositories.AppRepository;
import com.abdul_Codefellowship.codefellowship.repositories.PostRepository;
import com.abdul_Codefellowship.codefellowship.repositories.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class PostController {

    @Autowired
    PostRepository postRepository;
    @Autowired
    AppRepository appRepository;
    @Autowired
    ReplyRepository replyRepository;




    @PostMapping("/add-post")
    public RedirectView addPost (Principal p, Model m, String text) {
        if(p != null){
            String username = p.getName();
            AppUser appUser = appRepository.findByUsername(username);
            m.addAttribute("Appuser",appUser);

            Post post = new Post(text);
            post.setCreatedAt(new Date());
            post.setPostAuthor(appUser);
            postRepository.save(post);



        }
        return new RedirectView("/myProfile");
    }

    @PostMapping("/add-Reply")
    public RedirectView addReply (Principal p, Model m, String reply, Long id) {
        if(p != null){
            String username = p.getName();
            Post userPost = postRepository.getById(id);
            m.addAttribute("Appuser",userPost);

            Reply replyPost = new Reply(reply);
            replyPost.setReplyDate(new Date());
            replyPost.setPost(userPost);
            replyRepository.save(replyPost);




        }
        return new RedirectView("/myfeed");
    }
    @DeleteMapping ("/deletePost/{id}")
    public RedirectView deletePost(@PathVariable Long id){
        Post postDelete = postRepository.findById(id).orElseThrow(IllegalAccessError::new);

        postRepository.delete(postDelete);


        return new RedirectView("/myProfile");
    }


}
