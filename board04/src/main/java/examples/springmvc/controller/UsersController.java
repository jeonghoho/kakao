package examples.springmvc.controller;

import examples.springmvc.dto.User;
import examples.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(path = "/users")
public class UsersController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String getUsers(ModelMap model){
        model.addAttribute("list", userService.getUsers());
        return "users/list";
    }

    @GetMapping("/loginform")
    public String loginform(){
        return "users/loginform";
    }

    @PostMapping("/login")
    public String login(@RequestParam(name = "userId") String userId, @RequestParam(name = "password") String password, HttpSession session){

        
        User user = userService.getUser(userId);
        if(user != null && user.getPassword().equals(password)){
            user.setPassword(""); // 세션에 저장하기 전에 암호를 삭제한다.
            session.setAttribute("login", user); // 세션에 user정보를 저장한다.
            return "redirect:/boards";
        }else{
            return "redirect:/users/loginerror";
        }
    }

    @GetMapping("/loginerror")
    public String loginerror(){
        return "users/loginerror";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("login"); // 세션에서 삭제한다.

        return "redirect:/boards";
    }

    @GetMapping("/joinform")
    public String joinform(){
        return "users/joinform";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute User user){
        userService.addUser(user);
        return "redirect:/users/welcome";
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "users/welcome";
    }
}
