package com.atmexplorer.database;

import android.database.Cursor;
import android.database.CursorWrapper;

/**
 * @author ramzes642 (https://gist.github.com/ramzes642/5400792)
 * @brief FilterCursorWrapper class creates a compatible cursor that uses a filter argument to quick search ListView
 * in android. It can be useful when you need unicode case insensetive "like" search, but you don't want to rebuild
 * android's sqlite with icu support.
 */
public class FilterCursorWrapper extends CursorWrapper{
    private String mFilter;
    private int [] mColumn;
    private int[] mIndex;
    private int mCount = 0;
    private int mPosition = 0;

    public FilterCursorWrapper(Cursor cursor,String filter,int column1, int column2) {
        super(cursor);
        this.mFilter = filter.toLowerCase();
        this.mColumn = new int[] {column1, column2};
        if (this.mFilter.isEmpty()) {
            mCount = super.getCount();
            this.mIndex = new int[this.mCount];
            for (int i = 0; i < this.mCount; i++) {
                super.moveToPosition(i);
                if (this.getString(this.mColumn[0]).toLowerCase().contains(this.mFilter) ||
                        this.getString(this.mColumn[1]).toLowerCase().contains(this.mFilter))
                    this.mIndex[this.mPosition++] = i;
            }
            this.mCount = this.mPosition;
            this.mPosition = 0;
            super.moveToFirst();
        } else {
            this.mCount = super.getCount();
            this.mIndex = new int[this.mCount];
            for (int i = 0; i < this.mCount; i++) {
                this.mIndex[i] = i;
            }
        }
    }

    @Override
    public boolean move(int offset) {
        return this.moveToPosition(this.mPosition + offset);
    }

    @Override
    public boolean moveToNext() {
        return this.moveToPosition(this.mPosition + 1);
    }

    @Override
    public boolean moveToPrevious() {
        return this.moveToPosition(this.mPosition - 1);
    }

    @Override
    public boolean moveToFirst() {
        return this.moveToPosition(0);
    }

    @Override
    public boolean moveToLast() {
        return this.moveToPosition(this.mCount - 1);
    }

    @Override
    public boolean moveToPosition(int position) {
        if (position >= this.mCount || position < 0)
            return false;
        return super.moveToPosition(this.mIndex[position]);
    }

    @Override
    public int getCount() {
        return this.mCount;
    }

    @Override
    public int getPosition() {
        return this.mPosition;
    }
}
