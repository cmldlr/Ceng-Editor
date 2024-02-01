package com.company;

import enigma.console.TextAttributes;
import enigma.core.Enigma;

import java.awt.*;

public class Mll {
    IndentNode head;
    IndentNode ındenttail;// asagıdakı kuyruk
    int headx;
    int heady;
    public static char tem;
    public static String tem2="";
    public static enigma.console.Console cn = Enigma.getConsole("CENG EDITOR", 85, 10);
    public static int [][] replace=new int [10][4];
    public static int cont=0;
    public Mll(){
        head=null;
        ındenttail=null;
    }
    public void addingletterfromend(int px, int py, char letter,IndentNode temp) {
        LetterNode newLetterNode = new LetterNode(px,py,letter);
        temp.getLettertail().setNext(newLetterNode);
        newLetterNode.setPrevious(temp.getLettertail());
        temp.setLettertail(newLetterNode);



    }
    public void addingletterfromfront(int px,int py,char dataToAdd, IndentNode temp) {
        // cümlebaşına harf ekleme
        LetterNode newLetterNode = new LetterNode(px,py,dataToAdd);
        temp.getRight().setPrevious(newLetterNode);
        newLetterNode.setNext(temp.getRight());
        temp.setRight(newLetterNode);
        temp.setIndentx(newLetterNode.getLetterx());//temp ındentın konumunu değiştirme
        temp.setIndenty(newLetterNode.getLettery());
        // tail değişmez


    }
    public void addletterfromfront(int px,int py,char dataToAdd, IndentNode temp){//önden ekleme kaydırmalı
        LetterNode newLetterNode1=temp.getRight();
        LetterNode newLetterNode = new LetterNode(px,py,dataToAdd);
        temp.getRight().setPrevious(newLetterNode);
        newLetterNode.setNext(temp.getRight());
        temp.setRight(newLetterNode);

        for (int i = 0; i <sizeletter(temp); i++) {//px 60 olma durumuda var
            if (newLetterNode1 != null) {
                newLetterNode1.setLetterx(newLetterNode1.getLetterx()+1);
                newLetterNode1.setLettery(newLetterNode1.getLettery());
                newLetterNode1=newLetterNode1.getNext();
            }

        }
    }
    public void addingletterfromaway(int px,int py,char dataToAdd,IndentNode temp) {
        LetterNode newLetterNode = new LetterNode(px,py,dataToAdd);
        LetterNode temp1=temp.getRight();
        LetterNode previous=null;
        while (temp1!=null && px>temp1.getLetterx()){
            previous=temp1;
            temp1=temp1.getNext();
        }
        previous.setNext(newLetterNode);
        newLetterNode.setPrevious(previous);
        temp1.setPrevious(newLetterNode);
        newLetterNode.setNext(temp1);
        if(temp1.getLetterx()-previous.getLetterx()==1){
            for (int i = 0; i <60; i++) {//tüm satırı temizleyip tekrar yazmak için
                cn.getTextWindow().setCursorPosition(i+1,temp.getIndenty());
                System.out.print(" ");
            }
            for (int i = 0; i <=temp.getLettertail().getLetterx()-px; i++) {//px 60 olma durumuda var
                if(temp1!=null){
                    temp1.setLetterx(temp1.getLetterx()+1);
                    temp1.setLettery(temp1.getLettery());
                    temp1=temp1.getNext();
                }

            }
        }




    }
    public void addhead(int px,int py,char dataToAdd ){
        IndentNode newIndentNode = new IndentNode(px,py,'*');
        head = newIndentNode;
        ındenttail = newIndentNode;
        //yeni bir letter node oluşturup ındente gelmesi gereken değeri letter node ekliyoruz
        LetterNode newLetterNode=new LetterNode(px,py,dataToAdd);
        head.setRight(newLetterNode);
        headx=px;
        heady=py;
        head.setLettertail(newLetterNode);//ındentın sonuna  tail letter ekleme
        ındenttail.setRight(newLetterNode);
        ındenttail.setLettertail(newLetterNode);//ındentın sonuna  tail letter ekleme
    }
    public void addındentfromend(int px,int py,char dataToAdd){//sona ındent ekleme
        IndentNode newIndentNode = new IndentNode(px,py,'*');
        if (sizeindent() == 1) {//head olan indentin altına indent ekleme
            head.setDown(newIndentNode);
            newIndentNode.setUp(head);
            ındenttail =newIndentNode;
            LetterNode newLetterNode=new LetterNode(px,py,dataToAdd);
            ındenttail.setRight(newLetterNode);
            ındenttail.setLettertail(newLetterNode);//ındentın sonuna  tail letter ekleme

        } else if (sizeindent() != 1) {//burada head yok yanlış olma ihtimali var  //doğru
            newIndentNode.setUp(ındenttail);
            ındenttail.setDown(newIndentNode);
            ındenttail = newIndentNode;
            LetterNode newLetterNode=new LetterNode(px,py,dataToAdd);
            ındenttail.setRight(newLetterNode);
            ındenttail.setLettertail(newLetterNode);//ındentın sonuna  tail letter ekleme
        }
    }
    public void addbetween2indents(int px,int py,char dataToAdd){//araya ekleme
        IndentNode temp=head;
        IndentNode previous=null;
        while (temp!=null && py>temp.getIndenty()){
            previous=temp;
            temp=temp.getDown();
        }
        IndentNode newIndentNode = new IndentNode(px,py,'*');
        newIndentNode.setUp(previous);
        previous.setDown(newIndentNode);
        newIndentNode.setDown(temp);
        temp.setUp(newIndentNode);
        IndentNode temp1=head;
        while (temp1!=null){//ındent node eklemesi bittikten sonra orada bulunan ındentın yanına  letter node ekleme
            if(temp1.getIndenty()==py){
                LetterNode newLetterNode=new LetterNode(px,py,dataToAdd);
                temp1.setRight(newLetterNode);
                temp1.setLettertail(newLetterNode);//ındentın sonuna  tail letter ekleme
                break;
            }
            temp1=temp1.getDown();
        }



    }
    public void addonhead(int px,int py,char dataToAdd){
        IndentNode newIndentNode = new IndentNode(px,py,'*');
        if (sizeindent() == 1) {//head olan indentin altına indent ekleme
            ındenttail.setUp(newIndentNode);
            newIndentNode.setDown(ındenttail);
            head=newIndentNode;
            LetterNode newLetterNode=new LetterNode(px,py,dataToAdd);
            head.setRight(newLetterNode);
            head.setLettertail(newLetterNode);

        } else if (sizeindent() != 1) {//burada head yok yanlış olma ihtimali var  //doğru
            newIndentNode.setDown(head);
            head.setUp(newIndentNode);
            head=newIndentNode;
            LetterNode newLetterNode=new LetterNode(px,py,dataToAdd);
            head.setRight(newLetterNode);
            head.setLettertail(newLetterNode);
        }

    }
    public void add2(int px, int py, char dataToAdd) {
        boolean control=true;
        if(head==null){
            control=true;
        }
        else {
            if(py<heady){
                control=true;
            }
            IndentNode temp3 = head;
            while(temp3!=null) {
                if (temp3.getIndenty() == py) {
                    control = false;
                    break;
                } else {
                    control = true;

                }
                temp3 = temp3.getDown();
            }



        }
        if(control) {
            if(head == null){
                addhead(px,py,dataToAdd);
            }
            else if(py> ındenttail.getIndenty() &&head!=null &&ındenttail!=null){
                addındentfromend(px,py,dataToAdd);
            }
            else if(py<heady){
                addonhead(px,py,dataToAdd);
            }
            else{
                addbetween2indents(px,py,dataToAdd);
            }
        }
        else{

            IndentNode temp4=head;
            IndentNode temp6=null;
            for (int j = 0; j <sizeindent(); j++) {
                if(temp4.getIndenty()==py){
                    temp6=temp4;
                    break;
                }
                temp4=temp4.getDown();
            }
            if(temp6.getLettertail().getLetterx()!=60) {
                if(px<temp6.getRight().getLetterx()){
                    addingletterfromfront(px,py,dataToAdd, temp6);
                }
                else if(px==temp6.getRight().getLetterx() ){
                    addletterfromfront(px,py,dataToAdd, temp6);


                }
                else if(px>temp6.getLettertail().getLetterx()){
                    addingletterfromend(px, py, dataToAdd,temp6);

                }
                else {
                    addingletterfromaway(px, py, dataToAdd,temp6);
                }
            }


        }




    }
    public void overwrite(int px, int py, char dataToAdd){
        boolean control=true;
        if(head==null){
            control=true;
        }
        else {
            if(py<heady){//başa indent  ekleme durumu
                control=true;
            }
            IndentNode temp3 = head;
            while(temp3!=null) {
                if (temp3.getIndenty() == py) {//eğer eşit ise letter eklenir
                    control = false;
                    break;
                } else {//değilse o koordinatta ındent yoktur oluşturmak zorundayız
                    control = true;

                }
                temp3 = temp3.getDown();
            }



        }
        if(control) {//yeni indent ekleme
            if(head == null){//ındent headı ekleme
                IndentNode newIndentNode = new IndentNode(px,py,'*');
                head = newIndentNode;
                ındenttail = newIndentNode;
                //yeni bir letter node oluşturup ındente gelmesi gereken değeri letter node ekliyoruz
                LetterNode newLetterNode=new LetterNode(px,py,dataToAdd);
                head.setRight(newLetterNode);
                headx=px;
                heady=py;
                head.setLettertail(newLetterNode);//ındentın sonuna  tail letter ekleme
                ındenttail.setRight(newLetterNode);
                ındenttail.setLettertail(newLetterNode);//ındentın sonuna  tail letter ekleme
            }
            else if(py> ındenttail.getIndenty() &&head!=null &&ındenttail!=null){//sona ındent ekleme
                addındentfromend(px,py,dataToAdd);
            }
            else if(py<heady){//headın üstüne ındent ekleme
                addonhead(px,py,dataToAdd);
            }
            else{//araya  ındent ekleme
                //2 olasılık var araya ındent eklemek için
                //1.si arada satır yoksa
                //2.si iki ındent arasında
                addbetween2indents(px,py,dataToAdd);
            }
        }
        else{//yeni letter ekleme
            cn.getTextWindow().setCursorPosition(15,10);
            System.out.println("asda");
            boolean control1=false;//konum üzerinde bir letter varsa
            IndentNode temp=head;
            LetterNode temp1=null;
            while (temp!=null){
                if(temp.getIndenty()==py){
                    temp1=temp.getRight();
                    while (temp1!=null){
                       if(temp1.getLetterx()==px){
                           control1=true;
                           break;
                       }
                    }
                    temp1=temp1.getNext();
                }
                temp=temp.getDown();
            }
            if(!control1){
                cn.getTextWindow().setCursorPosition(22,22);
                System.out.println("a");
                temp1.setLetter(dataToAdd);
                cn.getTextWindow().setCursorPosition(22,22);
                System.out.println(" ");
            }
            else{//olmayan yeni nokta ekleme
                cn.getTextWindow().setCursorPosition(20,15);
                System.out.println("sadas");
                IndentNode temp4=head;
                IndentNode temp6=null;
                for (int j = 0; j <sizeindent(); j++) {
                    if(temp4.getIndenty()==py){
                        temp6=temp4;
                        break;
                    }
                    temp4=temp4.getDown();
                }
                if(px<temp6.getRight().getLetterx()){//en başa ekleme
                    cn.getTextWindow().setCursorPosition(23,22);
                    System.out.println("b");
                    addingletterfromfront(px,py,dataToAdd, temp6);
                    cn.getTextWindow().setCursorPosition(23,22);
                    System.out.println("  ");
                }
                else if(px>temp6.getLettertail().getLetterx()){//sona ekleme
                    cn.getTextWindow().setCursorPosition(24,22);
                    System.out.println("c");
                    IndentNode newIndentNode = new IndentNode(px,py,'*');
                    if (sizeindent() == 1) {//head olan indentin altına indent ekleme
                        head.setDown(newIndentNode);
                        newIndentNode.setUp(head);
                        ındenttail =newIndentNode;
                        LetterNode newLetterNode=new LetterNode(px,py,dataToAdd);
                        ındenttail.setRight(newLetterNode);
                        ındenttail.setLettertail(newLetterNode);//ındentın sonuna  tail letter ekleme

                    } else if (sizeindent() != 1) {//burada head yok yanlış olma ihtimali var  //doğru
                        newIndentNode.setUp(ındenttail);
                        ındenttail.setDown(newIndentNode);
                        ındenttail = newIndentNode;
                        LetterNode newLetterNode=new LetterNode(px,py,dataToAdd);
                        ındenttail.setRight(newLetterNode);
                        ındenttail.setLettertail(newLetterNode);//ındentın sonuna  tail letter ekleme
                    }
                    cn.getTextWindow().setCursorPosition(24,22);
                    System.out.println(" ");

                }
                else{
                    cn.getTextWindow().setCursorPosition(25,22);
                    System.out.println("d");
                    addingletterfromaway(px,py,dataToAdd,temp6);
                    cn.getTextWindow().setCursorPosition(25,22);
                    System.out.println(" ");
                }
            }





        }
    }
    public void remove(int px, int py) {
        if(head!=null){
            boolean control=false;
            boolean control_indent=false;
            IndentNode c_temp_i=head;
            LetterNode c_temp_l=null;
            while (c_temp_i!=null){;
                if(c_temp_i.getIndenty()==py){
                    if(c_temp_i.getRight().getLetter()==' ' &&sizeletter(c_temp_i)==1){
                        control_indent=true;

                        break;
                    }
                    else{
                        c_temp_l=c_temp_i.getRight();
                        while (c_temp_l!=null){
                            if(c_temp_l.getLetterx()==px-1){
                                control=true;
                                break;
                            }
                            c_temp_l=c_temp_l.getNext();
                        }
                    }

                }
                c_temp_i=c_temp_i.getDown();
            }
            if(control){
                IndentNode temp;
                LetterNode temp2;
                IndentNode remove_posıtıon_ındent=null;
                LetterNode remove_posıtıon_letter=null;
                temp = head;
                while (temp!=null){
                    if(temp.getIndenty()==py){
                        remove_posıtıon_ındent=temp;
                        break;
                    }
                    temp=temp.getDown();
                }
                temp2=remove_posıtıon_ındent.getRight();
                while (temp2!=null){
                    if(temp2.getLetterx()== px-1){
                        remove_posıtıon_letter=temp2;
                        break;
                    }
                    temp2=temp2.getNext();


                }
                if(sizeletter(remove_posıtıon_ındent)==1){
                    remove_posıtıon_ındent.getRight().setLetter(' ');


                }
                else if(sizeletter(remove_posıtıon_ındent)!=1 && remove_posıtıon_letter.getLetterx()==remove_posıtıon_ındent.getRight().getLetterx() &&
                        remove_posıtıon_letter.getLettery()==remove_posıtıon_ındent.getRight().getLettery()){
                    LetterNode temp_letter=remove_posıtıon_letter.getNext();
                    remove_posıtıon_ındent.setRight(temp_letter);
                    for (int i = 0; i <60; i++) {
                        cn.getTextWindow().setCursorPosition(i+1,remove_posıtıon_ındent.getIndenty());
                        System.out.print(" ");
                    }
                    while (temp_letter!=null){
                        temp_letter.setLetterx(temp_letter.getLetterx()-1);
                        temp_letter.setLettery(temp_letter.getLettery());
                        temp_letter=temp_letter.getNext();
                    }


                }
                else if(sizeletter(remove_posıtıon_ındent)!=1 && remove_posıtıon_letter.getLetterx()==remove_posıtıon_ındent.getLettertail().getLetterx() &&
                        remove_posıtıon_letter.getLettery()==remove_posıtıon_ındent.getLettertail().getLettery()){

                    LetterNode temp_letter=remove_posıtıon_letter.getPrevious();
                    temp_letter.setNext(null);
                    remove_posıtıon_letter.setPrevious(null);
                    remove_posıtıon_ındent.setLettertail(temp_letter);
                    for (int i = 0; i <60; i++) {
                        cn.getTextWindow().setCursorPosition(i+1,remove_posıtıon_ındent.getIndenty());
                        System.out.print(" ");
                    }


                }
                else{
                    LetterNode temp_previous=remove_posıtıon_letter.getPrevious();
                    LetterNode temp_next=remove_posıtıon_letter.getNext();
                    temp_previous.setNext(temp_next);
                    temp_next.setPrevious(temp_previous);
                    while (temp_next!=null){
                        temp_next.setLetterx(temp_next.getLetterx()-1);
                        temp_next.setLettery(temp_next.getLettery());
                        temp_next=temp_next.getNext();
                    }
                    for (int i = 0; i <60; i++) {
                        cn.getTextWindow().setCursorPosition(i+1,remove_posıtıon_ındent.getIndenty());
                        System.out.print(" ");
                    }


                }
            }
            else if(control_indent){

                 if(c_temp_i.getIndentx()==head.getIndentx() &&c_temp_i.getIndenty()==head.getIndenty()){
                    if(sizeindent()==1){
                        head.setDown(null);
                        head.setUp(null);
                        head.setRight(null);
                        head.setLettertail(null);
                        head=null;
                        ındenttail=null;
                    }
                    else{
                        int a=sizeindent();
                        int e=head.getIndenty();
                        IndentNode temp=head.getDown();
                        head.setDown(null);
                        temp.setUp(null);
                        head=temp;
                        IndentNode temp_indent=head;
                        LetterNode temp_letter=null;
                        for (int x = 0; x <60; x++) {
                            for (int y = e; y <e+a; y++) {
                                cn.getTextWindow().setCursorPosition(x+1,y);
                                System.out.print(" ");
                            }
                        }
                        while (temp_indent!=null){
                            temp_indent.setIndentx(temp_indent.getIndentx());
                            temp_indent.setIndenty(temp_indent.getIndenty()-1);
                            temp_letter=temp_indent.getRight();
                            while (temp_letter!=null){
                                temp_letter.setLetterx(temp_letter.getLetterx());
                                temp_letter.setLettery(temp_letter.getLettery()-1);
                                temp_letter=temp_letter.getNext();
                            }
                            temp_indent=temp_indent.getDown();
                        }


                    }

                }
                else if(c_temp_i.getIndentx()==ındenttail.getIndentx() &&c_temp_i.getIndenty()==ındenttail.getIndenty()&&sizeindent()!=1){
                    IndentNode temp=ındenttail.getUp();
                    temp.setDown(null);
                    ındenttail.setUp(null);
                    ındenttail=temp;
                    px=temp.getLettertail().getLetterx();
                    py=temp.getLettertail().getLettery();
                    cn.getTextWindow().setCursorPosition(px,py);



                }
                else {
                     int a=head.getIndenty();
                     int b=ındenttail.getIndenty();
                     IndentNode up=c_temp_i.getUp();
                     IndentNode down=c_temp_i.getDown();
                     up.setDown(down);
                     down.setUp(up);
                     while (down!=null){
                         down.setIndentx(down.getIndentx());
                         down.setIndenty(down.getIndenty()-1);
                         LetterNode next=down.getRight();
                         while (next!=null){
                             next.setLetterx(next.getLetterx());
                             next.setLettery(next.getLettery()-1);
                             next=next.getNext();
                         }
                         down=down.getDown();

                     }
                     for (int i = 0; i <60; i++) {
                         for (int j = a; j <a+b; j++) {
                             cn.getTextWindow().setCursorPosition(i+1,j);
                             System.out.print(" ");

                         }
                     }

                }
            }
        }
    }
    public void delete(int px,int py){
        if(head!=null){
            boolean control=false;
            boolean control_indent=false;
            IndentNode c_temp_i=head;
            LetterNode c_temp_l=null;
            while (c_temp_i!=null){;
                if(c_temp_i.getIndenty()==py){
                    if(c_temp_i.getRight().getLetter()==' ' &&sizeletter(c_temp_i)==1){
                        control_indent=true;

                        break;
                    }
                    else{
                        c_temp_l=c_temp_i.getRight();
                        while (c_temp_l!=null){
                            if(c_temp_l.getLetterx()==px){
                                control=true;
                                break;
                            }
                            c_temp_l=c_temp_l.getNext();
                        }
                    }

                }
                c_temp_i=c_temp_i.getDown();
            }
            if(control){
                IndentNode temp;
                LetterNode temp2;
                IndentNode remove_posıtıon_ındent=null;
                LetterNode remove_posıtıon_letter=null;
                temp = head;
                while (temp!=null){
                    if(temp.getIndenty()==py){
                        remove_posıtıon_ındent=temp;
                        break;
                    }
                    temp=temp.getDown();
                }
                temp2=remove_posıtıon_ındent.getRight();
                while (temp2!=null){
                    if(temp2.getLetterx()== px){
                        remove_posıtıon_letter=temp2;
                        break;
                    }
                    temp2=temp2.getNext();


                }
                if(sizeletter(remove_posıtıon_ındent)==1){
                    remove_posıtıon_ındent.getRight().setLetter(' ');


                }
                else if(sizeletter(remove_posıtıon_ındent)!=1 && remove_posıtıon_letter.getLetterx()==remove_posıtıon_ındent.getRight().getLetterx() &&
                        remove_posıtıon_letter.getLettery()==remove_posıtıon_ındent.getRight().getLettery()){
                    LetterNode temp_letter=remove_posıtıon_letter.getNext();
                    remove_posıtıon_ındent.setRight(temp_letter);
                    for (int i = 0; i <60; i++) {
                        cn.getTextWindow().setCursorPosition(i+1,remove_posıtıon_ındent.getIndenty());
                        System.out.print(" ");
                    }
                    while (temp_letter!=null){
                        temp_letter.setLetterx(temp_letter.getLetterx()-1);
                        temp_letter.setLettery(temp_letter.getLettery());
                        temp_letter=temp_letter.getNext();
                    }


                }
                else if(sizeletter(remove_posıtıon_ındent)!=1 && remove_posıtıon_letter.getLetterx()==remove_posıtıon_ındent.getLettertail().getLetterx() &&
                        remove_posıtıon_letter.getLettery()==remove_posıtıon_ındent.getLettertail().getLettery()){

                    LetterNode temp_letter=remove_posıtıon_letter.getPrevious();
                    temp_letter.setNext(null);
                    remove_posıtıon_letter.setPrevious(null);
                    remove_posıtıon_ındent.setLettertail(temp_letter);
                    for (int i = 0; i <60; i++) {
                        cn.getTextWindow().setCursorPosition(i+1,remove_posıtıon_ındent.getIndenty());
                        System.out.print(" ");
                    }


                }
                else{
                    LetterNode temp_previous=remove_posıtıon_letter.getPrevious();
                    LetterNode temp_next=remove_posıtıon_letter.getNext();
                    temp_previous.setNext(temp_next);
                    temp_next.setPrevious(temp_previous);
                    while (temp_next!=null){
                        temp_next.setLetterx(temp_next.getLetterx()-1);
                        temp_next.setLettery(temp_next.getLettery());
                        temp_next=temp_next.getNext();
                    }
                    for (int i = 0; i <60; i++) {
                        cn.getTextWindow().setCursorPosition(i+1,remove_posıtıon_ındent.getIndenty());
                        System.out.print(" ");
                    }


                }
            }
            else if(control_indent){

                if(c_temp_i.getIndentx()==head.getIndentx() &&c_temp_i.getIndenty()==head.getIndenty()){
                    if(sizeindent()==1){
                        head.setDown(null);
                        head.setUp(null);
                        head.setRight(null);
                        head.setLettertail(null);
                        head=null;
                        ındenttail=null;
                    }
                    else{
                        int a=sizeindent();
                        int e=head.getIndenty();
                        IndentNode temp=head.getDown();
                        head.setDown(null);
                        temp.setUp(null);
                        head=temp;
                        IndentNode temp_indent=head;
                        LetterNode temp_letter=null;
                        for (int x = 0; x <60; x++) {
                            for (int y = e; y <e+a; y++) {
                                cn.getTextWindow().setCursorPosition(x+1,y);
                                System.out.print(" ");
                            }
                        }
                        while (temp_indent!=null){
                            temp_indent.setIndentx(temp_indent.getIndentx());
                            temp_indent.setIndenty(temp_indent.getIndenty()-1);
                            temp_letter=temp_indent.getRight();
                            while (temp_letter!=null){
                                temp_letter.setLetterx(temp_letter.getLetterx());
                                temp_letter.setLettery(temp_letter.getLettery()-1);
                                temp_letter=temp_letter.getNext();
                            }
                            temp_indent=temp_indent.getDown();
                        }


                    }

                }
                else if(c_temp_i.getIndentx()==ındenttail.getIndentx() &&c_temp_i.getIndenty()==ındenttail.getIndenty()&&sizeindent()!=1){//tail ındenti silme
                    IndentNode temp=ındenttail.getUp();
                    temp.setDown(null);
                    ındenttail.setUp(null);
                    ındenttail=temp;
                    px=temp.getLettertail().getLetterx();
                    py=temp.getLettertail().getLettery();
                    cn.getTextWindow().setCursorPosition(px,py);



                }
                else {
                    int a=head.getIndenty();
                    int b=ındenttail.getIndenty();
                    IndentNode up=c_temp_i.getUp();
                    IndentNode down=c_temp_i.getDown();
                    up.setDown(down);
                    down.setUp(up);
                    while (down!=null){
                        down.setIndentx(down.getIndentx());
                        down.setIndenty(down.getIndenty()-1);
                        LetterNode next=down.getRight();
                        while (next!=null){
                            next.setLetterx(next.getLetterx());
                            next.setLettery(next.getLettery()-1);
                            next=next.getNext();
                        }
                        down=down.getDown();

                    }
                    for (int i = 0; i <60; i++) {
                        for (int j = a; j <a+b; j++) {
                            cn.getTextWindow().setCursorPosition(i+1,j);
                            System.out.print(" ");

                        }
                    }

                }
            }
        }
    }
    public void write(){
        IndentNode temp = head;
        while (temp != null) {
            LetterNode temp2 = temp.getRight();
            while (temp2 != null) {
                cn.getTextWindow().setCursorPosition(temp2.getLetterx(),temp2.getLettery());
                System.out.print(temp2.getLetter());
                temp2 = temp2.getNext();
            }
            temp = temp.getDown();
            System.out.println();
        }
    }
    public int sizeindent() {
        int indent_count = 0;
        IndentNode temp = head;
        while (temp != null) {
            indent_count++;
            temp = temp.getDown();

        }
        return indent_count;
    }
    public int sizeletter(IndentNode t) {
        int letter_count = 0;
        LetterNode temp2 = t.getRight();
        while (temp2 != null) {
            letter_count++;
            temp2 = temp2.getNext();
        }
        return letter_count;
    }
    public void display() {
        if (head == null)
            System.out.println("Linked list is empty");
        else {
            IndentNode temp = head;
            while (temp != null) {

                LetterNode temp2 = temp.getRight();
                while (temp2 != null) {
                    System.out.print(temp2.getLetter());
                    temp2 = temp2.getNext();
                }

                temp = temp.getDown();
                System.out.println();
            }

        }
    }
    public void selection(Mll select) {
        IndentNode temp = select.head;
        IndentNode temp4 = select.head;
        LetterNode temp2 = null;
        LetterNode temp3 = null;
        LetterNode temp6 = null;
        LetterNode selection_start = null;
        LetterNode selection_end = null;
        boolean control = false;
        tem2 = "";
        int range;
        int range2;
        int count = 0;
        while (temp != null) {
            temp2 = temp.getRight();
            while (temp2 != null) {
                if (temp2.getLetterx() == Editor.selection_start_x && temp2.getLettery() == Editor.selection_start_y) {
                    selection_start = temp2;
                    control = true;
                    break;
                }
                temp2 = temp2.getNext();
            }
            if (control == true) {
                break;
            }
            temp = temp.getDown();

        }
        control = false;
        temp4 = select.head;
        while (temp4 != null) {
            temp3 = temp4.getRight();
            while (temp3 != null) {
                if (temp3.getLetterx() == Editor.selection_end_x && temp3.getLettery() == Editor.selection_end_y) {
                    selection_end = temp3;
                    control = true;
                    break;
                }
                temp6 = temp3;
                temp3 = temp3.getNext();
            }
            if (control == true) {
                break;
            }

            temp4 = temp4.getDown();

        }
        if (control == false) {
            selection_end = temp6;
        }
        range = selection_end.getLettery() - selection_start.getLettery();
        range2 = (selection_end.getLetterx() - selection_start.getLetterx());
        if (selection_start.getLettery() == selection_end.getLettery()) {
            if (control == false) {
                for (int i = 0; i < range2 + 1; i++) {
                    Editor.selections.add2(selection_start.getLetterx(), selection_start.getLettery(),
                            selection_start.getLetter());
                    selection_start = selection_start.getNext();
                }
            } else {
                while (selection_start.getLetterx() != selection_end.getLetterx()
                        && selection_end.getLettery() == selection_start.getLettery()) {
                    Editor.selections.add2(selection_start.getLetterx(), selection_start.getLettery(),
                            selection_start.getLetter());
                    selection_start = selection_start.getNext();
                }
            }
        } else {
            for (int i = 0; i < range + 1; i++) {
                while (temp.getLettertail().getLetterx() >= selection_start.getLetterx()
                        && selection_end.getLettery() != selection_start.getLettery()) {
                    Editor.selections.add2(selection_start.getLetterx(), selection_start.getLettery(),
                            selection_start.getLetter());
                    selection_start = selection_start.getNext();
                    if (temp.getLettertail().getLetterx() == selection_start.getLetterx()) {
                        Editor.selections.add2(selection_start.getLetterx(), selection_start.getLettery(),
                                selection_start.getLetter());
                        break;
                    }
                }
                if (i != range) {
                    temp = temp.getDown();
                    selection_start = temp.getRight();
                }
                while (selection_end.getLettery() == selection_start.getLettery()
                        && selection_start.getLetterx() != selection_end.getLetterx()) {
                    Editor.selections.add2(selection_start.getLetterx(), selection_start.getLettery(),
                            selection_start.getLetter());
                    selection_start = selection_start.getNext();
                }
                System.out.println(temp.getLettertail().getLetter());
            }
        }
    }
    public void cut(Mll select, Mll selected) {
        IndentNode temp = select.head;
        IndentNode temp2 = select.head;
        LetterNode temp3 = null;
        LetterNode temp4 = null;
        IndentNode temp5 = selected.head;
        temp4 = temp5.getRight();
        int size;
        int indent_size;
        while (temp5.getIndenty() != temp.getIndenty()) {
            temp = temp.getDown();
        }
        temp3 = temp.getRight();
        while (temp3.getLetterx() != temp4.getLetterx()) {
            temp3 = temp3.getNext();
        }
        size = sizeletter(temp5);
        indent_size = selected.sizeindent();
        for (int i = 0; i < indent_size; i++) {
            for (int j = 0; j < sizeletter(temp5); j++) {
                remove(temp3.getLetterx() + 1, temp3.getLettery());
                temp3 = temp3.getNext();
            }
            if (i != indent_size - 1) {
                temp = temp.getDown();
                temp3 = temp.getRight();
                temp5 = temp5.getDown();

            }

        }

    }
    public void write_remove() {
        IndentNode temp = head;
        int i = headx;
        int j = heady;
        while (temp != null) {
            LetterNode temp2 = temp.getRight();
            while (temp2 != null) {
                cn.getTextWindow().setCursorPosition(temp2.getLetterx(), temp2.getLettery());
                System.out.print(" ");
                temp2 = temp2.getNext();
                i++;
            }

            temp = temp.getDown();
            System.out.println();
        }
        ;
        temp = head;
        while (temp != null) {
            LetterNode temp2 = temp.getRight();
            while (temp2 != null) {
                cn.getTextWindow().setCursorPosition(temp2.getLetterx(), temp2.getLettery());
                System.out.print(temp2.getLetter());
                temp2 = temp2.getNext();

            }
            temp = temp.getDown();
            System.out.println();

        }

    }
    public boolean Search_in_category(Object category) {
        boolean f = false;
        IndentNode temp = head;
        while (temp != null) {
            if (category.toString().equals(temp.getIndent())) {
                f = true;
                break;
            }
            temp = temp.getDown();
        }
        return f;
    }
    public void load_display(int px, int py, Mll ml) {
        if (ml.head == null)
            System.out.println("Linked list is empty");
        else {
            cn.getTextWindow().setCursorPosition(head.getIndentx(), head.getIndenty());
            IndentNode temp = ml.head;

            for (int i = 0; i < ml.sizeindent(); i++) {
                LetterNode temp2 = temp.getRight();
                while (temp2 != null) {
                    System.out.print(temp2.getLetter());
                    temp2 = temp2.getNext();
                }
                if (temp.getRight() != null) {
                    py++;
                    cn.getTextWindow().setCursorPosition(px, py);
                }

                temp = temp.getDown();

            }
        }
    }
    public char findNode(int x, int y, IndentNode temp) {
        int x_cunt = 0;
        int y_cunt = 0;
        char r_node = ' ';
        // IndentNode temp = head;
        if (head == null) {
            System.out.println("linked list is empty");
        } else {
            while (temp != null) {
                LetterNode temp_2 = temp.getRight();
                while (temp_2 != null) {
                    if (x_cunt == x && y == y_cunt) {
                        r_node = temp_2.getLetter();
                    }
                    temp_2 = temp_2.getNext();
                    y_cunt++;
                }
                temp = temp.getDown();
                y_cunt = 1;
                x_cunt++;
            }
        }
        return r_node;
    }
    public void replace(int replacing [][],Mll mll,String word) {
        IndentNode ındent=mll.head;
        LetterNode letter=head.getRight();
        LetterNode letter2;
        int count=0;

        for (int i = 0; i < cont; i++) {
            for (int j = 0; j <replacing[i][1] ; j++) {
                if (j!=replacing[i][1]-1) {
                    ındent=ındent.getDown();
                    count++;
                }

            }
            letter=ındent.getRight();
            letter2=letter;
            if (count==0) {
                for (int j = 0; j < replacing[i][0]; j++) {
                    if (j!=replacing[i][0]-1) {
                        letter=letter.getNext();
                    }

                }
            }
            else {
                for (int j = 0; j < replacing[i][0]-letter2.getLetterx(); j++) {
                    if (j!=replacing[i][0]-1) {
                        letter=letter.getNext();
                    }

                }

            }

            for (int j = 0; j <replacing[i][2]-replacing[i][0]+1; j++) {
                letter.setLetter(word.charAt(j));
                letter=letter.getNext();
            }
        }
    }
    public void find(String word, Mll mll) {

        char firstLetter = word.charAt(0);
        IndentNode indent=mll.head;
        LetterNode letter=indent.getRight();
        LetterNode letter2=letter;
        int startx=0;
        int starty=0;
        int endx=0;
        int endy=0;
        int count=0;
        cont=0;
        String selected = "";
        String highlight;
        String select="";
        for (int i = 0; i < mll.sizeindent(); i++) {
            letter=indent.getRight();
            for (int j = 0; j < sizeletter(indent); j++) {
                if (firstLetter==letter.getLetter()) {
                    letter2=letter;
                    for (int k = 0; k < word.length(); k++) {
                        if (word.charAt(k)==letter2.getLetter()) {
                            count++;
                            if (count==1){
                                startx=letter2.getLetterx();
                                starty=letter2.getLettery();
                            }
                            if (count==word.length()) {
                                endx=letter2.getLetterx();
                                endy=letter2.getLettery();
                            }
                            selected=selected+word.charAt(k);
                            if (k!=word.length()-1) {
                                letter2=letter2.getNext();
                            }

                        }
                        else {
                            count=0;
                            selected="";
                            break;
                        }
                    }
                    if (selected.equals(word)) {
                        selected="";
                        highlight= highlight(mll, startx, starty, endx, endy, select);
                        select="";
                        count=0;
                        replace[cont][0]=startx;
                        replace[cont][1]=starty;
                        replace[cont][2]=endx;
                        replace[cont][3]=endy;

                        cont++;

                    }
                }
                if (j!=sizeletter(indent)-1) {
                    letter=letter.getNext();
                }
            }
            indent=indent.getDown();
        }
        cn.getTextWindow().setCursorPosition(62, 25);
        System.out.println("count => "+cont);
    }
    public String highlight(Mll mll, int startX, int startY, int endX, int endY, String selected) {
        int px = startX;
        int endLoop = (endY - startY + 1) * 60 - startX - (60 - endX);
        int cont = startY;
        for (int i = 1; i <= endLoop+1; i++) {
            selected += mll.findPosition(px, cont);
            cn.getTextWindow().setCursorPosition(px, cont);
            cn.getTextWindow().output(mll.findPosition(px, cont), new TextAttributes(Color.WHITE, Color.BLUE));
            px++;
        }
        return selected;
    }
    public char findPosition(int x, int y) {
        char re_Node = ' ';
        if (head == null) {
            System.out.println("linked list is empty");
        } else {
            IndentNode temp = head;
            while (temp != null) {
                LetterNode temp_2 = temp.getRight();
                while (temp_2 != null) {
                    if (temp_2.getLetterx() == x && y == temp_2.getLettery()) {
                        re_Node = temp_2.getLetter();
                    }
                    temp_2 = temp_2.getNext();
                }
                temp = temp.getDown();
            }
        }
        return re_Node;
    }
}




