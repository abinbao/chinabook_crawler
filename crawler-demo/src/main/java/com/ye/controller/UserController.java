package com.ye.controller;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.ye.constant.R;
import com.ye.dao.ChinaBookDao;
import com.ye.dao.UserDao;
import com.ye.dao.UserRoleRelationDao;
import com.ye.model.User;

/**
 * 用户模块
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static final String TOKEN = "username";
    private static final String LOGIN = "login";

    @Autowired
    UserDao userDao;
    @Autowired
    UserRoleRelationDao userRoleRelationDao;
    @Autowired
    ChinaBookDao chinaBookDao;

    @RequestMapping("/healthCheck")
    @ResponseBody
    public String healthCheck() {
        return "success";
    }

    @RequestMapping("/login")
    public String login() {
        return LOGIN;
    }

    @RequestMapping("/loginout")
    public String loginout(HttpSession session) {
        session.removeAttribute(TOKEN);
        return LOGIN;
    }

    /**
     * 概览大屏
     * 
     * @param model
     * @param session
     * @return
     */
    @GetMapping("/dashboard")
    public ModelAndView dashboard(Model model, HttpSession session) {
        if (session.getAttribute(TOKEN) == null)
            return new ModelAndView(LOGIN);
        return new ModelAndView("dashboard");
    }

    /**
     * 用户登录
     * 
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(User user, Model model, HttpSession session) {
        String msg = "";
        if (StringUtils.isBlank(user.getUsername())) {
            msg = "请输入用户名";
        }
        if (StringUtils.isBlank(user.getPassword())) {
            msg = "请输入密码";
        }
        User uu = userDao.queryUserByUsername(user.getUsername());
        if (null == uu) {
            msg = "用户不存在";
        } else {
            if (!user.getPassword().equals(uu.getPassword()))
                msg = "用户密码错误";
            else
                msg = "登录成功";
        }
        if (msg.equals("登录成功")) {
            session.setAttribute(TOKEN, uu.getUsername());
            return "redirect:/user/dashboard";
        } else {
            model.addAttribute("error", msg);
            return LOGIN;
        }
    }

    /**
     * 用户注册
     * 
     * @param user
     * @return
     */
    @GetMapping(value = "/register")
    public String register() {
        return "register";
    }

    @PostMapping(value = "/registerUser")
    public String registerUser(User user, Model model) {
        User uu = userDao.queryUserByUsername(user.getUsername());
        String msg = "注册成功。";
        if (null == uu) {
            userDao.insertUser(user);
            User u1 = userDao.queryUserByUsername(user.getUsername());
            userRoleRelationDao.insert(String.valueOf(u1.getId()));
            model.addAttribute("alert", msg);
            return LOGIN;
        }
        msg = "用户名已存在";
        model.addAttribute("alert", msg);
        return "register";
    }

    /**
     * 查看普通用户信息
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/user_list", method = RequestMethod.GET)
    public String queryUserAll(Model model, HttpSession session) {
        if (session.getAttribute(TOKEN) == null)
            return LOGIN;
        List<User> users = userDao.queryAll();
        model.addAttribute("users", users);
        return "users/user_list";
    }

    /**
     * 删除用户
     * 
     * @param user
     * @return
     */
    @ResponseBody
    @PostMapping("/user_delete")
    public R delPatient(User user, HttpSession session) {
        if ("admin".equals(session.getAttribute(TOKEN))) {
            userDao.deleteById(user);
            return R.ok();
        }
        return R.error(401, "非法请求");
    }

    /**
     * 编辑用户信息
     * 
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/user_edit_{id}")
    public ModelAndView editUser(User user, @PathVariable("id") int id, Model model, HttpSession session) {
        if (session.getAttribute(TOKEN) == null)
            return new ModelAndView(LOGIN);
        User p = userDao.findById(user);
        model.addAttribute("user", p);
        return new ModelAndView("users/user_edit");
    }

    /**
     * 个人用户信息
     * 
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/user_info_{username}")
    public ModelAndView userInfo(User user, @PathVariable("username") String username, Model model,
            HttpSession session) {
        if (session.getAttribute(TOKEN) == null)
            return new ModelAndView(LOGIN);
        User p = userDao.findByUserName(username);
        model.addAttribute("user", p);
        return new ModelAndView("users/user_edit");
    }

    /**
     * 更新用户信息
     * 
     * @param patient
     * @param model
     * @param httpSession
     * @return
     */
    @RequestMapping("/save_user")
    public ModelAndView savePatient(User user, RedirectAttributesModelMap model, HttpSession httpSession) {
        if (httpSession.getAttribute(TOKEN) == null)
            return new ModelAndView(LOGIN);
        User p = userDao.findById(user);
        if (p == null) {
            Date createtime = new Date();
            user.setCreateTime(createtime);
            userDao.insertUser(user);
            User uu = userDao.queryUserByUsername(user.getUsername());
            userRoleRelationDao.insert(String.valueOf(uu.getId()));
            return new ModelAndView("redirect:/user/login");
        } else {

            // 校验密码
            if (StringUtils.isBlank(user.getPassword())) {
                model.addFlashAttribute("passwordError", "密码不能为空");
                return new ModelAndView("redirect:/user/user_info_" + user.getUsername());
            }
            // 校验手机号
            if (StringUtils.isNotBlank(user.getPhone())) {
                String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
                Pattern pattern = Pattern.compile(regex);
                Matcher m = pattern.matcher(user.getPhone());
                if (!m.find()) {
                    model.addFlashAttribute("phoneError", "请输入正确的手机号");
                    return new ModelAndView("redirect:/user/user_info_" + user.getUsername());
                }
            }
            // 校验年龄
            if (StringUtils.isNotBlank(user.getAge())) {
                String regex = "^\\d+$";
                Pattern pattern = Pattern.compile(regex);
                Matcher m = pattern.matcher(user.getAge());
                if (!m.find()) {
                    model.addFlashAttribute("ageError", "请输入正确的年龄");
                    return new ModelAndView("redirect:/user/user_info_" + user.getUsername());
                }
            }
            // 校验邮箱
            if (StringUtils.isNotBlank(user.getEmail())) {
                String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
                Pattern pp;
                Matcher m;
                pp = Pattern.compile(regEx1);
                m = pp.matcher(user.getEmail());

                if (!m.matches()) {
                    model.addFlashAttribute("emailError", "请输入正确的邮箱");
                    return new ModelAndView("redirect:/user/user_info_" + user.getUsername());
                }
            }
            userDao.updateAll(user);
            if (httpSession.getAttribute(TOKEN).equals(user.getUsername())) {
                return new ModelAndView("redirect:/user/user_info_" + user.getUsername());
            }
            return new ModelAndView("redirect:/user/user_list");
        }
    }
}
