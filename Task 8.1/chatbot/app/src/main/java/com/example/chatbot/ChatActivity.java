package com.example.chatbot;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView messagesRecyclerView;
    private EditText messageInput;
    private Button sendButton;
    private ProgressBar progressBar;
    private MessageAdapter messageAdapter;
    private List<Message> messageList;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //get username from intent
        username = getIntent().getStringExtra("username");
        if (username == null) username = "User";

        //set action bar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Chat - " + username);
        }

        initViews();
        setupRecyclerView();
        setupClickListeners();

        //add welcome message
        addBotMessage("Hello " + username + "! How can I help you today?");
    }

    private void initViews() {
        messagesRecyclerView = findViewById(R.id.messagesRecyclerView);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);
        progressBar = findViewById(R.id.progressBar);
    }

    private void setupRecyclerView() {
        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        messagesRecyclerView.setLayoutManager(layoutManager);
        messagesRecyclerView.setAdapter(messageAdapter);
    }

    private void setupClickListeners() {
        sendButton.setOnClickListener(v -> sendMessage());

        messageInput.setOnEditorActionListener((v, actionId, event) -> {
            sendMessage();
            return true;
        });
    }

    private void sendMessage() {
        String userMessage = messageInput.getText().toString().trim();
        if (userMessage.isEmpty()) {
            Toast.makeText(this, getString(R.string.empty_message_error), Toast.LENGTH_SHORT).show();
            return;
        }

        //add user message to chat
        addUserMessage(userMessage);
        messageInput.setText("");

        //show progress bar
        progressBar.setVisibility(View.VISIBLE);
        sendButton.setEnabled(false);

        //send request to backend
        String url = "http://10.0.2.2:5001/chat";
        //String url = "http://192.168.1.241:5001/chat";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    //hide progress bar
                    progressBar.setVisibility(View.GONE);
                    sendButton.setEnabled(true);

                    //add bot response to chat
                    String botMessage = response.trim();
                    if (!botMessage.isEmpty()) {
                        addBotMessage(botMessage);
                    } else {
                        addBotMessage("Sorry, I couldn't understand that. Please try again.");
                    }
                },
                error -> {
                    //hide progress bar
                    progressBar.setVisibility(View.GONE);
                    sendButton.setEnabled(true);

                    //show error message
                    addBotMessage("Sorry, I'm having trouble connecting to the server. Please try again.");
                    Toast.makeText(this, getString(R.string.connection_error), Toast.LENGTH_LONG).show();
                }
        ) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userMessage", userMessage);
                return params;
            }
        };

        //set timeout and retry policy
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        //add request to queue
        Volley.newRequestQueue(this).add(request);
    }

    private void addUserMessage(String text) {
        Message message = new Message(text, true);
        messageList.add(message);
        messageAdapter.notifyItemInserted(messageList.size() - 1);
        scrollToBottom();
    }

    private void addBotMessage(String text) {
        Message message = new Message(text, false);
        messageList.add(message);
        messageAdapter.notifyItemInserted(messageList.size() - 1);
        scrollToBottom();
    }

    private void scrollToBottom() {
        if (messageList.size() > 0) {
            messagesRecyclerView.smoothScrollToPosition(messageList.size() - 1);
        }
    }
}