package com.fengkuizhang.dvs.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class VideoDto {

    /**
     * a.	Movie or TV Show name,
     * b.	Movie/TV Show price,
     * c.	Synopsis of the movie or tv show,
     * d.	A value to represent if the item is a movie or tv show
     * e.	Movie/TV show small poster (image path of the movie/tv show)
     * f.	Movie/TV  show large poster (image path of the movie /tv show)
     * g.	The price to rent the movie or tv show
     * h.	The price to purchase the movie or tv show outright.
     * i.	A field to determine if the movie or tv show is a featured movie or tv show
     */
    private String id;

    @NotEmpty
    private String name;
    @NotEmpty
    private String overview;
    @NotEmpty
    private String type;
    @NotEmpty
    private String poster_small;
    @NotEmpty
    private String poster_big;
    @NotEmpty
    private String price_rent;
    @NotEmpty
    private String price_buy;

    private String year;
    private String tags;
    private String length;
    private String director;
    private boolean featured;
}
