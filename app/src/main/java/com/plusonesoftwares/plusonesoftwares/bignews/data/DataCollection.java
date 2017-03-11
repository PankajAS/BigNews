package com.plusonesoftwares.plusonesoftwares.bignews.data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Plus 3 on 09-03-2017.
 */

public class DataCollection {
   public  static JSONArray jarray;
    static String dataString = "{\n" +
            " \"items\": [\n" +
            "  {\n" +
            "   \"title\": \"Modi lauds passage of Maternity Leave Bill, says it protects womens employment - Oneindia\",\n" +
            "   \"desc\": {\n" +
            "    \"value\": \"New Delhi, March 10: Prime Minister Narendra Modi on Friday lauded the passage of the Maternity Benefit Amendment Bill in the Lok Sabha. Its passage \\\"is a landmark moment in our efforts towards women-led development\\\", he tweeted. The bill ensures ...\"\n" +
            "   },\n" +
            "   \"link\": \"http://www.oneindia.com/india/narendra-modi-lauds-maternity-leave-bills-passage-in-lok-sabha-2370467.html\",\n" +
            "   \"publishedDate\": \"2017-03-10T08:26:12.000Z\",\n" +
            "   \"loadedDate\": \"2017-03-10T12:27:19.843Z\",\n" +
            "   \"sourceNews\": \"Oneindia\",\n" +
            "   \"imgURL\": \"//t2.gstatic.com/images?q=tbn:ANd9GcSpzfEI9p4t_DXmmjqjxMAAxWyY4R7CW4GnvQRcxUdVMXzTEvp02xeoDF10OO3oxXvfYWp2NJSs\",\n" +
            "   \"sourceNewsDomain\": \"http://www.oneindia.com\",\n" +
            "   \"kind\": \"flipnewsendpoint#resourcesItem\"\n" +
            "  },\n" +
            "  {\n" +
            "   \"title\": \"Tata Motors signs MoU with VW, Skoda to launch products in 2019 - Moneycontrol.com\",\n" +
            "   \"desc\": {\n" +
            "    \"value\": \"The agreement has been signed by Guenter Butschek, CEO & MD of Tata Motors, Matthias Mueller, CEO of Volkswagen AG and Bernhard Maier, CEO of Skoda Auto. Like this story, share it with millions of investors on M3 ...\"\n" +
            "   },\n" +
            "   \"link\": \"http://www.moneycontrol.com/news/business/tata-motors-signs-mouvw-skoda-to-launch-products2019_8628741.html\",\n" +
            "   \"publishedDate\": \"2017-03-10T10:06:15.000Z\",\n" +
            "   \"loadedDate\": \"2017-03-10T12:27:19.844Z\",\n" +
            "   \"sourceNews\": \"Moneycontrol.com\",\n" +
            "   \"imgURL\": \"//t0.gstatic.com/images?q=tbn:ANd9GcQ2AvH5e7CFR_7O9VuM2Pvs-wP5l48OSDmCFVa9LR5yqQxNwT4qTxa2WR7AzmJZJ9DrJA84Dgce\",\n" +
            "   \"sourceNewsDomain\": \"http://www.moneycontrol.com\",\n" +
            "   \"kind\": \"flipnewsendpoint#resourcesItem\"\n" +
            "  },\n" +
            "  {\n" +
            "   \"title\": \"Mallya says ready to talk to banks for one-time settlement - Business Standard\",\n" +
            "   \"desc\": {\n" +
            "    \"value\": \"The embattled liquor baron Vijay Mallya today took to Twitter saying he is ready to negotiate with banks to pay a one-time settlement charge on Rs 9,000-crore loan default. \\\"Public sector banks have policies for one-time settlements. Hundreds of ...\"\n" +
            "   },\n" +
            "   \"link\": \"http://www.business-standard.com/article/pti-stories/mallya-says-ready-to-talk-to-banks-for-one-time-settlement-117031000528_1.html\",\n" +
            "   \"publishedDate\": \"2017-03-10T09:39:39.000Z\",\n" +
            "   \"loadedDate\": \"2017-03-10T12:27:19.844Z\",\n" +
            "   \"sourceNews\": \"Business Standard\",\n" +
            "   \"imgURL\": \"//t1.gstatic.com/images?q=tbn:ANd9GcRJdDetEw8wYejQ3nSjpjgWfAtmRwDWU-LGtW7-c_K8xKfTxiD-ZPZ5t-TTf9wNHFcPcXj7WgRZ\",\n" +
            "   \"sourceNewsDomain\": \"http://www.business-standard.com\",\n" +
            "   \"kind\": \"flipnewsendpoint#resourcesItem\"\n" +
            "  },\n" +
            "  {\n" +
            "   \"title\": \"Punjab heads for counting day, parties keep fingers crossed - Times of India\",\n" +
            "   \"desc\": {\n" +
            "    \"value\": \"CHANDIGARH: The stage is set for counting of votes on Saturday for 117 assembly seats in Punjab with pollsters predicting the exit of the ruling SAD-BJP alliance and a neck-and-neck fight between the debutant AAP and the Congress. Counting of votes ...\"\n" +
            "   },\n" +
            "   \"link\": \"http://timesofindia.indiatimes.com/elections/assembly-elections/punjab/news/punjab-heads-for-counting-day-parties-keep-fingers-crossed/articleshow/57575693.cms\",\n" +
            "   \"publishedDate\": \"2017-03-10T10:53:47.000Z\",\n" +
            "   \"loadedDate\": \"2017-03-10T12:27:19.844Z\",\n" +
            "   \"sourceNews\": \"Times of India\",\n" +
            "   \"imgURL\": \"//t3.gstatic.com/images?q=tbn:ANd9GcTjJuD5JjBz0O4eia8M8nk03xWG6fOGryB70C1y7H2iqixwrws4aB4tXRnSAg4DI2rJuCMgkodo\",\n" +
            "   \"sourceNewsDomain\": \"http://timesofindia.indiatimes.com\",\n" +
            "   \"kind\": \"flipnewsendpoint#resourcesItem\"\n" +
            "  }\n" +
            " ],\n" +
            " \"kind\": \"flipnewsendpoint#resources\",\n" +
            " \"etag\": \"\\\"nUSomUsvqA4uuugCSyp0STNjjIo/xoErlBp_jNwv5q6VvwJIQureP6I\\\"\"\n" +
            "}";

