package mrfu.childlayout;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class MainActivity extends Activity {

    private GridLayout gridlayout;
    private ImageView grid_iv_left;
    private ImageView grid_iv_right_top;
    private ImageView grid_iv_right_bottom;
    private ImageView rel_iv_left;
    private ImageView rel_iv_right_top;
    private ImageView rel_iv_right_bottom;

    private static final String LEFT_IMAGE = "https://raw.githubusercontent.com/MrFuFuFu/ChildLayout/master/Images/left.png";
    private static final String RIGHT_IMAGE = "https://raw.githubusercontent.com/MrFuFuFu/ChildLayout/master/Images/right.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTitleBar();

        useLinearLayout();

        useGridLayout();

        useRelateLayout();

    }

    /**
     * unsuccess
     * 1. use LinearLayout to layout, it can't draw  iv_right_top and iv_right_bottom
     */
    private void useLinearLayout() {
        ImageView iv_left = (ImageView)findViewById(R.id.iv_left);
        ImageView iv_right_top = (ImageView) findViewById(R.id.iv_right_top);
        ImageView iv_right_bottom = (ImageView) findViewById(R.id.iv_right_bottom);

        Glide.with(this).load(LEFT_IMAGE).fitCenter().into(iv_left);
        Glide.with(this).load(RIGHT_IMAGE).fitCenter().into(iv_right_top);
        Glide.with(this).load(RIGHT_IMAGE).fitCenter().into(iv_right_bottom);

    }

    /**
     * success
     * 2. use gridLayout, this problem is the views do not stretch evently for each row. this causes a lot of extra space to the right of GridLayout. to solve this problem, we need to call MainActivity.java in setLayoutTODO method.
     */
    private void useGridLayout() {
        gridlayout = (GridLayout) findViewById(R.id.gridlayout);
        grid_iv_left = (ImageView) findViewById(R.id.grid_iv_left);
        grid_iv_right_top = (ImageView) findViewById(R.id.grid_iv_right_top);
        grid_iv_right_bottom = (ImageView) findViewById(R.id.grid_iv_right_bottom);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Hide the refresh after 2sec
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(MainActivity.this).load(RIGHT_IMAGE).fitCenter().into(grid_iv_right_bottom);
                        setLayoutTODO(gridlayout, grid_iv_right_bottom);

                        Glide.with(MainActivity.this).load(RIGHT_IMAGE).fitCenter().into(grid_iv_right_top);
                        setLayoutTODO(gridlayout, grid_iv_right_top);

                        Glide.with(MainActivity.this).load(LEFT_IMAGE).fitCenter().into(grid_iv_left);
                        setLayoutTODO(gridlayout, grid_iv_left);
                    }
                });
            }
        }, 2000);
    }

    /**
     * success
     * 3. this approach is great, it is completely solved my problem, my boss is a genius, in other wards, I was awkward? hahaha
     */
    private void useRelateLayout() {
        rel_iv_left = (ImageView) findViewById(R.id.rel_iv_left);
        rel_iv_right_top = (ImageView) findViewById(R.id.rel_iv_right_top);
        rel_iv_right_bottom = (ImageView) findViewById(R.id.rel_iv_right_bottom);

        Glide.with(this).load(LEFT_IMAGE).fitCenter().into(rel_iv_left);
        Glide.with(this).load(RIGHT_IMAGE).fitCenter().into(rel_iv_right_top);
        Glide.with(this).load(RIGHT_IMAGE).fitCenter().into(rel_iv_right_bottom);
    }


    private void setLayoutTODO(GridLayout gridLayoutParent, View child){
        GridLayout.LayoutParams params = (GridLayout.LayoutParams) child.getLayoutParams();
        params.width = (gridLayoutParent.getWidth()/gridLayoutParent.getColumnCount())
                -params.rightMargin - params.leftMargin;
        child.setLayoutParams(params);
    }

    private void initTitleBar() {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Problems LinearLayout");
        toolbar.setSubtitle("MrFu");
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                                               @Override
                                               public boolean onMenuItemClick(MenuItem item) {
                                                   return false;
                                               }
                                           }
        );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent it = new Intent(Intent.ACTION_VIEW);
            it.setData(Uri.parse("http://mrfufufu.github.io/"));
            it = Intent.createChooser(it, null);
            startActivity(it);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
