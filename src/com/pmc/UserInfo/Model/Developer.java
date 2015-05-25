package com.pmc.UserInfo.Model;

import org.springframework.data.annotation.Id;
/**
 * Created by luluteam on 2015/5/23.
 */
public class Developer {
    @Id
    private String id;

    private String firstName;
    private String lastName;

}
