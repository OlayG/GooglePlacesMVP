package com.example.admin.googleplacesmvp.model;

import java.util.List;

/**
 * Created by Admin on 8/27/2017.
 */

public class MessageEvent {

    public final List<Result> results;

    public MessageEvent(List<Result> results) {
        this.results = results;
    }
}
