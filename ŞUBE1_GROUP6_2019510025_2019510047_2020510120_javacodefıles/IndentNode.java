package com.company;

public class IndentNode {
    private char Indent;
    private IndentNode down;
    private IndentNode up;
    private LetterNode right;
    private int indentx;
    private int indenty;
    private LetterNode lettertail;
    public IndentNode(char indent) {
        Indent = indent;
        down=null;
        up=null;
        right=null;
    }
    public IndentNode(int px,int py,char dataToAdd){
        Indent = dataToAdd;
        down=null;
        up=null;
        right=null;
        indentx=px;
        indenty=py;
    }
    public LetterNode getLettertail() {
        return lettertail;
    }
    public void setLettertail(LetterNode lettertail) {
        this.lettertail = lettertail;
    }
    public int getIndentx() {
        return indentx;
    }
    public int getIndenty() {
        return indenty;
    }
    public void setIndentx(int indentx) {
        indentx = indentx;
    }
    public void setIndenty(int indenty) {
        this.indenty = indenty;
    }
    public char getIndent() {
        return Indent;
    }
    public void setIndent(char indent) {
        Indent = indent;
    }
    public IndentNode getDown() {
        return down;
    }
    public void setDown(IndentNode down) {
        this.down = down;
    }
    public IndentNode getUp() {
        return up;
    }
    public void setUp(IndentNode up) {
        this.up = up;
    }
    public LetterNode getRight() {
        return right;
    }
    public void setRight(LetterNode right) {
        this.right = right;
    }
}

