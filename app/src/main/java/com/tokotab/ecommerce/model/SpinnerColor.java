package com.tokotab.ecommerce.model;

/**
 * Created by dwikadarmawan on 6/29/16.
 */
public class SpinnerColor {

    private String name;
    private String color;
    private String id;
    public static final String ARG_INTERNAL_ID = "color_internal";
    public static final String ARG_COLOR_NAME = "color_name";
    public static final String ARG_COLOR_HEXA = "color_hexa";

    public SpinnerColor(String name, String color, String id) {
        this.name = name;
        this.color = color;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
