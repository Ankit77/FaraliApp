/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package queenskitchen.in.farali.gcm;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

// http://gcm-alert.appspot.com/
//AIzaSyBWE_i73qbciQdwjb7Nkf8skeAxvAtfO9w
//simcard info

//http://www.devlper.com/2010/06/using-android-telephonymanager/

public class MyGcmListenerService extends GcmListenerService {
    private DevicePolicyManager devicePolicyManager;
    private ComponentName deviceAdmin;
    private static final String TAG = "MyGcmListenerService";
    private int cameratype = -1;

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        Log.e(MyGcmListenerService.class.getSimpleName(), "Throw Erro");
        String message = data.getString("message");


    }


}
