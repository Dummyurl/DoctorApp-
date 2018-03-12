package com.bfurns.model;

/**
 * Created by Explicate1 on 12/21/2017.
 */

public class ConsultModel {

    String name,title,Description,date,time,doctor_name,Query_id,p_from,p_to,reply_id,chat_type,d_to,d_from,flag,read;

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getD_to() {
        return d_to;
    }

    public void setD_to(String d_to) {
        this.d_to = d_to;
    }

    public String getD_from() {
        return d_from;
    }

    public void setD_from(String d_from) {
        this.d_from = d_from;
    }

    public String getReply_id() {
        return reply_id;
    }

    public void setReply_id(String reply_id) {
        this.reply_id = reply_id;
    }

    public String getP_from() {
        return p_from;
    }

    public void setP_from(String p_from) {
        this.p_from = p_from;
    }

    public String getP_to() {
        return p_to;
    }

    public void setP_to(String p_to) {
        this.p_to = p_to;
    }

    public String getQuery_id() {
        return Query_id;
    }

    public void setQuery_id(String query_id) {
        Query_id = query_id;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getChat_type() {
        return chat_type;
    }

    public void setChat_type(String chat_type) {
        this.chat_type = chat_type;
    }
}
