/*
 * Copyright (C) 2015 Can Elmas
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cnlms.wear.datalayerapi;

import android.app.Activity;
import android.app.RemoteInput;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by can on 09/05/15.
 */
public class ReplyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null != getIntent()) {
            Bundle remoteInput = RemoteInput.getResultsFromIntent(getIntent());

            if (null != remoteInput) {
                Toast.makeText(this, remoteInput.getCharSequence("extra_voice_reply"), Toast.LENGTH_SHORT).show();
            }
        }

        finish();
    }
}
