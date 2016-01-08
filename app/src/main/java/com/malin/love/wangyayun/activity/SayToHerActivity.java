package com.malin.love.wangyayun.activity;

import android.app.Activity;
import android.os.Bundle;

import com.malin.love.wangyayun.R;
import com.malin.love.wangyayun.view.TypeTextView;

/**
 * 类描述:文字描述
 * 创建人:
 * 创建时间:16-1-7
 * 备注:
 */
public class SayToHerActivity extends Activity {

    private TypeTextView mTypeTextView;
    private static final String LOVE = "曾经有一份真诚的爱情放在我面前，我没有珍惜，等我失去的时候我才后悔莫及，人世间最痛苦的事莫过于此。如果上天能够给我一个再来一次的机会，我会对那个女孩子说三个字：我爱你。如果非要在这份爱上加上一个期限，我希望是……一万年！ ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saytoher);

        mTypeTextView = (TypeTextView) findViewById(R.id.typeTxtId);

        mTypeTextView.setOnTypeViewListener(new TypeTextView.OnTypeViewListener() {
            @Override
            public void onTypeStart() {
            }

            @Override
            public void onTypeOver() {


            }
        });

        mTypeTextView.setText("");
        mTypeTextView.start(LOVE);
    }
}
