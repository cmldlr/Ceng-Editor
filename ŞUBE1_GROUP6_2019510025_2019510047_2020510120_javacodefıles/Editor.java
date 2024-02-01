package com.company;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

import enigma.console.TextAttributes;
import enigma.core.Enigma;
import enigma.event.TextMouseEvent;
import enigma.event.TextMouseListener;

public class Editor {
    public static enigma.console.Console cn = Enigma.getConsole("CENG EDITOR", 90, 30);
    public static enigma.console.TextWindow cnt = cn.getTextWindow();
    public static TextMouseListener tmlis;
    public static KeyListener klis;
    public static TextAttributes att0 = new TextAttributes(Color.white, Color.black); // foreground, background color
    public static TextAttributes att1 = new TextAttributes(Color.black, Color.white);
    public static int px;
    public static int py;
    public static int rkeymod;
    public static int capslock = 0;
    public static int mousepr;
    public static int mousex, mousey;
    public static int keypr;
    public static int rkey;
    char rckey = (char) rkey;
    public char[][] editor = new char[22][62];
    private Mll load_mll = new Mll();
    private Mll mll;
    private boolean flag = false;
    private int count = 0;
    private int temp_count = 0;
    private Mll[] mll_array = new Mll[20];
    public static int selection_start_x;
    public static int selection_start_y;
    public static int selection_end_x;
    public static int selection_end_y;
    public static Mll selections=new Mll();
    public static boolean next=false;
    public static int next_x=0;
    public static int next_y=0;
    public Editor() throws InterruptedException, IOException {

        screen();
        cursor(mll);
    }
    public void screen() throws InterruptedException, IOException {
        Scanner sc = new Scanner(System.in);
        BufferedReader br = new BufferedReader(new FileReader("editor.txt"));
        String str0 = "";
        int column = 0;

        while ((str0 = br.readLine()) != null) {
            for (int i = 0; i < str0.length(); i++) {
                char a = str0.charAt(i);
                editor[column][i] = a;

            }
            column++;
        }
        for (int i = 0; i < editor.length; i++) {
            for (int j = 0; j < editor[i].length; j++) {
                System.out.print(editor[i][j]);
            }
            System.out.println();
        }

        cnt.setCursorPosition(px, py);

        cn.getTextWindow().setCursorPosition(63, 0);// py,px
        System.out.println("F1:Selection start");
        cn.getTextWindow().setCursorPosition(63, 1);
        System.out.println("F2:Selection end");
        cn.getTextWindow().setCursorPosition(63, 2);
        System.out.println("F3:Cut");
        cn.getTextWindow().setCursorPosition(63, 3);
        System.out.println("F4:Copy");
        cn.getTextWindow().setCursorPosition(63, 4);
        System.out.println("F5:Paste");
        cn.getTextWindow().setCursorPosition(63, 5);
        System.out.println("F6:Find");
        cn.getTextWindow().setCursorPosition(63, 6);
        System.out.println("F7:Replace");
        cn.getTextWindow().setCursorPosition(63, 7);
        System.out.println("F8:Next");
        cn.getTextWindow().setCursorPosition(63, 8);
        System.out.println("F9:Align left");
        cn.getTextWindow().setCursorPosition(63, 9);
        System.out.println("F10:Justify");
        cn.getTextWindow().setCursorPosition(63, 10);
        System.out.println("F11:Load");
        cn.getTextWindow().setCursorPosition(63, 11);
        System.out.println("F12:Save");
        cn.getTextWindow().setCursorPosition(63, 13);
        System.out.println("Mode:Insert");
        cn.getTextWindow().setCursorPosition(63, 14);
        System.out.println("Alignment:");

        cn.getTextWindow().setCursorPosition(px, py);

    }
    public void cursor(Mll mll) throws InterruptedException, IOException {
        tmlis=new TextMouseListener() {
            public void mouseClicked(TextMouseEvent arg0) {}
            public void mousePressed(TextMouseEvent arg0) {
                if(mousepr==0) {
                    mousepr=1;
                    mousex=arg0.getX();
                    mousey=arg0.getY();
                }
            }
            public void mouseReleased(TextMouseEvent arg0) {}
        };
        cn.getTextWindow().addTextMouseListener(tmlis);

        klis = new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                if (keypr == 0) {
                    keypr = 1;
                    rkey = e.getKeyCode();
                    rkeymod = e.getModifiersEx();
                    if (rkey == KeyEvent.VK_CAPS_LOCK) {
                        if (capslock == 0)
                            capslock = 1;
                        else
                            capslock = 0;
                    }
                }
            }

