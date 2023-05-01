package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @PostMapping
    public String save(@ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/edit")
    public String edit(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam Long userId, ModelMap model) {
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("user", userService.getById(userId));
        return "edit";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long userId) {
        userService.deleteById(userId);
        return "redirect:/admin";
    }

    @GetMapping("/new")
    public String createNew(Model model) {
        User user = new User();
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("user", user);
        return "newUser";
    }
}
