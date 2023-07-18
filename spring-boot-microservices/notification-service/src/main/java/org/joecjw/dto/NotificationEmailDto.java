package org.joecjw.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationEmailDto {

    private final String SENDER_EMAIL = "joechan1520@gmail.com";

    private String to;

    private String subject;

    private String[] cc;

    private String body;

    private String classPathResourceName;
}
