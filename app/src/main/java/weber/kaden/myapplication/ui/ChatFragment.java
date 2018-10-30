package weber.kaden.myapplication.ui;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import weber.kaden.common.model.ChatMessage;
import weber.kaden.myapplication.R;

public class ChatFragment extends DialogFragment implements ChatViewInterface {

    private Button sendButton, cancelButton;
    private EditText messageInput;
    private ChatAdapter adapter;
    private List<ChatMessage> messages;
    private RecyclerView chatWindow;
    private ChatPresenter presenter;

    public ChatFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_, container, false);

        presenter = new ChatPresenter(this);
        chatWindow = view.findViewById(R.id.chat_window);
        chatWindow.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ChatAdapter(getContext(), messages);

        sendButton = view.findViewById(R.id.send_button);
        cancelButton = view.findViewById(R.id.cancel_button);
        messageInput = view.findViewById(R.id.chat_text);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendChat(messageInput.getText().toString());
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: close dialog
                getDialog().dismiss();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // poller
    }

    @Override
    public void onPause() {
        super.onPause();
        // poller
    }

    @Override
    public void updateChat(List<ChatMessage> messages) {
        this.messages.clear();
        this.messages.addAll(messages);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void sendChat(String message) {
        presenter.sendMessage(message);
    }

    @Override
    public void printChatError(String message) {
        Toast.makeText(getContext(), "Error: chat did not send", Toast.LENGTH_LONG).show();
    }
}
