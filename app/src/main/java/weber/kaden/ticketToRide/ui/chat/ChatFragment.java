package weber.kaden.ticketToRide.ui.chat;

import android.os.AsyncTask;
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

import java.util.ArrayList;
import java.util.List;

import weber.kaden.common.model.ChatMessage;
import weber.kaden.ticketToRide.R;
import weber.kaden.ticketToRide.model.ClientFacade;

public class ChatFragment extends DialogFragment implements ChatViewInterface {

    private Button sendButton, cancelButton;
    private EditText messageInput;
    private ChatAdapter adapter;
    private List<ChatMessage> messages = new ArrayList<>();
    private RecyclerView chatWindow;
    private ChatPresenter presenter;
    private SendChatTask chatTask;
    private ChatFragment instance = this;

    public ChatFragment() {}

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_, container, false);

        chatWindow = view.findViewById(R.id.chat_window);
        chatWindow.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ChatAdapter(getContext(), messages);
        chatWindow.setAdapter(adapter);

        sendButton = view.findViewById(R.id.send_button);
        cancelButton = view.findViewById(R.id.cancel_button);
        messageInput = view.findViewById(R.id.chat_text);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageInput.getText().toString();
                messageInput.getText().clear();
                sendChat(message);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        int width = getResources().getDimensionPixelSize(R.dimen.chat_width);
        int height = getResources().getDimensionPixelSize(R.dimen.chat_height);
        getDialog().getWindow().setLayout(width, height);

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
        chatTask = new SendChatTask(message);
        chatTask.execute((Void) null);
    }

    @Override
    public void printChatError(String message) {
        Toast.makeText(getContext(), "Error: chat did not send", Toast.LENGTH_LONG).show();
    }

    public class SendChatTask extends AsyncTask<Void, Void, Boolean> {

        private String message;
        private String errorMessage;

        SendChatTask(String message) {
            this.message = message;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            ClientFacade client = new ClientFacade();
            presenter = new ChatPresenter(instance, client);
            try {
                presenter.sendMessage(message);
            } catch (Exception e) {
                errorMessage = e.getMessage();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            if (!success) printChatError(errorMessage);
        }

        @Override
        protected void onCancelled() {
            chatTask = null;
            super.onCancelled();
        }
    }
}
