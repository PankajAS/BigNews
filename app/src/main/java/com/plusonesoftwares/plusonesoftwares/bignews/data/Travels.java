package com.plusonesoftwares.plusonesoftwares.bignews.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Plus 3 on 07-03-2017.
 */

public class Travels {

    public static final List<Data> IMG_DESCRIPTIONS = new ArrayList<Data>();

    static {
        Travels.IMG_DESCRIPTIONS.add(new Travels.Data("","","","","","",""));
    }

    public static final class Data {

        public final String categotyName;
        public final String imageFilename;
        public final String description;
        public final String publishedDate;
        public final String loadedDate;
        public final String sourceNews;
        public final String title;

        public Data(String categotyName, String title, String image, String description,
                    String publishedDate, String loadedDate, String sourceNews) {
            this.categotyName = categotyName;
            this.imageFilename = image;
            this.description = description;
            this.publishedDate = publishedDate;
            this.loadedDate = loadedDate;
            this.sourceNews = sourceNews;
            this.title = title;
        }
    }
}
