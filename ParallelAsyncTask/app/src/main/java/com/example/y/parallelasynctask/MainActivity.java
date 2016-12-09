package com.example.y.parallelasynctask;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.util.Pools;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    Button start_bt;
    ProgressBar one_pb, two_pb, three_pb, four_pb, five_pb;
    MyTask mytask1, mytask2, mytask3, mytask4, mytask5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start_bt = (Button) findViewById(R.id.start_bt);
        one_pb = (ProgressBar) findViewById(R.id.one_pb);
        two_pb = (ProgressBar) findViewById(R.id.two_pb);
        three_pb = (ProgressBar) findViewById(R.id.three_pb);
        four_pb = (ProgressBar) findViewById(R.id.four_pb);
        five_pb = (ProgressBar) findViewById(R.id.five_pb);

        start_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mytask1 = new MyTask(one_pb);
                StartAsyncTaskInParallel(mytask1);
                mytask2 = new MyTask(two_pb);
                StartAsyncTaskInParallel(mytask2);
                mytask3 = new MyTask(three_pb);
                StartAsyncTaskInParallel(mytask3);
                mytask4 = new MyTask(four_pb);
                StartAsyncTaskInParallel(mytask4);
                mytask5 = new MyTask(five_pb);
               StartAsyncTaskInParallel(mytask5);

            }
        });

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void StartAsyncTaskInParallel(MyTask task) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            task.execute();
    }

    public class MyTask extends AsyncTask<Void, Integer, Void> {
        ProgressBar mprogressbar;

        public MyTask(ProgressBar mprogressbar) {
            this.mprogressbar = mprogressbar;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            int i=0;
            synchronized (this){
                while (i<10){
                    try{
                        wait(1500);
                        i++;
                        publishProgress(i);


                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int progress = values[0];
           mprogressbar.setProgress(progress);
            super.onProgressUpdate(values[0]);
        }
    }
}


