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

package com.cnlms.wear;

import android.os.Bundle;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by can on 07/04/15.
 */
public final class ReplyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reply);

        if (null != getIntent()) {
            Bundle remoteInput = RemoteInput.getResultsFromIntent(getIntent());

            if (null != remoteInput) {
                Toast.makeText(this, remoteInput.getCharSequence(NotificationHelper.EXTRA_VOICE_REPLY), Toast.LENGTH_SHORT).show();
            }
        }

    }

}
