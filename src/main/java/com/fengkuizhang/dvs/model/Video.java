package com.fengkuizhang.dvs.model;

import lombok.Data;

@Data
public class Video {

    private String id;
    private String name;
    private String year;
    private String tags;
    private String length;
    private String director;
    private String price_rent;
    private String price_buy;
    private String poster_small;
    private String poster_big;
    private String overview;
    private String type;
    private boolean featured;

}
