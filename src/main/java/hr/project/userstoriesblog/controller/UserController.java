package hr.project.userstoriesblog.controller;

import hr.project.userstoriesblog.model.User;
import hr.project.userstoriesblog.service.SessionService;
import hr.project.userstoriesblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    SessionService sessionService;

    @GetMapping("/register")
    public String registerUser(Model model, HttpSession session){

        if (sessionService.loginCheck(session)) {
            return "redirect:/";
        }

        model.addAttribute("user", new User());

        return "user/register";
    }

    @PostMapping("/saveuser")
    public String saveUser(@Valid @ModelAttribute User user, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "/user/register";
        }

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRole("USER");

        userService.saveUser(user);

        return "user/login";
    }

    @GetMapping("/login")
    public String loginForm(Model model, HttpSession session){

        if (sessionService.loginCheck(session)) {
            return "redirect:/";
        }

        model.addAttribute("error", false);

        return "user/login";

    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model,
                        HttpServletRequest request){

        User user = userService.getUserByUserName(username);

        if (user == null) {
            System.out.println("wrong name");
            model.addAttribute("error", true);
            return "user/login";
        }

        if (new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            request.getSession().setAttribute("user", user.getUsername());
            request.getSession().setAttribute("role", user.getRole());
            request.getSession().setAttribute("id", user.getId());
            request.getSession().setAttribute("firstName", user.getFirstName());
            request.getSession().setAttribute("lastName", user.getLastName());

            return "redirect:/";
        }


        model.addAttribute("error", true);
        return "user/login";

    }

    @PostMapping("/users")
    public String editRoles(Model model, HttpSession session) {

        List<User> users = userService.getAllUsers();

        model.addAttribute("users", users);
        model.addAttribute("user", session.getAttribute("user"));

        return "/user/editRoles";
    }

    @GetMapping("/user/showUpdateForm/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model, HttpSession session){

        if (!sessionService.loginCheck(session)) {
            return "redirect:/";
        } else {
            if (!session.getAttribute("role").equals("ADMIN")) {
                return "redirect:/";
            }
        }


        User updatedUser = userService.getUserById(id);
        model.addAttribute("updateUser", updatedUser);
        model.addAttribute("user", session.getAttribute("user"));

        return "user/updateRole";


    }

    @PostMapping("/user/saveRole")
    public String saveEdit(@ModelAttribute User updateUser, HttpSession session){

        User user = userService.getUserById(updateUser.getId());
        user.setRole(updateUser.getRole());

        userService.saveUser(user);

        if (session.getAttribute("user").equals(user.getUsername())){
            session.setAttribute("role", user.getRole());
        }

        String url = "redirect:/user/showUpdateForm/" + user.getId();

        return url;
    }
}
