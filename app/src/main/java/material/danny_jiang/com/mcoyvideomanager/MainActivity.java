package material.danny_jiang.com.mcoyvideomanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mcoy_jiang.videomanager.ui.McoyVideoView;

public class MainActivity extends AppCompatActivity {

    private McoyVideoView mcoyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mcoyView = ((McoyVideoView) findViewById(R.id.vvv));
        mcoyView.setVideoUrl("http://112.253.22.157/17/z/z/y/u/zzyuasjwufnqerzvyxgkuigrkcatxr/" +
                "hc.yinyuetai.com/D046015255134077DDB3ACA0D7E68D45.flv");
    }
}
