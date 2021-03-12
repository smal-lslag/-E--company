package com.xxxx.server.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**权限控制
 * 判断用户角色
 */
@Component
public class CustomUrlDecisionManager implements AccessDecisionManager {


    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {

        for (ConfigAttribute configAttribute : configAttributes) {
//            当前Url所需要的的角色
            String neeRole = configAttribute.getAttribute();
//            判断所需要的角色是否一致 是否登陆 此角色在CustomFilter中设置
            if("ROLE_LOGIN".equals(neeRole)){
//                一致判断是否登陆
                if (authentication instanceof AnonymousAuthenticationToken){
                    throw new AccessDeniedException("您未登录,请登陆");
                }else{
                    return;
                }
            }
//            判断用户角色 是否为URl所需角色
            Collection<? extends GrantedAuthority> authorities =  authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(neeRole)){
                    return;
                }
            }
        }
        throw  new AccessDeniedException("权限不足,请核对");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
