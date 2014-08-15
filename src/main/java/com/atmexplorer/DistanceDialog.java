package com.atmexplorer;

import android.content.Context;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

/**
 * @author Maks Kukushkin (maks.kukushkin@gmail.com)
 * @brief Allows to use NumberPicker in Settings
 */
public class DistanceDialog extends DialogPreference implements NumberPicker.OnValueChangeListener {

    private static final int COUNT_KM_STEPS = 11;
    private static final int COUNT_M_STEPS = 20;
    private static final int STEP_DIVISION = 50;

    private static final String ANDROID_NS = "http://schemas.android.com/apk/res/android";
    private static final String ATTR_DEFAULT_VALUE = "defaultValue";
    private static final int DEFAULT_CURRENT_VALUE = 2000;

    private int mCurrentValue;
    private NumberPicker mNumberPickerMain;
    private NumberPicker mNumberPickerExtra;
    private int mDefaultValue;

    public DistanceDialog(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public DistanceDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDefaultValue = attrs.getAttributeIntValue(ANDROID_NS, ATTR_DEFAULT_VALUE, DEFAULT_CURRENT_VALUE);
    }

    public View onCreateDialogView() {
        View view = null;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.layout_preference_distance, null);

        String[] values = new String[COUNT_KM_STEPS];
        for (int i = 0; i < values.length; i++) {
            values[i] = Integer.toString(i);
        }

        mCurrentValue = getPersistedInt(mDefaultValue);

        mNumberPickerMain = (NumberPicker) view.findViewById(R.id.numberPickerMain);
        mNumberPickerMain.setMaxValue(values.length - 1);
        mNumberPickerMain.setMinValue(0);
        //division on 1000 for get km
        mNumberPickerMain.setValue(mCurrentValue / 1000);
        mNumberPickerMain.setDisplayedValues(values);
        mNumberPickerMain.setOnValueChangedListener(this);

        String[] valuesExtra = new String[COUNT_M_STEPS];
        for (int i = 0; i < valuesExtra.length; i++) {
            valuesExtra[i] = Integer.toString(i * STEP_DIVISION);
        }
        mNumberPickerExtra = (NumberPicker) view.findViewById(R.id.numberPickerExtra);
        mNumberPickerExtra.setMaxValue(valuesExtra.length - 1);
        mNumberPickerExtra.setMinValue(0);
        //remainder of the division for get metres
        mNumberPickerExtra.setValue((mCurrentValue % 1000) / STEP_DIVISION);
        mNumberPickerExtra.setDisplayedValues(valuesExtra);
        mNumberPickerExtra.setOnValueChangedListener(this);

        return view;
    }


    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i2) {
        int km, metres = 0;
        mCurrentValue = 0;
        if (mNumberPickerMain.equals(numberPicker)) {
            //convert to metres
            km = i2 * 1000;
        } else {
            //convert to metres
            km = mNumberPickerMain.getValue() * 1000;
        }

        if (mNumberPickerExtra.equals(numberPicker)) {
            //convert to metres
            metres = i2 * STEP_DIVISION;
        } else {
            //convert to metres
            metres = mNumberPickerExtra.getValue() * STEP_DIVISION;
        }

        mCurrentValue = km + metres;
    }

    public void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (!positiveResult) return;
        if (shouldPersist()) persistInt(mCurrentValue);

        notifyChanged();
    }

}
