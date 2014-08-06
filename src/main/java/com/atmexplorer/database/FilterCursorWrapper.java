package com.atmexplorer.database;

import android.database.Cursor;
import android.database.CursorWrapper;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief FilterCursorWrapper class creates a compatible cursor that uses a filter argument to quick search ListView
 * in android. It can be useful when you need unicode case insensitive "like" search, but you don't want to rebuild
 * android's sqlite with icu support.
 * (https://gist.github.com/ramzes642/5400792)
 */
public class FilterCursorWrapper extends CursorWrapper{
    private String mFilter;
    private int [] mColumn;
    private int[] mIndex;
    private int mCount = 0;
    private int mPosition = 0;

    public FilterCursorWrapper(Cursor cursor, String filter, int column1) {
        super(cursor);
        mFilter = filter.toLowerCase();
        mColumn = new int[] {column1};
        if (mFilter.isEmpty()) {
            mCount = super.getCount();
            mIndex = new int[mCount];
            for (int i = 0; i < mCount; i++) {
                super.moveToPosition(i);
                if (getString(mColumn[0]).toLowerCase().contains(mFilter)) {
                    mIndex[mPosition++] = i;
                }
            }
            mCount = mPosition;
            mPosition = 0;
            super.moveToFirst();
        } else {
            mCount = super.getCount();
            mIndex = new int[mCount];
            for (int i = 0; i < mCount; i++) {
                mIndex[i] = i;
            }
        }
    }

    @Override
    public boolean move(int offset) {
        return moveToPosition(mPosition + offset);
    }

    @Override
    public boolean moveToNext() {
        return moveToPosition(mPosition + 1);
    }

    @Override
    public boolean moveToPrevious() {
        return moveToPosition(mPosition - 1);
    }

    @Override
    public boolean moveToFirst() {
        return moveToPosition(0);
    }

    @Override
    public boolean moveToLast() {
        return moveToPosition(mCount - 1);
    }

    @Override
    public boolean moveToPosition(int position) {
        if (position >= mCount || position < 0) {
            return false;
        }
        return super.moveToPosition(mIndex[position]);
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public int getPosition() {
        return mPosition;
    }
}
