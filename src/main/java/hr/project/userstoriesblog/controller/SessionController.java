package hr.project.userstoriesblog.controller;

import hr.project.userstoriesblog.model.User;
import org.apache.catalina.users.SparseUserDatabase;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SessionController {

    @PostMapping("/startSession")
    public String startSession(@RequestParam User user, HttpServletRequest request){
        request.getSession().setAttribute("user", user.getUsername());

        return "redirect:/";
    }

    @PostMapping("/invalidate/session")
    public String destroySession(HttpServletRequest request) throws ServletException {
        //invalidate the session , this will clear the data from configured database (Mysql/redis/hazelcast)
        request.getSession().invalidate();
        return "redirect:/";
    }
}
