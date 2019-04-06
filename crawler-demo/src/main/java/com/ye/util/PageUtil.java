package com.ye.util;

/**
 * 翻页工具类
 */
public class PageUtil {

    private static String pageCurrentPage = "{pageCurrent}";
    private static final String DISABLE = " disabled";
    private static final String LI = "<li class=\"footable-page-arrow";
    private static final String A = "\"><a href=\"";

    private PageUtil() {

    }

    /**
     * 翻页处理 列表的翻页处理，通过自己拼接标签，将拼接的翻页标签放到model中，返回给页面。页面通过model拿到翻页结果并进行展示。
     * 
     * @param url
     * @param pageCurrent
     * @param pageSize
     * @param pageCount
     * @return
     */
    public static String getPageContent(String url, int pageCurrent, int pageSize, int pageCount) {
        if (pageCount == 0) {
            return "";
        }
        String urlNew = url.replace("{pageSize}", pageSize + "").replace("{pageCount}", pageCount + "");

        String first = urlNew.replace(pageCurrentPage, 1 + "");
        String prev = urlNew.replace(pageCurrentPage, (pageCurrent - 1) + "");
        String next = urlNew.replace(pageCurrentPage, (pageCurrent + 1) + "");
        String last = urlNew.replace(pageCurrentPage, pageCount + "");

        StringBuilder html = new StringBuilder();
        html.append(LI + (pageCurrent <= 1 ? DISABLE : "") + A + (pageCurrent <= 1 ? "#" : first) + "\">«</a></li>");
        html.append(LI + (pageCurrent <= 1 ? DISABLE : "") + A + (pageCurrent <= 1 ? "#" : prev) + "\">‹</a></li>");

        html.append("<li class=\"footable-page" + (((pageCurrent) == pageCurrent) ? " active" : "") + A + "#"
                + "\" disabled=\"disabled\" \">" + (pageCurrent) + "</a></li>");

        html.append(LI + (pageCurrent == pageCount ? DISABLE : "") + A + (pageCurrent == pageCount ? "#" : next)
                + "\">›</a></li>");
        html.append(LI + (pageCurrent == pageCount ? DISABLE : "") + A + (pageCurrent == pageCount ? "#" : last)
                + "\">»</a></li>");
        return html.toString().replaceAll("null", "");
    }

}