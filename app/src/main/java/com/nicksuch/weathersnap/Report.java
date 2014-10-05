package com.nicksuch.weathersnap;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by nicksuch on 10/4/14.
 */
@ParseClassName("Report")
public class Report extends ParseObject {

    public Report() {
        // A default constructor is required.
    }

    public String getTitle() {
        return getString("title");
    }

    public void setTitle(String title) {
        put("title", title);
    }

    public ParseUser getAuthor() {
        return getParseUser("author");
    }

    public void setAuthor(ParseUser user) {
        put("author", user);
    }

    public String getConditions() {
        return getString("conditions");
    }

    public void setConditions(String conditions) {
        put("conditions", conditions);
    }

    public ParseFile getPhotoFile() {
        return getParseFile("photo");
    }

    public void setPhotoFile(ParseFile file) {
        put("photo", file);
    }

}
