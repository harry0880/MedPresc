package com.medpresc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.card.CardProvider;
import com.dexafree.materialList.card.OnActionClickListener;
import com.dexafree.materialList.card.action.TextViewAction;
import com.dexafree.materialList.view.MaterialListView;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.squareup.picasso.RequestCreator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MaterialListView mListView;
    int cnt;
    private Context mContext;
    String[] menuItems={"Patient Info","Prescriptions Info","Patient History","Logout & Exit"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mContext = this;

        // Bind the MaterialListView to a variable
        mListView = (MaterialListView) findViewById(R.id.material_listview);
      //  mListView.setItemAnimator(new SlideInLeftAnimator());
        mListView.getItemAnimator().setAddDuration(300);
        mListView.getItemAnimator().setRemoveDuration(300);
while(cnt<5) {
    mListView.getAdapter().add(generate());
    cnt++;
}



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        AccountHeader headerResult = new AccountHeaderBuilder().withActivity(this)
                .withHeaderBackground(R.drawable.mat2).addProfiles(
                        new ProfileDrawerItem().withName("Doctor Name").withEmail("xyz@gmail.com")).build();

        ArrayList<IDrawerItem> items=new ArrayList<IDrawerItem>();

        int cnt=0;
        for(String str:menuItems) {
            items.add(new PrimaryDrawerItem().withName(menuItems[cnt]));
            cnt++;
        }


        final Drawer result= new DrawerBuilder().withActivity(this).withDrawerItems(items).withAccountHeader(headerResult).withSelectedItem(-1).withTranslucentStatusBar(true).withActionBarDrawerToggle(false).build();

    }

    private Card generate()
    {
       return new Card.Builder(this)
                .setTag("BASIC_IMAGE_BUTTON_CARD")
                .setDismissible()
                .withProvider(new CardProvider<>())
                .setLayout(R.layout.material_welcome_card_layout)
                .setTitle("Name of appointee")
                .setTitleGravity(Gravity.START)
               .setGender("Male")
               .setAge("10")
                .setDescription("Patient id ill")
                .setDescriptionGravity(Gravity.END)
                        //.setDrawable(R.drawable.dog)
                .setDrawableConfiguration(new CardProvider.OnImageConfigListener() {
                    @Override
                    public void onImageConfigure(@NonNull RequestCreator requestCreator) {
                        requestCreator.fit();
                    }
                })
                .addAction(R.id.ok_button, new TextViewAction(this)
                        .setListener(new OnActionClickListener() {
                            @Override
                            public void onActionClicked(View view, Card card) {
                                startActivity(new Intent(MainActivity.this,ActivityViewPatient.class));
                            }
                        }))
                .addAction(R.id.history, new TextViewAction(this)
                        .setListener(new OnActionClickListener() {
                            @Override
                            public void onActionClicked(View view, Card card) {
                                Toast.makeText(mContext, "You have pressed Historyn on card " + card.getProvider().getTitle(), Toast.LENGTH_SHORT).show();
                            }
                        })).endConfig().build();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
