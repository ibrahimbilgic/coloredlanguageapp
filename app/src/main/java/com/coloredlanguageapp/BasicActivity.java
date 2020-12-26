package com.coloredlanguageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Adapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.huxq17.swipecardsview.SwipeCardsView;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BasicActivity extends AppCompatActivity {

    private SwipeCardsView swipeCardsView;

    private FirebaseAuth firebaseAuth;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);

        firebaseAuth = FirebaseAuth.getInstance();

        swipeCardsView = findViewById(R.id.swipeCardView);
        swipeCardsView.retainLastCard(false);
        swipeCardsView.enableSwipe(true);


        winnerWord();

    }


    private List<ItemModel> addList(String emoji,String english, String turkish, String mean) {
        List<ItemModel> items = new ArrayList<>();
        items.add(new ItemModel(emoji,english,turkish,mean));
        return items;
    }

    public void winnerWord(){
        myRef
                .child("Cards")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<String> sets = new ArrayList<>();
                        for (DataSnapshot snapshot1:snapshot.child("Basic").getChildren()){
                            sets.add(snapshot1.getKey());
                        }
                        Random random = new Random();
                        int number = random.nextInt(sets.size());
                        String word = sets.get(number);
                        getCard(word);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void getCard(String word) {
        myRef.child("Cards")
                .child("Basic")
                .child(word).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String htmlCode = snapshot.child("englishSentence").getValue().toString();
                String htmlCode2 = snapshot.child("turkishSentence").getValue().toString();
                String htmlCode4 = snapshot.child("words").getValue().toString();

                String htmlCode3 = "";
                if(!snapshot.child("emoji").getValue().toString().equals("0")){
                    htmlCode3 = snapshot.child("emoji").getValue().toString();
                }
                else{
                    htmlCode3 = "";
                }


                swipeCardsView.setCardsSlideListener(new SwipeCardsView.CardsSlideListener() {
                    @Override
                    public void onShow(int index) {
                    }

                    @Override
                    public void onCardVanish(int index, SwipeCardsView.SlideType type) {
                        winnerWord();
                    }

                    @Override
                    public void onItemClick(View cardImageView, int index) {

                    }
                });

                BaseCardAdapter cardAdapter = new BaseCardAdapter(addList(htmlCode3,htmlCode,htmlCode2,htmlCode4),BasicActivity.this);
                swipeCardsView.setAdapter(cardAdapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}