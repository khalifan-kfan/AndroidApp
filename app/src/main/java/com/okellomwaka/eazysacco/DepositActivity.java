package com.okellomwaka.eazysacco;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import android.os.Handler;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.StringValue;

import ug.sparkpl.momoapi.models.Transaction;
import ug.sparkpl.momoapi.network.MomoApiException;
import ug.sparkpl.momoapi.network.RequestOptions;
import ug.sparkpl.momoapi.network.collections.CollectionsClient;

public class DepositActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText phone_number, amount;
    CollectionsClient client;
    Handler handler;
    ProgressBar loading;
     HashMap<String, String> collmap;
     FirebaseFirestore firestore;
     FirebaseAuth auth;
     String account,external,tot_amount;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        phone_number = findViewById(R.id.etDepositNumber);
        amount = findViewById(R.id.etDeposit);
        loading = findViewById(R.id.progress_bar);
        Spinner spinner = findViewById(R.id.spinner1);
         handler= new Handler() ;
         firestore = FirebaseFirestore.getInstance();
         auth = FirebaseAuth.getInstance();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Account_Numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        account = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), account, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //Loading Deposit
    public  void  Make_Deposit( View v){
        if(!TextUtils.isEmpty(phone_number.getText())||!TextUtils.isEmpty(amount.getText())||!account.isEmpty()){
            loading.setVisibility(View.VISIBLE);
            String externalId = UUID.randomUUID().toString();
            external= externalId;
            collmap = new HashMap<>();
            collmap.put("amount", amount.getText().toString());
            collmap.put("mobile", phone_number.getText().toString());
            collmap.put("externalId", externalId);
            collmap.put("payeeNote", "Sacco");
            collmap.put("payerMessage", "Sacco payments");

            RequestOptions opts = RequestOptions.builder()
                    .setCollectionApiSecret(Constants.MY_SECRET_API_KEY)
                    .setCollectionPrimaryKey(Constants.MY_SECRET_SUBSCRIPTION_KEY)
                    .setCollectionUserId(Constants.MYSECRET_USER_ID)
                    .build();
            client = new CollectionsClient(opts);

            Runnable momo = new Runnable() {
                @Override
                public void run() {
                    try {
                        String transactionRef = client.requestToPay(collmap);
                        //present waiting for confirmation massage
                        if(!transactionRef.isEmpty()){
                            //call the tracking method with the reference
                            Tracking_transaction(transactionRef);
                        }else{
                            // dint execute ryt
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    } catch (MomoApiException e) {
                        //handle momo error
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Momo error", Toast.LENGTH_SHORT).show();
                            }
                        });
                        loading.setVisibility(View.GONE);
                    } catch (IOException e) {
                        //network
                        loading.setVisibility(View.GONE);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),"network error" , Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            };
            Thread bac = new Thread(momo);
            bac.start();
        }else {
            Toast.makeText(DepositActivity.this,"Please fill the required fields", Toast.LENGTH_SHORT).show();
        }
    }
    public void Tracking_transaction(final String ref) {

        Runnable track = new Runnable() {
            @Override
            public void run() {
                try {
                    Transaction t = client.getTransaction(ref);
                    final String state = t.getStatus();
                    if (state.toLowerCase().contains("success")) {
                        //end process
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "transaction:" + state, Toast.LENGTH_SHORT).show();
                                UpdateDatabase(ref,external);
                            }
                        });

                    } else if (state.toLowerCase().contains("failed") || state.toLowerCase().contains("rejected")) {
                        //end process
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "transaction:" + state, Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        //assuming process is pending
                        //try again after 5 seconds
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Tracking_transaction(ref);
                            }
                        },5000);
                    }
                } catch (IOException e) {

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Network issue", Toast.LENGTH_SHORT).show();
                        }
                    });

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Tracking_transaction(ref);
                        }
                    },5000);
                }
            }
        };
        Thread track_= new Thread(track);
        track_.start();

    }
    private void UpdateDatabase(String ref,String externalId) {
        HashMap <String, Object> pay = new HashMap<>();
        pay.put("amount", amount.getText().toString());
        pay.put("mobile", phone_number.getText().toString());
        pay.put("externalId", externalId);
        pay.put("userId",auth.getCurrentUser().getUid());
        pay.put("transactionId",ref);
        pay.put("account", account);
        firestore.collection("Transactions")
                .add(collmap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {

                firestore.collection("SaccoAccounts").document(account)
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().exists()) {
                                //first deposit
                               // String bal = (String) task.getResult().get("balance");
                               // int new_bal = Integer.parseInt(amount.getText().toString());
                                    tot_amount = amount.getText().toString();
                                Map<String, Object> map2 = new HashMap<>();
                                map2.put("update_timestamp", FieldValue.serverTimestamp());
                                map2.put("balance",String.valueOf(amount.getText().toString()));
                                firestore.collection("SaccoAccounts").document(account)
                                        .set(map2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Map<String, Object> map1 = new HashMap<>();
                                            map1.put("deposit_timestamp", FieldValue.serverTimestamp());
                                            map1.put("user_id",auth.getCurrentUser().getUid());
                                            map1.put("account", account);
                                            map1.put("deposit",amount.getText().toString());
                                            firestore.collection("SaccoAccounts").document(account)
                                                    .collection("deposits").add(map1).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                                    if(task.isSuccessful()){
                                                        //all done
                                                        Toast.makeText(getApplicationContext(), "All saved thank you",
                                                                Toast.LENGTH_SHORT).show();
                                                        changeUi();
                                                    }else {
                                                        //dint save properly
                                                    }
                                                }
                                            });
                                        }else{
                                            //dintt save properly
                                        }
                                        loading.setVisibility(View.GONE);
                                    }
                                });
                            }else{
                                String bal = (String) task.getResult().get("balance");
                                int new_bal = Integer.parseInt(amount.getText().toString())+Integer.parseInt(bal);
                                tot_amount = String.valueOf(new_bal);
                                Map<String, Object> map2 = new HashMap<>();
                                map2.put("update_timestamp", FieldValue.serverTimestamp());
                                map2.put("balance",String.valueOf(new_bal));
                                firestore.collection("SaccoAccounts").document(account)
                                        .update(map2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Map<String, Object> map1 = new HashMap<>();
                                            map1.put("deposit_timestamp", FieldValue.serverTimestamp());
                                            map1.put("user_id",auth.getCurrentUser().getUid());
                                            map1.put("account", account);
                                            map1.put("deposit",amount.getText().toString());
                                            firestore.collection("SaccoAccounts").document(account)
                                                    .collection("deposits")
                                                    .add(map1).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                                    if(task.isSuccessful()){
                                                        //all done
                                                        Toast.makeText(getApplicationContext(), "All saved thank you",
                                                                Toast.LENGTH_SHORT).show();
                                                        changeUi();
                                                    }else {
                                                        //dint save properly
                                                    }
                                                }
                                            });
                                        }else{
                                            //dintt save properly
                                        }
                                        loading.setVisibility(View.GONE);
                                    }
                                });

                            }
                        }
                    }
                });
            }
        });
    }

    private void changeUi() {
        loading.setVisibility(View.GONE);
        Intent trans = new Intent(DepositActivity.this,Transactions_Activity.class);
        trans.putExtra("account",account);
        trans.putExtra("amount",tot_amount);
        startActivity(trans);

    }
}
