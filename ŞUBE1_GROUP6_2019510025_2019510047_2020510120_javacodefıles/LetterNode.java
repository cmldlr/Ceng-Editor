package com.company;


public class LetterNode {
    private char letter;
    private LetterNode next;
    private LetterNode previous;
    private int letterx;
    private int lettery;

    public LetterNode(char letter) {
        this.letter = letter;
        next=null;
        previous=null;
    }
    public LetterNode(int px,int py,char dataToAdd){
        this.letter = dataToAdd;
        next=null;
        previous=null;
        letterx=px;
        lettery=py;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public LetterNode getNext() {
        return next;
    }

    public void setNext(LetterNode next) {
        this.next = next;
    }

    public LetterNode getPrevious() {
        return previous;
    }

    public void setPrevious(LetterNode previous) {
        this.previous = (LetterNode) previous;
    }

    public int getLetterx() {
        return letterx;
    }

    public void setLetterx(int letterx) {
        this.letterx = letterx;
    }

    public int getLettery() {
        return lettery;
    }

    public void setLettery(int lettery) {
        this.lettery = lettery;
    }
}

