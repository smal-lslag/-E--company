package com.xxxx.server.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 消息类
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ChatMsg {
//    谁发送的
    private String from;
//    发给谁的
    private String to;
//    发送内容
    private String content;
//    发送时间
    private LocalDateTime date;
//    发消息人的昵称
    private String formNickName;
}