            public void keyReleased(KeyEvent e) {
            }
        };
        cn.getTextWindow().addKeyListener(klis);
        int curtype;
        curtype = cnt.getCursorType();
        cnt.setCursorType(1);
        cn.setTextAttributes(att0);
        px = 1;
        py = 1;
        cn.getTextWindow().setCursorPosition(px, py);
        int count=0;
        boolean open=true;
        while (true) {

            if (keypr == 1) {
                if (rkey == KeyEvent.VK_LEFT && editor[py][px - 1] == ' ' && px >= 0 && px <= 60) {
                    px--;
                    if (px == 1) {
                        py--;
                        px = 60;
                    }

                }
                if (rkey == KeyEvent.VK_RIGHT && editor[py][px + 1] == ' ' && px >= 0 && px <= 60) {
                    px++;
                    if (px == 60) {
                        py++;
                        px = 1;
                    }

                }
                if (rkey == KeyEvent.VK_UP && editor[py - 1][px] == ' ' && py >= 0 && py <= 20) {
                    py--;

                }
                if (rkey == KeyEvent.VK_DOWN && editor[py + 1][px] == ' ' && py >= 0 && py <= 20) {
                    py++;

                }
                char rckey = (char) rkey;
                if(rckey==KeyEvent.VK_INSERT){
                    count++;
                    if(count%2==0){
                        open=true;
                        cn.getTextWindow().setCursorPosition(63,13);
                        System.out.println("                                              ");
                        cn.getTextWindow().setCursorPosition(63, 13);
                        System.out.println("Mode:Insert");
                    }
                    else {
                        open=false;
                        cn.getTextWindow().setCursorPosition(63, 13);
                        System.out.println("Mode:Overwrite");
                    }


                }


                if (rckey == '%' || rckey == '\'' || rckey == '&' || rckey == '(') {

                } else {

                    if (flag == false) {
                        mll = new Mll();
                        flag = true;
                    }
                    if (editor[py][px + 1] == ' ' && px >= 0 && px <= 60) {
                        if (rckey >= '0' && rckey <= '9') {
                            mll.add2(px, py, rckey);
                            px++;

                        }
                        if (rckey >= 'A' && rckey <= 'Z') {
                            if ((((rkeymod & KeyEvent.SHIFT_DOWN_MASK) > 0) || capslock == 1)) {
                                mll.add2(px, py, rckey);
                                px++;

                            } else {
                                mll.add2(px, py, (char) (rckey + 32));
                                px++;

                            }
                        }
                        if ((rkeymod & KeyEvent.SHIFT_DOWN_MASK) == 0) {
                            if ((rckey == '.' || rckey == ',' || rckey == '-')) {
                                mll.add2(px, py, rckey);
                                px++;

                            }
                        } else {
                            if (rckey == '.') {

                                mll.add2(px, py, (char) (rckey + 12));
                                px++;

                            }
                            if (rckey == ',') {
                                mll.add2(px, py, (char) (rckey + 15));
                                px++;

                            }
                        }
                    }

                    mll.write();
                    if (px == 60) {
                        py++;
                        px = 1;
                    }
                }

                cn.getTextWindow().setCursorPosition(px, py);
                keys(mll);
                keypr = 0;
                if (next==true) {
                    if (mousepr==1) {
                        cn.setTextAttributes(att0);
                        mll.write();
                        String next="";
                        if (mousex>px) {
                            mll.highlight(mll, px, py, mousex, mousey, next);
                        }
                        else {
                            mll.highlight(mll, mousex, mousey, px, py, next);
                        }
                        next="";
                        mousepr=0;
                    }
                }

            }
            Thread.sleep(5);
        }
    }
    public void load(Mll load_mll, int px, int py) throws FileNotFoundException {

        File input = new File("src/load.txt");
        Scanner scanner = new Scanner(input);
        IndentNode temp;
        temp = load_mll.head;
        boolean control = false;
        int counter = 1;
        int count=0;
        boolean control2 = false;
        while (scanner.hasNextLine()) {
            count=0;
            String tem = scanner.nextLine();
            if (tem == "") {
                load_mll.addındentfromend(px, py, '*');
            } else {
                for (int i = 0; i < tem.length(); i++) {
                    if (load_mll.Search_in_category(counter) == false && control == false) {
                        if (load_mll.head == null) {
                            load_mll.addhead(px, py, tem.charAt(i));
                            control = true;
                            control2 = true;
                        } else {
                            load_mll.addındentfromend(px, py, tem.charAt(i));
                            control = true;
                            control2 = false;
                        }
                    }
                    count++;

                    temp = load_mll.head;
                    for (int j = 0; j < load_mll.sizeindent() - 1; j++) {
                        temp = temp.getDown();
                    }
                    if (count>1) {
                        load_mll.addingletterfromend(px, py, tem.charAt(i), temp);
                    }


                }
            }
            counter++;
            control = false;

        }

    }
    public void save(Mll[] a) throws IOException {
        Writer write = new FileWriter("src/load.txt");
        for (int j = 0; j < count; j++) {
            IndentNode temp;
            temp = a[j].head;
            for (int i = 0; i < a[j].sizeindent(); i++) {
                for (int k = 0; k < a[j].sizeletter(temp); k++) {
                    write.write(a[j].findNode(i, k,temp));
                }
                //temp = temp.getDown();
                write.write('\n');
            }

        }


        write.flush();
        write.close();

    }
    public void keys(Mll mll) throws IOException {
        if (rkey == KeyEvent.VK_SPACE) {
            cn.getTextWindow().setCursorPosition(px, py);
            mll.add2(px, py, (char) (rckey + 32));
            mll.write();
            px++;
            cn.getTextWindow().setCursorPosition(px, py);
        }
        if (rkey == KeyEvent.VK_F1) {
            selections = new Mll();
            selection_start_x = px;
            selection_start_y = py;
            cn.getTextWindow().setCursorPosition(px, py);
            cn.getTextWindow().setCursorPosition(82, 0);
            cn.getTextWindow().output("<<<<", new TextAttributes(Color.RED));
        }
        if (rkey == KeyEvent.VK_F2) {
            selection_end_x = px;
            selection_end_y = py;
            selections.selection(mll);
            cn.setTextAttributes(att1);
            cn.getTextWindow().setCursorPosition(selections.headx, selections.heady);
            selections.write();
            cn.getTextWindow().setCursorPosition(selection_end_x, selection_end_y);
            cn.setTextAttributes(att0);
            cn.getTextWindow().setCursorPosition(px, py);
            cn.getTextWindow().setCursorPosition(80, 1);
            cn.getTextWindow().output("<<<<", new TextAttributes(Color.red));
        }
        if (rkey == KeyEvent.VK_F3) {//cut
            mll.cut(mll, selections);
            mll.write();
            cn.getTextWindow().setCursorPosition(px, py);
            cn.getTextWindow().setCursorPosition(70, 2);
            cn.getTextWindow().output("<<<<", new TextAttributes(Color.ORANGE));
        }
        if (rkey == KeyEvent.VK_F4) {//copy
            selections.selection(mll);
            cn.getTextWindow().setCursorPosition(px, py);
            cn.getTextWindow().setCursorPosition(78, 3);
            cn.getTextWindow().output("<<<<", new TextAttributes(Color.GREEN));
        }
        if (rkey == KeyEvent.VK_F5) {//paste
            IndentNode s_temp;
            s_temp = Editor.selections.head;

            LetterNode s_temp_letter = s_temp.getRight();
            cn.getTextWindow().setCursorPosition(px, py);
            while (s_temp != null) {
                s_temp_letter = s_temp.getRight();
                while (s_temp_letter != null) {
                    mll.add2(px, py, s_temp_letter.getLetter());
                    s_temp_letter = s_temp_letter.getNext();
                    px++;
                }
                s_temp = s_temp.getDown();
                py++;
            }
            mll.write();
            cn.getTextWindow().setCursorPosition(px, py);
            cn.getTextWindow().setCursorPosition(72, 4);
            cn.getTextWindow().output("<<<<", new TextAttributes(Color.MAGENTA));



        }
        if (rkey == KeyEvent.VK_F6) {//find
            cn.getTextWindow().setCursorPosition(63, 22);
            System.out.println("||||||||||||||||||||||");
            cn.getTextWindow().setCursorPosition(63, 23);
            System.out.println("||                  ||");
            cn.getTextWindow().setCursorPosition(63, 24);
            System.out.println("||||||||||||||||||||||");
            cn.getTextWindow().setCursorPosition(65, 23);
            Scanner sc=new Scanner(System.in);
            String search=sc.nextLine();
            mll.find(search,mll);
        }
        if (rkey == KeyEvent.VK_F7) {
            cn.getTextWindow().setCursorPosition(10, 22);
            System.out.println("-----------------------");
            cn.getTextWindow().setCursorPosition(4, 23);
            System.out.println("Find: |                     |");
            cn.getTextWindow().setCursorPosition(10, 24);
            System.out.println("-----------------------");
            cn.getTextWindow().setCursorPosition(1, 25);
            System.out.println("Replace: |                     |");
            cn.getTextWindow().setCursorPosition(10, 26);
            System.out.println("-----------------------");
            cn.getTextWindow().setCursorPosition(12, 23);
            Scanner sc=new Scanner(System.in);
            String search=sc.nextLine();
            mll.find(search,mll);
            cn.getTextWindow().setCursorPosition(12, 25);
            String replace_word=sc.nextLine();
            mll.replace(Mll.replace,mll,replace_word);
            mll.write();
        }
        if (rkey == KeyEvent.VK_F8) {
            if (next==false) {
                next_x=px;
                next_y=py;
                next=true;
            }
            else {
                next=false;
            }
        }
        if (rkey == KeyEvent.VK_F9) {
        }
        if (rkey == KeyEvent.VK_F10) {

        }
        if (rkey == KeyEvent.VK_F11) {
            cn.getTextWindow().setCursorPosition(px, py);
            load(load_mll, px, py);
            load_mll.load_display(px, py, load_mll);
            cn.getTextWindow().setCursorPosition(px, py);
            cn.getTextWindow().setCursorPosition(73, 10);
            cn.getTextWindow().output("<<<<", new TextAttributes(Color.BLUE));

        }
        if (rkey == KeyEvent.VK_F12) {
            save(mll_array);
            cn.getTextWindow().setCursorPosition(73, 11);
            cn.getTextWindow().output("<<<<", new TextAttributes(Color.BLUE));

        }
        if (rkey == KeyEvent.VK_HOME) {
            IndentNode temp;
            temp = mll.head;
            int x = 1;

            for (int i = 0; i < mll.sizeindent(); i++) {

                if (py == temp.getIndenty()) {
                    x = temp.getIndentx();
                }
                temp = temp.getDown();

            }
            cn.getTextWindow().setCursorPosition(x, py);

        }
        if (rkey == KeyEvent.VK_END) {
            IndentNode temp;
            temp = mll.head;
            int x = 1;

            for (int i = 0; i < mll.sizeindent(); i++) {

                if (py == temp.getIndenty()) {
                    x = temp.getLettertail().getLetterx() + 1;
                }
                temp = temp.getDown();

            }
            cn.getTextWindow().setCursorPosition(x, py);

        }
        if (rkey == KeyEvent.VK_ENTER) {

            if (count != 0) {
                mll_array[count] = mll;
            }
            for (int i = 0; i < mll_array.length; i++) {
                if (i == 0 && mll_array[0] == null) {
                    mll_array[count] = mll;
                }
                if (i == 0 && mll_array[0] != null) {
                    count = 1;
                } else {
                    if (mll_array[i] != null) {
                        count++;
                    } else {
                        break;
                    }
                }

            }
            Mll new_mll = new Mll();
            flag = false;
            py++;
            px = 1;
            cn.getTextWindow().setCursorPosition(px, py);

        }

        if ((rkey == KeyEvent.VK_BACK_SPACE) && px > 0 && px <=60&& editor[py][px - 1] == ' ') {
            mll.remove(px, py);
            mll.write();
            px--;
            if (px==1) {
	            py--;
	            px=60;
	            px--;
	        }

            cn.getTextWindow().setCursorPosition(px, py);


        }
        if((rkey == KeyEvent.VK_DELETE) && px > 0 && px < 60&& editor[py][px] == ' '){
            mll.delete(px,py);
            mll.write();
            cn.getTextWindow().setCursorPosition(px, py);
        }

    }
}
