/*
 * Copyright 2010-2014 the original author or authors.
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

package org.springframework.android.showcase.rest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.springframework.android.showcase.AbstractAsyncActivity;
import org.springframework.android.showcase.R;
import org.springframework.android.showcase.to.AbstractJsonPostTask;

/**
 * @author Roy Clarkson
 * @author Helena Edelson
 * @author Pierre-Yves Ricau
 */
public class HttpPostJsonXmlActivity extends AbstractAsyncActivity {

	protected static final String TAG = HttpPostJsonXmlActivity.class.getSimpleName();

	// ***************************************
	// Activity methods
	// ***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.http_post_json_xml_activity_layout);

		// Initiate the JSON POST request when the JSON button is clicked
		final Button buttonJson = (Button) findViewById(R.id.button_post_json);
		final String uri = getString(R.string.base_uri);
		buttonJson.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new PostMessageTask(uri).execute();
			}
		});

		// Initiate the XML POST request when the XML button is clicked
//		final Button buttonXml = (Button) findViewById(R.id.button_post_xml);
//		buttonXml.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) {
//				new PostMessageTask().execute(MediaType.APPLICATION_XML);
//			}
//		});
	}

	// ***************************************
	// Private methods
	// ***************************************
	private void showResult(Object result) {
		if (result != null) {
			// display a notification to the user with the response message
			Toast.makeText(this, "AbstractJsonPostTask->" + result, Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "I got null, something happened!", Toast.LENGTH_LONG).show();
		}
	}

	// ***************************************
	// Private classes
	// ***************************************
	private class PostMessageTask extends AbstractJsonPostTask<Message,Message> {

		public PostMessageTask(String uri) {
			super(Message.class,uri);
		}

		@Override
		protected void onPreExecute() {
			Log.i(TAG,"-> onPreExecute");
			showLoadingProgressDialog();

			// build the message object
			EditText idET = (EditText) findViewById(R.id.edit_text_message_id);
            EditText sbjtET = (EditText) findViewById(R.id.edit_text_message_subject);
            EditText msgET = (EditText) findViewById(R.id.edit_text_message_text);

            int id = parseInt(idET.getText().toString());

			Message message = new Message();
            message.setId(id);
			message.setSubject(sbjtET.getText().toString());
			message.setText(msgET.getText().toString());
			super.postValue = message;
		}

        int parseInt(String value) {
            int id;
            try {
                id = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                id = 0;
            }
            return id;
        }

        @Override
		protected void onPostExecute(Message result) {
            Log.i(TAG,"-> onPostExecute: " + result);
			dismissProgressDialog();
			showResult(result);
		}

	}

}
