package com.abdul_Codefellowship.codefellowship.nytimes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class News {
public Integer num_results;
public List<Result> results;
public class Result{
    private String updated_date;
    private String created_date;
    private String published_date;
    private List<Multimedium> multimedia = new ArrayList<Multimedium>();
    private String short_url;
    private String title;
    //private String abstract;
    private String url;
}
    public class Multimedium{

            private String url;
            private String format;
            private Integer height;
            private Integer width;
            private String type;
            private String subtype;
            private String caption;
            private String copyright;
    }


}
