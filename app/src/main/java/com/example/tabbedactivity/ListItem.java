package com.example.tabbedactivity;

public class ListItem {
    private String head;
    private String desc;
    private String lesson;
    private String lecturer;

    public ListItem(String head, String desc, String lesson, String lecturer) {
        this.head = head;
        this.desc = desc;
        this.lesson = lesson;
        this.lecturer = lecturer;
    }

    public String getHead() {
        return head;
    }

    public String getDesc() {
        return desc;
    }

    public String getLesson() { return lesson; }

    public String getLecturer() { return lecturer; }
}
