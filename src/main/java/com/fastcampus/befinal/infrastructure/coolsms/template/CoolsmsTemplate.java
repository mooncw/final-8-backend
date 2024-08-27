package com.fastcampus.befinal.infrastructure.coolsms.template;

import lombok.AllArgsConstructor;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@AllArgsConstructor
public class CoolsmsTemplate {
    private DefaultMessageService defaultMessageService;

    private String fromContact;

    public SingleMessageSentResponse sendSms(String toContact, String content) {
        Message message = new Message();
        message.setFrom(this.fromContact);
        message.setTo(toContact);
        message.setText(content);

        return defaultMessageService.sendOne(new SingleMessageSendingRequest(message));
    }
}
