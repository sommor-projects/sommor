package com.sommor.core.context;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/29
 */
public class RequestContext extends Context {

    private static final String KEY = "__SOMMOR_REQUEST_CONTEXT__";

    public static RequestContext get() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (null != requestAttributes) {
            RequestContext ctx = (RequestContext) requestAttributes.getAttribute(KEY, SCOPE_REQUEST);
            if (null == ctx) {
                ctx = new RequestContext();
                requestAttributes.setAttribute(KEY, ctx, SCOPE_REQUEST);
            }

            return ctx;
        }
        throw new RuntimeException("invalid request");
    }

    public static SubContext getSubContext(Object key) {
        return getSubContext(String.valueOf(key.hashCode()));
    }

    public static SubContext getSubContext(String key) {
        RequestContext requestContext = get();
        SubContext context = requestContext.getExt(key);
        if (null == context) {
            context = new SubContext(requestContext);
            requestContext.addExt(key, context);
        }
        return context;
    }
}