    public static final List<Data> IMG_DESCRIPTIONS = new ArrayList<>();
    public static final JSONObject JSON_OBJECT = new JSONObject();




       static {
          // DataCollection.JSON_OBJECT = jsonReader.


           DataCollection.IMG_DESCRIPTIONS.add(new DataCollection.Data("Pankaj","......","pankaj.jpg"));
           DataCollection.IMG_DESCRIPTIONS.add(new DataCollection.Data("Pankaj","......","pankaj.jpg"));
           DataCollection.IMG_DESCRIPTIONS.add(new DataCollection.Data("Pankaj","......","pankaj.jpg"));
           DataCollection.IMG_DESCRIPTIONS.add(new DataCollection.Data("Pankaj","......","pankaj.jpg"));
           DataCollection.IMG_DESCRIPTIONS.add(new DataCollection.Data("Pankaj","......","pankaj.jpg"));
           DataCollection.IMG_DESCRIPTIONS.add(new DataCollection.Data("Pankaj","......","pankaj.jpg"));
           DataCollection.IMG_DESCRIPTIONS.add(new DataCollection.Data("Pankaj","......","pankaj.jpg"));
           DataCollection.IMG_DESCRIPTIONS.add(new DataCollection.Data("Pankaj","......","pankaj.jpg"));

       }





    public static final class Data {
        String title;
        String description;
        String img;

        public Data(String title, String description, String img) {
            this.title = title;
            this.description = description;
            this.img = img;
        }

    }
}
