package com.example.skillsgogo.CourseImp;

import java.util.HashMap;
import java.util.Map;

public class Question {
    private String question;
    private String op1;
    private String op2;
    private String op3;
    private String op4;
    private String ans;

    public Question(String question, String op1, String op2, String op3, String op4, String ans) {
        this.question = question;
        this.op1 = op1;
        this.op2 = op2;
        this.op3 = op3;
        this.op4 = op4;
        this.ans = ans;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOp1() {
        return op1;
    }

    public void setOp1(String op1) {
        this.op1 = op1;
    }

    public String getOp2() {
        return op2;
    }

    public void setOp2(String op2) {
        this.op2 = op2;
    }

    public String getOp3() {
        return op3;
    }

    public void setOp3(String op3) {
        this.op3 = op3;
    }

    public String getOp4() {
        return op4;
    }

    public void setOp4(String op4) {
        this.op4 = op4;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    @Override
    public String toString() {
        return "com.example.pbl_v1.course.Question{" +
                "question='" + question + '\'' +
                ", op1='" + op1 + '\'' +
                ", op2='" + op2 + '\'' +
                ", op3='" + op3 + '\'' +
                ", op4='" + op4 + '\'' +
                ", ans='" + ans + '\'' +
                '}';
    }

    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("question", question);
        result.put("op1", op1);
        result.put("op2", op2);
        result.put("op3", op3);
        result.put("op4", op4);
        result.put("ans", ans);
        return result;

    }
}
