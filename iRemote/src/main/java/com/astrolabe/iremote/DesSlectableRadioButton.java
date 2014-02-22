/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.astrolabe.iremote;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton;


public class DeSelectableRadioButton extends CompoundButton {

    public DeSelectableRadioButton(Context context) {
        this(context, null);
    }

    public DeSelectableRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.radioButtonStyle);
    }

    public DeSelectableRadioButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * This method will toggle the radio button.
     */
    @Override
    public void toggle() {
        super.toggle();
    }
}
