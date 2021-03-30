
package com.gesforback.email;

import java.util.List;
import java.util.Map;
import lombok.Data;

/**
 *
 * @author sud
 */
@Data
public class Mail {

    private String from;
    private String mailTo;
    private String subject;
    private List<Object> attachments;
    private Map<String, Object> props;
}
