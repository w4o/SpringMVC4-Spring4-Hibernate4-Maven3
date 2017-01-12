package com.ueedit.common.freemarker;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

public class ContextPathFreemarkerView extends FreeMarkerView {

    /**
     * 全局内容路径
     */
    private static final String CONTENT_BASE_PATH = "_base";

    /**
     * 全局静态资源路径
     */
    private static final String CONTENT_RESOURCE_PATH = "_res";

    @Override
    protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) throws Exception {


        int port = request.getServerPort();
        String root;
        if (port == 80) {
            root = "//" + request.getServerName() + request.getContextPath();
        } else {
            root = "//" + request.getServerName() + ":" + port + request.getContextPath();
        }

        model.put(CONTENT_BASE_PATH, root);
        model.put(CONTENT_RESOURCE_PATH, root + "/static");
    }

}
