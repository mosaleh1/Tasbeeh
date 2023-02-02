package com.runcode.tasbee7.ui.Azkar;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "zikr_table")
public class Zikr {
    @PrimaryKey(autoGenerate = true)
    private int zikrId;
    private int numOfCount;
    private String zikrText;
    //private String zikrInfo;
    private boolean isMorningZikr;


    public Zikr(boolean isMorningZikr, int numOfCount/*, String zikrInfo*/, String zikrText) {
        this.numOfCount = numOfCount;
        this.zikrText = zikrText;
        this.isMorningZikr = isMorningZikr;
        //this.zikrInfo = zikrInfo;
    }

    public int getZikrId() {
        return zikrId;
    }

    public void setZikrId(int zikrId) {
        this.zikrId = zikrId;
    }

    public boolean isMorningZikr() {
        return isMorningZikr;
    }

    public void setMorningZikr(boolean morningZikr) {
        isMorningZikr = morningZikr;
    }

    public int getNumOfCount() {
        return numOfCount;
    }

    public void setNumOfCount(int numOfCount) {
        this.numOfCount = numOfCount;
    }

//    public String getZikrInfo() {
//        return zikrInfo;
//    }
//
//    public void setZikrInfo(String zikrInfo) {
//        this.zikrInfo = zikrInfo;
//    }

    public String getZikrText() {
        return zikrText;
    }

    public void setZikrText(String zikrText) {
        this.zikrText = zikrText;
    }
}
