package learning.blog.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
Custom access denied handler class. We pass this class to spring security and set the proper error message
This is invoked when a user tries to access a protected URL.
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    private static Logger logger = LoggerFactory.getLogger(MyAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        /* Authentication object can be used to obtain the user who has currently logged in
        * We create a log statement for illegal access.
        * */

        if (auth != null) {
            logger.info(String.format("User '%s' attempted to access the protected URL: %s",
                    auth.getName(), httpServletRequest.getRequestURI()));
        }

        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/403");
    }
}
