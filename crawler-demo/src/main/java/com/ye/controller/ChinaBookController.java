package com.ye.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ye.constant.Constant;
import com.ye.constant.R;
import com.ye.dao.ChinaBookDao;
import com.ye.model.ChinaBookModel;
import com.ye.util.PageUtil;

/**
 * 从数据库中读取获取的 中国图书网信息
 */
@Controller
@RequestMapping("/chinabook")
public class ChinaBookController {

    private static final String LOGIN = "login";
    private static final String TOKEN = "username";

    @Autowired
    ChinaBookDao chinaBookDao;

    /**
     * 返回中国图书网信息列表页面
     * 
     * @param session
     * @return
     */
    @RequestMapping("/chinabook_list")
    public String chinaBookList(HttpSession session) {
        if (session.getAttribute(TOKEN) == null)
            return LOGIN;
        return "chinabook/chinabook_list";
    }

    /**
     * 查看中国图书网详细信息
     * 
     * @param session
     * @param id
     * @return
     */
    @RequestMapping("/chinabook_info_{id}")
    public String chinaBookInfo(HttpSession session, Model model, @PathVariable Integer id) {
        if (session.getAttribute(TOKEN) == null)
            return LOGIN;
        // 根据id查询图书信息
        ChinaBookModel book = chinaBookDao.queryById(id);
        model.addAttribute("book", book);
        return "chinabook/chinabook_info";
    }

    // 翻页查询
    @RequestMapping("/chinabook_list_{pageCurrent}_{pageSize}_{pageCount}")
    public String getChainBookList(ChinaBookModel chainBook, @PathVariable Integer pageCurrent,
            @PathVariable Integer pageSize, @PathVariable Integer pageCount, Model model, HttpSession session) {
        if (session.getAttribute(TOKEN) == null)
            return LOGIN;
        // 判断
        if (pageSize == 0)
            pageSize = 10;
        if (pageCurrent == 0)
            pageCurrent = 1;
        int rows = chinaBookDao.count(chainBook);
        if (pageCount == 0)
            pageCount = rows % pageSize == 0 ? (rows / pageSize) : (rows / pageSize) + 1;

        // 查询
        chainBook.setStart((pageCurrent - 1) * pageSize);
        chainBook.setEnd(pageSize);
        if (chainBook.getOrderBy() == null) {
            chainBook.setOrderBy(Constant.OrderByAddDateDesc);
        }
        List<ChinaBookModel> chinaBookList = chinaBookDao.list(chainBook);

        model.addAttribute("books", chinaBookList);
        String pageHTML = PageUtil.getPageContent("chinabook_list_{pageCurrent}_{pageSize}_{pageCount}", pageCurrent,
                pageSize, pageCount);
        model.addAttribute("pageHTML", pageHTML);
        model.addAttribute("chinabook", chainBook);
        return "chinabook/chinabook_list";
    }

    /**
     * 按类别1 统计数量
     * 
     * @return
     */
    @RequestMapping("/category1group")
    @ResponseBody
    public R getCountByCate1() {
        return R.ok().put("result", chinaBookDao.getCountByCategory1());
    }

    /**
     * 按类别1 统计数量
     * 
     * @return
     */
    @RequestMapping("/category2group")
    @ResponseBody
    public R getCountByCate2() {
        return R.ok().put("result", chinaBookDao.getCountByCategory2());
    }

    /**
     * 售价排名前十
     * 
     * @return
     */
    @RequestMapping("/rankbysellprice")
    @ResponseBody
    public R getRankByPrice() {
        return R.ok().put("result", chinaBookDao.getRankBySellPrice());
    }

    /**
     * 折扣排名前十
     * 
     * @return
     */
    @RequestMapping("/rankbydiscount")
    @ResponseBody
    public R getRankByDiscount() {
        return R.ok().put("result", chinaBookDao.getRankByDiscount());
    }

    /**
     * 删除用户
     * 
     * @param user
     * @return
     */
    @ResponseBody
    @PostMapping("/book_delete")
    public R delChinabook(ChinaBookModel model, HttpSession session) {
        if ("admin".equals(session.getAttribute(TOKEN))) {
            chinaBookDao.deleteById(model);
            return R.ok();
        }
        return R.error(401, "非法请求");
    }

}
