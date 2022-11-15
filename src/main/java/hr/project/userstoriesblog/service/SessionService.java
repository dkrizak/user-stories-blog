package hr.project.userstoriesblog.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class SessionService {

    public boolean loginCheck(HttpSession session){
        if (session.getAttribute("user") == null) {
            return false;
        } else {
            return true;
        }
    }
}
