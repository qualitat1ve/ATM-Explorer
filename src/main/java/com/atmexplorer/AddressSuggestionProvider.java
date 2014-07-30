package com.atmexplorer;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import com.atmexplorer.database.DataBaseAdapter;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief Provides suggestions during searching
 */
public class AddressSuggestionProvider extends ContentProvider {
    private static final String LOG_TAG = AddressSuggestionProvider.class.getSimpleName();

    private DataBaseAdapter mDatabaseAdapter;

    public static String AUTHORITY = "com.atmexplorer.AddressSuggestionProvider";
    public static final String PATH = "atms";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PATH);

    public static final String SINGLE_ROW_ADDRESS = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + AUTHORITY + "." +
            PATH;
    public static final String MULTIPLE_ROW_ADDRESS = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + AUTHORITY + "." +
            PATH;

    //UriMatcher data
    private static final int SEARCH_ADDRESS = 0;
    private static final int GET_ADDRESS = 1;
    private static final int SUGGEST_ADDRESS = 2;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    /**
     * Builds up a UriMatcher for search suggestion.
     */
    private static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, PATH, SEARCH_ADDRESS);
        matcher.addURI(AUTHORITY, PATH + "/#", GET_ADDRESS);
        matcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY, SUGGEST_ADDRESS);
        matcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY + "/*", SUGGEST_ADDRESS);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mDatabaseAdapter = new DataBaseAdapter(getContext());
        return true;
    }

    @Override
    public final Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (sUriMatcher.match(uri)){
            case SUGGEST_ADDRESS:
                checkArguments(uri, selectionArgs);
                return getSuggestion(selectionArgs[0]);
            case SEARCH_ADDRESS:
                checkArguments(uri, selectionArgs);
                return doSearch(selectionArgs[0]);
            case GET_ADDRESS:
                return getAddress(uri);
            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
    }

    private Cursor getAddress(Uri uri) {
        String rowId = uri.getLastPathSegment();
        String[] columns = new String[] {
                DataBaseAdapter.KEY_ADDRESS};
        return mDatabaseAdapter.getAddress(rowId, columns);
    }

    private Cursor doSearch(String query) {
        String[] columns = new String[] {
                BaseColumns._ID,
                DataBaseAdapter.KEY_ADDRESS};
        return mDatabaseAdapter.getWordMatches(query, columns);
    }

    private void checkArguments(Uri uri, String[] selectionArgs) {
        if (selectionArgs == null) {
            throw new IllegalArgumentException("selectionArgs must be provided for the Uri: " + uri);
        }
    }

    private Cursor getSuggestion(String query) {
        String[] columns = new String[] {
                BaseColumns._ID,
                DataBaseAdapter.KEY_ADDRESS,
                SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID};
        return mDatabaseAdapter.getWordMatches(query, columns);
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)){
            case SEARCH_ADDRESS:
                return MULTIPLE_ROW_ADDRESS;
            case GET_ADDRESS:
                return SINGLE_ROW_ADDRESS;
            case SUGGEST_ADDRESS:
                return SearchManager.SUGGEST_MIME_TYPE;
            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        throw new UnsupportedOperationException();
    }
}
