package com.zzlin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author zlin
 * @date 20201105
 */
@ApiIgnore
@RestController
public class HelloController {

    private final static Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/hello")
    public Object hello() {
        LOGGER.info("Hello, info~");
        LOGGER.debug("Hello, debug~");
        LOGGER.warn("Hello, warn~");
        LOGGER.error("Hello, error~");
        return "Hello world";
    }

    private int uid = 0;
    @GetMapping("/session")
    public Object getSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object curUid = session.getAttribute("uid");
        if (curUid == null) {
            int setUid = ++uid;
            session.setAttribute("uid", setUid);
            session.setMaxInactiveInterval(60);
            return setUid;
        }
        return curUid;
    }

}
