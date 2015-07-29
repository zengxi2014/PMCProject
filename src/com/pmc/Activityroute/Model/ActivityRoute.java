package com.pmc.Activityroute.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by luluteam on 2015/5/28.
 */

@Document
public class ActivityRoute {
    @Id
    private String id;

    private String deviceId;
    private String appId;

    private List<Route> route;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public List<Route> getRoute() {
        return route;
    }

    public void setRoute(List<Route> route) {
        this.route = route;
    }

    public ActivityRoute(){}

    public ActivityRoute(String deviceId, String appId) {
        this.deviceId = deviceId;
        this.appId = appId;
    }

    public ActivityRoute(String deviceId, String appId, List<Route> route) {
        this.deviceId = deviceId;
        this.appId = appId;
        this.route = route;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s",appId,deviceId,id);
    }
}
