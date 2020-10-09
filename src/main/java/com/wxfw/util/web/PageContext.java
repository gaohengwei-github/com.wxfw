package com.wxfw.util.web;

/**
 * PageContext
 * 分页上下文
 *
 * @author gaohw
 * @date 2020/3/21
 */
public class PageContext {
    private static ThreadLocal<Long> currentPageHolder = new ThreadLocal<Long>();
    private static ThreadLocal<Long> pageSizeHolder = new ThreadLocal<Long>();
    private static ThreadLocal<Long> dataCountHolder = new ThreadLocal<Long>();

    public static Long getPageSize() {
        return pageSizeHolder.get();
    }

    public static void setPageSize(Long ps) {
        pageSizeHolder.set(ps);
    }

    public static Long getStart() {
        Long currentPage = currentPageHolder.get();
        if (currentPage == null) {
            currentPage = 1L;
        }
        Long pageSize = pageSizeHolder.get();
        if (pageSize == null) {
            pageSize = 10L;
        }
        return (currentPage - 1) * pageSize;
    }

    public static Long getCurrentPage() {
        Long currentPage = currentPageHolder.get();
        if (currentPage == null) {
            currentPage = 1L;
        }
        return currentPage;
    }

    public static void setCurrentPage(Long cp) {
        currentPageHolder.set(cp);
    }

    public static Long getDataCount() {
        return dataCountHolder.get();
    }

    public static void setDataCount(Long count) {
        dataCountHolder.set(count);
    }

    public static void clear() {
        currentPageHolder.remove();
        pageSizeHolder.remove();
        dataCountHolder.remove();
    }

}
