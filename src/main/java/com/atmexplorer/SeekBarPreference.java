package com.atmexplorer;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief Allows to use SeekBar in Settings
 */
public class SeekBarPreference extends DialogPreference implements SeekBar.OnSeekBarChangeListener {

    private static final String PREFERENCE_NS = "http://schemas.android.com/apk/res/com.atmexplorer";
    private static final String ANDROID_NS = "http://schemas.android.com/apk/res/android";

    private static final String ATTR_DEFAULT_VALUE = "defaultValue";
    private static final String ATTR_MIN_VALUE = "minValue";
    private static final String ATTR_MAX_VALUE = "maxValue";

    private static final int DEFAULT_MIN_VALUE = 50;
    private static final int DEFAULT_MAX_VALUE = 5000;
    private static final int DEFAULT_CURRENT_VALUE = 500;

    private int mMinValue;
    private int mMaxValue;
    private int mDefaultValue;
    private int mCurrentValue;

    private SeekBar mSeekBar;
    private TextView mCurrentValueText;

    public SeekBarPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SeekBarPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        mMinValue = attrs.getAttributeIntValue(PREFERENCE_NS, ATTR_MIN_VALUE, DEFAULT_MIN_VALUE);
        mMaxValue = attrs.getAttributeIntValue(PREFERENCE_NS, ATTR_MAX_VALUE, DEFAULT_MAX_VALUE);
        mDefaultValue = attrs.getAttributeIntValue(ANDROID_NS, ATTR_DEFAULT_VALUE, DEFAULT_CURRENT_VALUE);
    }

    public View onCreateDialogView() {
        View view = null;
        mCurrentValue = getPersistedInt(mDefaultValue);
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.seek_bar_preference_layout, null);

        ((TextView)view.findViewById(R.id.min_value_label)).setText(Integer.toString(mMinValue));
        ((TextView)view.findViewById(R.id.max_value_label)).setText(Integer.toString(mMaxValue));
        mCurrentValueText = (TextView) view.findViewById(R.id.current_value_label);
        mCurrentValueText.setText(Integer.toString(mCurrentValue));

        mSeekBar = (SeekBar) view.findViewById(R.id.distance_seek_bar);
        mSeekBar.setMax(mMaxValue - mMinValue);
        mSeekBar.setProgress(mCurrentValue - mMinValue);
        mSeekBar.setOnSeekBarChangeListener(this);

        return view;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int value, boolean fromUser) {
        mCurrentValue = value + mMinValue;
        mCurrentValueText.setText(Integer.toString(mCurrentValue));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (!positiveResult) return;
        if (shouldPersist()) persistInt(mCurrentValue);

        notifyChanged();
    }

    public CharSequence getSummary() {
        String summary = super.getSummary().toString();
        int value = getPersistedInt(mDefaultValue);
        return String.format(summary, value);
    }
}
