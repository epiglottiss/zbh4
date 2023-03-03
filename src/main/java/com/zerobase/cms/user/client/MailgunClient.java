package com.zerobase.cms.user.client;

import com.zerobase.cms.user.client.mailgun.SendMailForm;
import feign.Response;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="mailgun", url = "https://api.mailgun.net/v3/")
@Qualifier("mailgun")
public interface MailgunClient {
    @PostMapping("sandbox11a44533eec541dbac85dd78f50658db.mailgun.org/messages")
    Response sendEmail(@SpringQueryMap SendMailForm form);
}
