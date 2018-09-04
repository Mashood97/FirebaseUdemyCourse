package com.example.okcomputers.firebaseudemycourse;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //Array list is used to pop up the array adapter and array adapter is used to pop up the listview
  //  private EditText text;
   // private EditText contact;
   // private Button btn;
 private  DatabaseReference databaseReference;
    private FirebaseDatabase firebasedatabase;
   // TextView textView,textView1;
   private ListView listView;
   private ArrayList<String> mUsername = new ArrayList<>();
   //for child changed method
    private ArrayList<String> mKeys = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*storing data in Firebase without any textfield etc whenever app launches it'll save this in firebase realtime database.
        FirebaseDatabase fbd = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = fbd.getReference("MyName");
        databaseReference.setValue("Hello world");*/

        firebasedatabase = FirebaseDatabase.getInstance();
        databaseReference = firebasedatabase.getReference("Users");
       // text = (EditText)findViewById(R.id.TextId);
       // contact = (EditText)findViewById(R.id.ContactId);
       // btn = (Button)findViewById(R.id.BtnClick);
       //listview retrieve data
        listView = (ListView)findViewById(R.id.listview1);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mUsername);
        listView.setAdapter(arrayAdapter);
        //we will use child event listener it is same as value event listener but it sees the value of child whenever it's changed!
            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded( DataSnapshot dataSnapshot,  String s) {
                  String value = dataSnapshot.getValue(String.class);
                  String key = dataSnapshot.getKey();
                  mUsername.add(value);
                  mKeys.add(key);
                  arrayAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged( DataSnapshot dataSnapshot,  String s) {
                    String value = dataSnapshot.getValue(String.class);
                    String key = dataSnapshot.getKey();
                     int index = mKeys.indexOf(key);
                    mUsername.set(index,value);
                    arrayAdapter.notifyDataSetChanged();
                }


                @Override
                public void onChildRemoved( DataSnapshot dataSnapshot) {
                     String values = dataSnapshot.getValue(String.class);
                     mUsername.remove(values);
                     arrayAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildMoved( DataSnapshot dataSnapshot,  String s) {

                }

                @Override
                public void onCancelled( DatabaseError databaseError) {

                }
            });

 // /it'll get the single item show into textview
   databaseReference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
         /* String value = dataSnapshot.getValue(String.class);
            textView = (TextView) findViewById(R.id.textView);
          textView.setText(value);*/
        }
        @Override
        public void onCancelled( DatabaseError databaseError) {

        }
    });

   /* btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
         this will add data in firebase with edittext and whenever we change the data it'll change once
          String name = text.getText().toString();
          databaseReference.child(name);
          databaseReference.child("Names").push().setValue(name);

         // multiple Value add
            String name = text.getText().toString();
            String contacts = contact.getText().toString();
            databaseReference = firebasedatabase.getReference();
            databaseReference.child("name").setValue(name);
            databaseReference.child("Contact").setValue(contacts);
        }
    });*/
    }
}
