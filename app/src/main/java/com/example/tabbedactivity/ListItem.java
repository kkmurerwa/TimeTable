package com.example.tabbedactivity;

public class ListItem {
    private String head;
    private String desc;
    private String lesson;
    private String lecturer;

    ListItem(String head, String desc, String lesson, String lecturer) {
        this.head = head;
        this.desc = desc;
        this.lesson = lesson;
        this.lecturer = lecturer;
    }

    String getHead() {
        return head;
    }

    String getDesc() {
        return desc;
    }

    String getLesson() { return lesson; }

    String getLecturer() { return lecturer; }
}
