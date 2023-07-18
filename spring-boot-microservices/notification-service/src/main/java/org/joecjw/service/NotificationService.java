package org.joecjw.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joecjw.dto.NotificationEmailDto;
import org.joecjw.event.OrderPlacedEvent;
import org.joecjw.utils.EmailUtil;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final EmailUtil emailUtil;

    @KafkaListener(topics = "notificationTopic")
    public void handleNotification(OrderPlacedEvent orderPlacedEvent){
        log.info("Received Notification for Order - {}", orderPlacedEvent.getOrderNumber());
        //send email notification
        NotificationEmailDto notificationEmailDto = NotificationEmailDto.builder()
                .to("bdih0927@gmail.com")
                .subject("Spring Boot Microservices Order Place Testing")
                .cc(null)
                .body("Your order #" + orderPlacedEvent.getOrderNumber()+ " have been received!\n"
                        +"You can check the details of the order through our online services.\n"
                        +"Thank you!")
                .classPathResourceName(("notification-email-image.jpg"))
                .build();
        emailUtil.send(notificationEmailDto);
        log.info("Email Notification Sent Out Successfully");
    }
}
