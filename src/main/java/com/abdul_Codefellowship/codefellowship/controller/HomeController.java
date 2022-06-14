package com.abdul_Codefellowship.codefellowship.controller;

import com.abdul_Codefellowship.codefellowship.model.AppUser;
import com.abdul_Codefellowship.codefellowship.model.Post;
import com.abdul_Codefellowship.codefellowship.model.Reply;
import com.abdul_Codefellowship.codefellowship.repositories.AppRepository;
import com.abdul_Codefellowship.codefellowship.repositories.PostRepository;
import com.abdul_Codefellowship.codefellowship.repositories.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    AppRepository appRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    ReplyRepository replyRepository;

    // CUSTOM 404 exception!!
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException {
        ResourceNotFoundException(String message) {
            super(message);
        }
    }

    @GetMapping("/")
    public String getHomePage(Principal p, Model m) {

        if (p != null) {
            String username = p.getName();
            AppUser appUser = appRepository.findByUsername(username);

            m.addAttribute("username", username);
        }

//        throw a 404 error,
        return "index";

    }

    @GetMapping("/myProfile")
    public String userProfile(Principal p, Model m) {

        if (p != null) {
            String username = p.getName();
            AppUser appUser = appRepository.findByUsername(username);



           List<Post> postList = appUser.getPostList();
            m.addAttribute("username", username);
            m.addAttribute("user", appUser);
            m.addAttribute("posts",postList);



        }
        return "profile";
    }


    @GetMapping("/user/{id}")
    public String userInfo(Principal p, Model m, @PathVariable Long id) {

        if (p != null) { // not strictly required if your WebSecuityConfig is correct
            String username = p.getName();
            AppUser appUser = appRepository.findByUsername(username);

            m.addAttribute("username", username);
            m.addAttribute("appUser",appUser);
        }

        AppUser appUser = appRepository.findById(id).orElseThrow(IllegalAccessError::new);
        boolean isFollowing = appUser.getFollowingSet().contains(appUser);

        m.addAttribute("appUsername",appUser.getUsername());
        m.addAttribute("appUserId",appUser.getId());
        m.addAttribute("user",appUser.getFirstName() + ' '+ appUser.getLastName()+ ' '+appUser.getBio());

        List<Post> postList = appUser.getPostList();

        m.addAttribute("post", postList);
        m.addAttribute("isFollowing", isFollowing);


        return "user-info";
    }


    @GetMapping("/allusers")
    public String getAllUsersPage(Principal p, Model m) {
        if (p != null) {
            AppUser appUser = appRepository.findByUsername(p.getName());
            m.addAttribute("applicationUser", appUser);




        }
        List<AppUser> allUsers = appRepository.findAll();
        m.addAttribute("allUsers", allUsers);
        return "all-users.html";
    }


    @GetMapping("/myfeed")
    public String getMyFeed(Principal p, Model m) {
        if (p != null) {
            AppUser appUser = appRepository.findByUsername(p.getName());

//            Post post = postRepository.findById(appUser.getId()).orElseThrow(IllegalAccessError::new);

//            List<Reply> replyList = post.getReplyList();
//            m.addAttribute("replies", replyList);

            m.addAttribute("applicationUser", appUser);
            m.addAttribute("appUserId",appUser.getId());
            m.addAttribute("appUserName",appUser.getFirstName() + ' '+ appUser.getLastName());
            List<Post> followingPosts = new ArrayList<>();
            for (AppUser user : appUser.getFollowingSet()) {
                List<Post> postList = user.getPostList();
                for ( Post post : postList) {
                    post.setReplyList(replyRepository.findAllByPost(post));
                }
                followingPosts.addAll(postList);
            }
            m.addAttribute("allUserPosts", followingPosts);
        }
        return "my-feed";
    }


    @PutMapping("/follow/{id}")
    RedirectView followUser(@PathVariable long id, Principal p, Model m) {
        if (p != null) {
            AppUser appUser = appRepository.findByUsername(p.getName());
            AppUser publicUser = appRepository.getById(id);
            if (appUser != null && publicUser != null) {
                m.addAttribute("applicationUser", appUser);
                appUser.getFollowingSet().add(publicUser);
                appRepository.save(appUser);
            }
        }
        return new RedirectView("/user/" + id);
    }

}





