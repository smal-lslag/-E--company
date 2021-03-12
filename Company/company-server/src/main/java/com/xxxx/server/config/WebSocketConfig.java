package com.xxxx.server.config;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * websocket 配置类
 * 实现网页实时聊天
 */

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 添加Endpoint  这样在网页可以通过wesocket 连上服务
     * 配置websocket 服务地址  且前端可以通过socketJS连接
     *
     * @param registry
     */
    @Override
    public  void registerStompEndpoints(StompEndpointRegistry registry){

//        将ws/ep注册为stomp端点  用户连接这个端点就可以进行websocket通信 支持socketJS
//        setAllowedOrigins：允许跨域
//        withSockJS:支持socketJS连接
        registry.addEndpoint("/ws/ep").setAllowedOrigins("*").withSockJS();

    }


    /**
     * 由于此项目用的JWT令牌  所以此处需要配置
     * @param registration
     *
     * 配置输入通道参数配置
     */
    @Override
    public  void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//                判断是否是连接
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//                    Auth-Token客户端  发送过来的额
                    String token = accessor.getFirstNativeHeader("Auth-Token");
                    if (!StringUtils.isEmpty(token)) {
                        String authToken = token.substring(tokenHead.length());
                        String username = jwtTokenUtil.getUserNameFormToken(authToken);
//                        token中存在用户
                        if (!StringUtils.isEmpty(username)) {
//                                登陆
                            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//                                  验证token是否有效  重新设置用户对象
                            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                                UsernamePasswordAuthenticationToken authenticationToken =
                                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                                accessor.setUser(authenticationToken);
                            }
                        }
                    }
                }
                return message;
            }
        });

    }

    /**
     * 配置消息代理
     * @param registry
     */
    @Override
    public  void configureMessageBroker(MessageBrokerRegistry registry){
//        配置代理域 支持多个
//        配置代理目的地前缀为/queue ,可以在配置域上向客户端推送信息
        registry.enableSimpleBroker("/queue");
    }

}
