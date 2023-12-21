package com.jaydeemanuel.finalproj.data;

public class NoteModel {
    public int noteID;
    public String noteFname;
    public String noteLname;
    public String noteDose;
    public  String noteDate;

    NoteModel(int noteID, String noteFname, String noteLname, String noteDose, String noteDate){
        this.noteID = noteID;
        this.noteFname = noteFname;
        this.noteLname = noteLname;
        this.noteDose = noteDose;
        this.noteDate = noteDate;
    }

    @Override
    public String toString() {
        return "NoteModel{" +
                "noteID=" + noteID +
                ", noteFname='" + noteFname + '\'' +
                ", noteLname='" + noteLname + '\'' +
                ", noteDose='" + noteDose + '\'' +
                ", noteDate='" + noteDate + '\'' +
                '}';
    }
}
