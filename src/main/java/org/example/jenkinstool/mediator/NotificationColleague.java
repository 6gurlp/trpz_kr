package org.example.jenkinstool.mediator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotificationColleague {

    private CiMediator mediator;
    private final List<String> notifications = new ArrayList<>();

    public void setMediator(CiMediator mediator) {
        this.mediator = mediator;
    }

    public void notifyPipelineFinished(String pipelineName, boolean success) {
        String msg = success
                ? "Pipeline '" + pipelineName + "' finished SUCCESSFULLY"
                : "Pipeline '" + pipelineName + "' FAILED";
        notifications.add(msg);
    }

    public List<String> getNotifications() {
        return Collections.unmodifiableList(notifications);
    }

    public void clear() {
        notifications.clear();
    }
}