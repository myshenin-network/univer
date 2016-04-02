package com.univer.db.lab_1.base;

import com.univer.db.lab_1.gui.UI;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class BaseDriver {
    private File base;
    private File tmp;
    private UI parent;

    public BaseDriver(String baseName, UI ui) throws IOException {
        parent = ui;

        base = new File(baseName);
        if(!base.exists()){
            base.createNewFile();
        }

        tmp = new File(parent.getWorkspace() + "tmp_" + base.getName());
        tmp.createNewFile();
    }

    public void print() throws FileNotFoundException {
        Scanner baseInput = new Scanner(base);
        String record = "";
        while (baseInput.hasNext()){
            record = baseInput.nextLine();
            record = record.replace(";", "\t");
            System.out.println(record);
        }
        baseInput.close();
    }

    public boolean delete(){
        return base.delete()&&tmp.delete();
    }

    public File makeMessage(Scanner input) throws IOException {
        File messageFile = new File(parent.getWorkspace() + "message" + new SimpleDateFormat("_ddMMyy_hhmm").format(new Date()) + ".txt");
        messageFile.createNewFile();

        PrintStream messageOutput = new PrintStream(messageFile);
        String messageBuff = input.nextLine();
        while(!messageBuff.equals("end.")){
            System.out.print(">> ");
            messageOutput.print(messageBuff.equals("") ? "" : messageBuff + "\n");
            messageBuff = input.nextLine();
        }

        messageOutput.close();
        return messageFile;
    }

    public void optimizeMessage(File messageFile) throws FileNotFoundException {
        Scanner input = new Scanner(messageFile);
        ArrayList<String> messages = new ArrayList<String>();
        while (input.hasNext()){
            messages.add(input.nextLine());
        }

        String buff = null;
        for(int i = 0; i < messages.size(); i++){
            for(int j = 0; j < messages.size() - i - 1; j++){
                if(Integer.parseInt(messages.get(j).split(" ")[0]) > Integer.parseInt(messages.get(j+1).split(" ")[0])){
                    buff = messages.get(j);
                    messages.set(j, messages.get(j+1));
                    messages.set(j+1, buff);
                }
            }
        }

        for(int i = 0; i < messages.size() - 1; i++){
            if(messages.get(i).split(" ")[0].equals(messages.get(i + 1).split(" ")[0])){
                if((messages.get(i).split(" ")[1].equals("insert"))&&(messages.get(i+1).split(" ")[1].equals("delete"))){
                    messages.remove(i);
                    messages.remove(i);
                    i = -1;
                } else if((i + 2 < messages.size())&&(messages.get(i).split(" ")[1].equals("insert"))&&
                        (messages.get(i+1).split(" ")[1].equals("update"))&&(messages.get(i + 1).split(" ")[1].equals("delete"))){
                    messages.remove(i);
                    messages.remove(i);
                    messages.remove(i);
                    i = -1;
                } else if((messages.get(i).split(" ")[1].equals("delete"))&&(messages.get(i + 1).split(" ")[1].equals("insert"))){
                    messages.remove(i);
                    messages.set(i, messages.get(i).replace("insert","update"));
                    i = -1;
                } else if((messages.get(i).split(" ")[1].equals("update"))&&(messages.get(i + 1).split(" ")[1].equals("delete"))){
                    messages.remove(i);
                    i = -1;
                } else if((messages.get(i).split(" ")[1].equals("insert"))&&(messages.get(i + 1).split(" ")[1].equals("update"))){
                    messages.remove(i);
                    messages.set(i, messages.get(i).replace("update", "insert"));
                    i = -1;
                }
            }
        }
        PrintStream messageOutput = new PrintStream(messageFile);
        for (int i = 0; i < messages.size(); i++){
            messageOutput.println(messages.get(i));
        }
        messageOutput.close();
    }

    public void insert(Picture picture) throws IOException, ParseException, NotUniqueIdentifier {
        Scanner baseInput = new Scanner(base);
        PrintStream tmpOutput = new PrintStream(new FileOutputStream(tmp, true));

        String buff = null;
        while (baseInput.hasNext()){
            buff = baseInput.next();
            if((picture.compareTo(Picture.fromString(buff)) > 0)||(picture.isInserted())){
                tmpOutput.append(buff + "\n");
            } else if((picture.compareTo(Picture.fromString(buff)) < 0)&&(!picture.isInserted())){
                tmpOutput.append(picture.toString());
                tmpOutput.append(buff + "\n");
                picture.setInserted(true);
            } else {
                throw new NotUniqueIdentifier();
            }
        }

        if(!picture.isInserted()){
            tmpOutput.append(picture.toString() + "\n");
            picture.setInserted(true);
        }

        baseInput.close();
        tmpOutput.close();

        flush();
    }

    private void flush() throws IOException {
        FileInputStream tmpInput = new FileInputStream(tmp);
        FileOutputStream baseOutput = new FileOutputStream(base, false);

        int buff = tmpInput.read();
        while (buff != -1){
            baseOutput.write(buff);
            buff = tmpInput.read();
        }

        tmpInput.close();
        baseOutput.close();

        tmp.delete();
    }

    public boolean exit(){
        return tmp.delete();
    }

    public void replace(int index, Picture picture) throws IOException, ParseException, ElementIsNotOccurred {
        boolean elementOccured = false;
        Scanner baseInput = new Scanner(base);
        PrintStream tmpOutput = new PrintStream(new FileOutputStream(tmp, true));

        String buff = null;
        while (baseInput.hasNext()){
            buff = baseInput.next();
            if(Picture.fromString(buff).getId() == index){
                tmpOutput.append(picture.toString());
            } else {
                tmpOutput.append(buff + "\n");
            }
        }

        baseInput.close();
        tmpOutput.close();

        flush();
        if(!elementOccured) throw new ElementIsNotOccurred();
    }

    public void remove(int index) throws IOException, ParseException, ElementIsNotOccurred {
        boolean elementOccured = false;
        Scanner baseInput = new Scanner(base);
        PrintStream tmpOutput = new PrintStream(new FileOutputStream(tmp, true));

        String buff = null;
        while (baseInput.hasNext()){
            buff = baseInput.next();
            if(Picture.fromString(buff).getId() != index){
                tmpOutput.append(buff + "\n");
                elementOccured = true;
            }
        }

        baseInput.close();
        tmpOutput.close();

        flush();

        if(!elementOccured) throw new ElementIsNotOccurred();
    }

    public Picture search(Picture sample, String mask) throws FileNotFoundException, ParseException {
        Scanner baseInput = new Scanner(base);
        while (baseInput.hasNext()){
            if(Picture.fromString(baseInput.next()).aboutEquals(sample, mask)) return Picture.fromString(baseInput.next());
        }
        return null;
    }

    public void executeGroup(String messageName) throws IOException, ParseException, NotUniqueIdentifier, ElementIsNotOccurred {
        Scanner message = new Scanner(new File(parent.getWorkspace() + messageName));

        String buff = null;
        while(message.hasNext()){
            buff = message.nextLine();

            if(buff.split(" ")[1].equals("insert")){
                insert(Picture.fromString(buff.split(" ")[2]));
            } else if(buff.split(" ")[1].equals("update")){
                replace(Integer.parseInt(buff.split(" ")[0]), Picture.fromString(buff.split(" ")[2]));
            } else  if(buff.split(" ")[1].equals("delete")){
                remove(Integer.parseInt(buff.split(" ")[0]));
            }
        }
    }
}
