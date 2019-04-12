package com.cosmo.firebaserealtime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference myDatabaseReference;
    private String personId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        myDatabaseReference = FirebaseDatabase.getInstance().getReference("Pessoa");

        personId = myDatabaseReference.push().getKey();


        (findViewById(R.id.addBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPerson(((EditText) findViewById(R.id.nameEditText)).getText().toString(),
                        ((EditText) findViewById(R.id.lastNameEditText)).getText().toString());
            }
        });
        (findViewById(R.id.updateBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePerson(((EditText) findViewById(R.id.nameEditText)).getText().toString(), ((EditText) findViewById(R.id.lastNameEditText)).getText().toString());
            }
        });
        (findViewById(R.id.deleteBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removePerson("first added");
            }
        });
        (findViewById(R.id.loadBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readData();
            }
        });


        ((ListView) findViewById(R.id.peopleList)).setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                personId = parent.getItemAtPosition(position).toString();


            }

        });

        readData();
    }

    private void addPerson(String name, String lastName) {
        Person person = new Person(name, lastName, "");
        personId = UUID.randomUUID().toString();
        myDatabaseReference.child(personId).setValue(person);
        Toast.makeText(getApplicationContext(), "Salvo com Sucesso", Toast.LENGTH_SHORT).show();
        clearFields();
        readData();

    }

    private void updatePerson(String name, String lastName) {
        myDatabaseReference.child(personId).child("name").setValue(name);
        myDatabaseReference.child(personId).child("lastName").setValue(lastName);
        Toast.makeText(getApplicationContext(), "Atualizado com sucesso", Toast.LENGTH_SHORT).show();
        clearFields();
        readData();
    }

    private void removePerson(String name) {
        myDatabaseReference.child(personId).removeValue();
        Toast.makeText(getApplicationContext(), "Removido com sucesso", Toast.LENGTH_SHORT).show();
        clearFields();
        readData();
    }

    private void readData() {
        final ArrayList<String> names = new ArrayList<>();
        final ArrayList<String> lastNames = new ArrayList<>();
        final ArrayList<String> keys = new ArrayList<>();
        myDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();

                while ((iterator.hasNext())) {
                    DataSnapshot item = iterator.next();
                    Person value = item.getValue(Person.class);
                    value.setKey(item.getKey());
                    names.add(value.getName());
                    lastNames.add(value.getLastName());
                    keys.add(value.getKey());
                    ((CustomListAdapater) (((ListView) findViewById(R.id.peopleList)).getAdapter())).notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ((ListView) findViewById(R.id.peopleList)).
                setAdapter(new CustomListAdapater(names, lastNames, keys, this));
    }

    private void clearFields() {
        ((EditText) findViewById(R.id.nameEditText)).setText("");
        ((EditText) findViewById(R.id.lastNameEditText)).setText("");
    }
}
